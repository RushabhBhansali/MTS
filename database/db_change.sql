ALTER TABLE apcdata ADD COLUMN vehicle_type char(1) CHECK (vehicle_type IN ('B', 'T')) DEFAULT 'B'; -- DEFAULT setting causes all pre-existing vehicles to be buses

CREATE OR REPLACE VIEW apcdata_activity_only AS
 SELECT apcdata.calendar_day,
    apcdata.route,
    apcdata.route_name,
    apcdata.direction,
    apcdata.stop_id,
    apcdata.stop_name,
    apcdata.arrival_time,
    apcdata.departure_time,
    apcdata.ons,
    apcdata.offs,
    apcdata.latitude,
    apcdata.longitude,
    apcdata.vehicle_number,
    apcdata.vehicle_type
   FROM apcdata
  WHERE (((apcdata.ons > 0) OR (apcdata.offs > 0)) AND (apcdata.latitude IS NOT NULL) AND (apcdata.longitude IS NOT NULL) AND ((apcdata.arrival_time IS NOT NULL) OR (apcdata.departure_time IS NOT NULL)));

CREATE OR REPLACE VIEW unique_stopid_per_name AS
 SELECT min(apcdata_activity_only.stop_id) AS min_stop_id,
    apcdata_activity_only.stop_name AS min_stop_name,
    apcdata_activity_only.vehicle_type
   FROM apcdata_activity_only
  GROUP BY apcdata_activity_only.stop_name, apcdata_activity_only.vehicle_type;

CREATE OR REPLACE VIEW apcdata_source AS
 SELECT apcdata_activity_only.calendar_day,
    apcdata_activity_only.route,
    apcdata_activity_only.route_name,
    apcdata_activity_only.direction,
    unique_stopid_per_name.min_stop_id,
    apcdata_activity_only.stop_name,
    apcdata_activity_only.arrival_time,
    apcdata_activity_only.departure_time,
    apcdata_activity_only.ons,
    apcdata_activity_only.offs,
    apcdata_activity_only.latitude,
    apcdata_activity_only.longitude,
    apcdata_activity_only.vehicle_number,
    apcdata_activity_only.vehicle_type
   FROM apcdata_activity_only,
    unique_stopid_per_name
  WHERE ((apcdata_activity_only.stop_name)::text = (unique_stopid_per_name.min_stop_name)::text)
  ORDER BY apcdata_activity_only.calendar_day, apcdata_activity_only.arrival_time, apcdata_activity_only.departure_time;

DROP VIEW IF EXISTS apcdata_bus_distributions;

CREATE OR REPLACE VIEW apcdata_bus_distributions AS
 SELECT temp.route,
    min(temp.vehicle_count) AS min_vehicles,
    trunc(avg(temp.vehicle_count)) AS avg_vehicles,
    max(temp.vehicle_count) AS max_vehicles
   FROM ( SELECT apcdata_source.calendar_day,
            apcdata_source.route,
            count(DISTINCT apcdata_source.vehicle_number) AS vehicle_count,
            apcdata_source.vehicle_type
           FROM apcdata_source
          GROUP BY apcdata_source.calendar_day, apcdata_source.route, apcdata_source.vehicle_type) temp
  WHERE temp.vehicle_type='B'
  GROUP BY temp.route;

CREATE OR REPLACE VIEW apcdata_train_distributions AS
 SELECT temp.route,
    min(temp.vehicle_count) AS min_vehicles,
    trunc(avg(temp.vehicle_count)) AS avg_vehicles,
    max(temp.vehicle_count) AS max_vehicles
   FROM ( SELECT apcdata_source.calendar_day,
            apcdata_source.route,
            count(DISTINCT apcdata_source.vehicle_number) AS vehicle_count,
            apcdata_source.vehicle_type
           FROM apcdata_source
          GROUP BY apcdata_source.calendar_day, apcdata_source.route, apcdata_source.vehicle_type) temp
  WHERE temp.vehicle_type='T'
  GROUP BY temp.route;

-- No "DROP VIEW" command here, since "apcdata_bus_distributions" was the name of the original view from which the above two views were derived.

