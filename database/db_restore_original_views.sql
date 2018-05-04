SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 1 (class 3079 OID 12393)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2183 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

DROP VIEW IF EXISTS apcdata_activity_only CASCADE;
DROP VIEW IF EXISTS apcdata_bus_distributions CASCADE;
DROP VIEW IF EXISTS apcdata_rider_distributions CASCADE;
DROP VIEW IF EXISTS apcdata_routelist_oneway CASCADE;
DROP VIEW IF EXISTS apcdata_routes CASCADE;
DROP VIEW IF EXISTS apcdata_source CASCADE;
DROP VIEW IF EXISTS apcdata_stops CASCADE;
DROP VIEW IF EXISTS location_checks CASCADE;
DROP VIEW IF EXISTS unique_stopid_per_name CASCADE;
DROP VIEW IF EXISTS apcdata_bus_rider_distributions CASCADE;
DROP VIEW IF EXISTS apcdata_bus_routelist_oneway CASCADE;
DROP VIEW IF EXISTS apcdata_bus_routes CASCADE;
DROP VIEW IF EXISTS apcdata_bus_stops CASCADE;
DROP VIEW IF EXISTS apcdata_train_distributions CASCADE;
DROP VIEW IF EXISTS apcdata_train_rider_distributions CASCADE;
DROP VIEW IF EXISTS apcdata_train_routelist_oneway CASCADE;
DROP VIEW IF EXISTS apcdata_train_routes CASCADE;
DROP VIEW IF EXISTS apcdata_train_stops CASCADE;

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
    apcdata.vehicle_number
   FROM apcdata
  WHERE (((apcdata.ons > 0) OR (apcdata.offs > 0)) AND (apcdata.latitude IS NOT NULL) AND (apcdata.longitude IS NOT NULL) AND ((apcdata.arrival_time IS NOT NULL) OR (apcdata.departure_time IS NOT NULL)));


CREATE OR REPLACE VIEW unique_stopid_per_name AS
 SELECT min(apcdata_activity_only.stop_id) AS min_stop_id,
    apcdata_activity_only.stop_name AS min_stop_name
   FROM apcdata_activity_only
  GROUP BY apcdata_activity_only.stop_name;


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
    apcdata_activity_only.vehicle_number
   FROM apcdata_activity_only,
    unique_stopid_per_name
  WHERE ((apcdata_activity_only.stop_name)::text = (unique_stopid_per_name.min_stop_name)::text)
  ORDER BY apcdata_activity_only.calendar_day, apcdata_activity_only.arrival_time, apcdata_activity_only.departure_time;


CREATE OR REPLACE VIEW apcdata_bus_distributions AS
 SELECT temp.route,
    min(temp.bus_count) AS min_buses,
    trunc(avg(temp.bus_count)) AS avg_buses,
    max(temp.bus_count) AS max_buses
   FROM ( SELECT apcdata_source.calendar_day,
            apcdata_source.route,
            count(DISTINCT apcdata_source.vehicle_number) AS bus_count
           FROM apcdata_source
          GROUP BY apcdata_source.calendar_day, apcdata_source.route) temp
  GROUP BY temp.route;


CREATE OR REPLACE VIEW apcdata_rider_distributions AS
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
            sum(apcdata_source.offs) AS off_sum
           FROM apcdata_source
          GROUP BY apcdata_source.calendar_day, apcdata_source.min_stop_id, (date_part('hour'::text, apcdata_source.arrival_time))) temp
  WHERE (temp.time_slot IS NOT NULL)
  GROUP BY temp.min_stop_id, temp.time_slot;


CREATE OR REPLACE VIEW apcdata_routelist_oneway AS
 SELECT apcdata.route,
    unique_stopid_per_name.min_stop_id
   FROM apcdata,
    unique_stopid_per_name
  WHERE (((apcdata.stop_name)::text = (unique_stopid_per_name.min_stop_name)::text) AND ((apcdata.direction)::text = ANY (ARRAY[('Northbound'::character varying)::text, ('Eastbound'::character varying)::text, ('Clockwise'::character varying)::text])))
  ORDER BY apcdata.calendar_day, apcdata.arrival_time, apcdata.departure_time;


CREATE OR REPLACE VIEW apcdata_routes AS
 SELECT DISTINCT apcdata_source.route,
    apcdata_source.route_name
   FROM apcdata_source;


CREATE OR REPLACE VIEW apcdata_stops AS
 SELECT apcdata_source.min_stop_id,
    apcdata_source.stop_name,
    (avg(apcdata_source.latitude) - 33.0) AS latitude,
    (avg(apcdata_source.longitude) + 85.0) AS longitude
   FROM apcdata_source
  GROUP BY apcdata_source.min_stop_id, apcdata_source.stop_name;


CREATE OR REPLACE VIEW location_checks AS
 SELECT temp.min_stop_id,
    temp.stop_name,
    (((temp.max_lat - temp.min_lat) ^ (2)::numeric) + ((temp.max_long - temp.min_long) ^ (2)::numeric)) AS distance_diff
   FROM ( SELECT apcdata_source.min_stop_id,
            apcdata_source.stop_name,
            max(apcdata_source.latitude) AS max_lat,
            min(apcdata_source.latitude) AS min_lat,
            max(apcdata_source.longitude) AS max_long,
            min(apcdata_source.longitude) AS min_long
           FROM apcdata_source
          GROUP BY apcdata_source.min_stop_id, apcdata_source.stop_name) temp
  ORDER BY (((temp.max_lat - temp.min_lat) ^ (2)::numeric) + ((temp.max_long - temp.min_long) ^ (2)::numeric)) DESC;


REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;