CREATE OR REPLACE VIEW apcdata_bus_rider_distributions AS
 SELECT temp.min_stop_id,
    temp.time_slot,
    min(temp.on_sum) AS min_ons,
    trunc(avg(temp.on_sum)) AS avg_ons,
    max(temp.on_sum) AS max_ons,
    min(temp.off_sum) AS min_offs,
    trunc(avg(temp.off_sum)) AS avg_offs,
    max(temp.off_sum) AS max_offs
   FROM ( SELECT apcdata_source.calendar_day,
            apcdata_source.min_stop_id,
            date_part('hour'::text, apcdata_source.arrival_time) AS time_slot,
            sum(apcdata_source.ons) AS on_sum,
            sum(apcdata_source.offs) AS off_sum,
            apcdata_source.vehicle_type
           FROM apcdata_source
          GROUP BY apcdata_source.calendar_day, apcdata_source.min_stop_id,
                  (date_part('hour'::text, apcdata_source.arrival_time)), apcdata_source.vehicle_type) temp
  WHERE (temp.time_slot IS NOT NULL) AND temp.vehicle_type='B'
  GROUP BY temp.min_stop_id, temp.time_slot;

CREATE OR REPLACE VIEW apcdata_train_rider_distributions AS
 SELECT temp.min_stop_id,
    temp.time_slot,
    min(temp.on_sum) AS min_ons,
    trunc(avg(temp.on_sum)) AS avg_ons,
    max(temp.on_sum) AS max_ons,
    min(temp.off_sum) AS min_offs,
    trunc(avg(temp.off_sum)) AS avg_offs,
    max(temp.off_sum) AS max_offs
   FROM ( SELECT apcdata_source.calendar_day,
            apcdata_source.min_stop_id,
            date_part('hour'::text, apcdata_source.arrival_time) AS time_slot,
            sum(apcdata_source.ons) AS on_sum,
            sum(apcdata_source.offs) AS off_sum,
            apcdata_source.vehicle_type
           FROM apcdata_source
          GROUP BY apcdata_source.calendar_day, apcdata_source.min_stop_id,
                  (date_part('hour'::text, apcdata_source.arrival_time)), apcdata_source.vehicle_type) temp
  WHERE (temp.time_slot IS NOT NULL) AND temp.vehicle_type='T'
  GROUP BY temp.min_stop_id, temp.time_slot;

DROP VIEW IF EXISTS apcdata_rider_distributions;

CREATE OR REPLACE VIEW apcdata_bus_routelist_oneway AS
 SELECT apcdata.route,
    unique_stopid_per_name.min_stop_id,
    apcdata.calendar_day,
    apcdata.departure_time,
    apcdata.vehicle_number
   FROM apcdata,
    unique_stopid_per_name
  WHERE (((apcdata.stop_name)::text = (unique_stopid_per_name.min_stop_name)::text) AND ((apcdata.direction)::text = ANY (ARRAY[('Northbound'::character varying)::text, ('Eastbound'::character varying)::text, ('Clockwise'::character varying)::text]))) AND unique_stopid_per_name.vehicle_type='B'
  ORDER BY calendar_day, route, vehicle_number, departure_time, stop_id;
  -- ORDER BY clause was apcdata.calendar_day, apcdata.arrival_time, apcdata.departure_time;
  -- I changed it because not ordering by route and vehicle_number would cause the resulting list of stops on the routes to be out of order.

CREATE OR REPLACE VIEW apcdata_train_routelist_oneway AS
 SELECT apcdata.route,
    unique_stopid_per_name.min_stop_id,
    apcdata.calendar_day,
    apcdata.departure_time,
    apcdata.vehicle_number
   FROM apcdata,
    unique_stopid_per_name
  WHERE (((apcdata.stop_name)::text = (unique_stopid_per_name.min_stop_name)::text) AND ((apcdata.direction)::text = ANY (ARRAY[('Northbound'::character varying)::text, ('Eastbound'::character varying)::text, ('Clockwise'::character varying)::text]))) AND unique_stopid_per_name.vehicle_type='T'
  ORDER BY calendar_day, route, vehicle_number, departure_time, stop_id;
  -- ORDER BY clause was apcdata.calendar_day, apcdata.arrival_time, apcdata.departure_time;
  -- I changed it because not ordering by route and vehicle_number would cause the resulting list of stops on the routes to be out of order.

DROP VIEW IF EXISTS apcdata_routelist_oneway;

CREATE OR REPLACE VIEW apcdata_bus_travel_time AS
  SELECT a.calendar_day, a.route, a.stop_id stop_id_a, a.departure_time, MIN(a.departure_time-b.departure_time) travel_time, a.vehicle_number
    FROM (
      SELECT calendar_day, route, min_stop_id stop_id, departure_time, vehicle_number
      FROM apcdata_bus_routelist_oneway
      ORDER BY calendar_day, route, vehicle_number, departure_time, stop_id
      ) a
    INNER JOIN (
      SELECT calendar_day, route, min_stop_id stop_id, departure_time, vehicle_number
      FROM apcdata_bus_routelist_oneway
      ORDER BY calendar_day, route, vehicle_number, departure_time, stop_id
      ) b
    ON a.calendar_day=b.calendar_day
      AND a.route=b.route
      AND a.stop_id <> b.stop_id
      AND a.departure_time > b.departure_time
      AND a.vehicle_number=b.vehicle_number
    WHERE b.departure_time IS NOT NULL AND a.departure_time IS NOT NULL
    GROUP BY a.calendar_day, a.route, a.vehicle_number, a.stop_id, a.departure_time
    ORDER BY calendar_day, route, vehicle_number, departure_time;

CREATE OR REPLACE VIEW apcdata_train_travel_time AS
  SELECT a.calendar_day, a.route, a.stop_id stop_id_a, a.departure_time, MIN(a.departure_time-b.departure_time) travel_time, a.vehicle_number
    FROM (
      SELECT calendar_day, route, min_stop_id stop_id, departure_time, vehicle_number
      FROM apcdata_train_routelist_oneway
      ORDER BY calendar_day, route, vehicle_number, departure_time, stop_id
      ) a
    INNER JOIN (
      SELECT calendar_day, route, min_stop_id stop_id, departure_time, vehicle_number
      FROM apcdata_train_routelist_oneway
      ORDER BY calendar_day, route, vehicle_number, departure_time, stop_id
      ) b
    ON a.calendar_day=b.calendar_day
      AND a.route=b.route
      AND a.stop_id <> b.stop_id
      AND a.departure_time > b.departure_time
      AND a.vehicle_number=b.vehicle_number
    WHERE b.departure_time IS NOT NULL AND a.departure_time IS NOT NULL
    GROUP BY a.calendar_day, a.route, a.vehicle_number, a.stop_id, a.departure_time
    ORDER BY calendar_day, route, vehicle_number, departure_time;

CREATE OR REPLACE VIEW apcdata_bus_routes AS
 SELECT DISTINCT apcdata_source.route,
    apcdata_source.route_name
   FROM apcdata_source
   WHERE apcdata_source.vehicle_type='B';

CREATE OR REPLACE VIEW apcdata_train_routes AS
 SELECT DISTINCT apcdata_source.route,
    apcdata_source.route_name
   FROM apcdata_source
   WHERE apcdata_source.vehicle_type='T';

DROP VIEW IF EXISTS apcdata_routes;

CREATE OR REPLACE VIEW apcdata_bus_stops AS
 SELECT apcdata_source.min_stop_id,
    apcdata_source.stop_name,
    (avg(apcdata_source.latitude) - 33.0) AS latitude,
    (avg(apcdata_source.longitude) + 85.0) AS longitude
   FROM apcdata_source
  WHERE apcdata_source.vehicle_type='B'
  GROUP BY apcdata_source.min_stop_id, apcdata_source.stop_name;

CREATE OR REPLACE VIEW apcdata_train_stops AS
 SELECT apcdata_source.min_stop_id,
    apcdata_source.stop_name,
    (avg(apcdata_source.latitude) - 33.0) AS latitude,
    (avg(apcdata_source.longitude) + 85.0) AS longitude
   FROM apcdata_source
  WHERE apcdata_source.vehicle_type='T'
  GROUP BY apcdata_source.min_stop_id, apcdata_source.stop_name;

DROP VIEW IF EXISTS apcdata_stops;

CREATE OR REPLACE VIEW location_checks AS
 SELECT temp.min_stop_id,
    temp.stop_name,
    (((temp.max_lat - temp.min_lat) ^ (2)::numeric) + ((temp.max_long - temp.min_long) ^ (2)::numeric)) AS distance_diff,
    temp.vehicle_type
   FROM ( SELECT apcdata_source.min_stop_id,
            apcdata_source.stop_name,
            max(apcdata_source.latitude) AS max_lat,
            min(apcdata_source.latitude) AS min_lat,
            max(apcdata_source.longitude) AS max_long,
            min(apcdata_source.longitude) AS min_long,
            apcdata_source.vehicle_type
           FROM apcdata_source
          GROUP BY apcdata_source.min_stop_id, apcdata_source.stop_name, apcdata_source.vehicle_type) temp
  ORDER BY (((temp.max_lat - temp.min_lat) ^ (2)::numeric) + ((temp.max_long - temp.min_long) ^ (2)::numeric)) DESC;

