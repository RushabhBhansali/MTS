DROP TABLE IF EXISTS bus_travel_time;
DROP TABLE IF EXISTS train_travel_time;

CREATE TABLE bus_travel_time (
  calendar_day		date,
  route			smallint,
  stop_id		integer,
  departure_time	time without time zone,
  travel_time		integer, -- measured in seconds
  vehicle_number	integer
);

CREATE TABLE train_travel_time (
  calendar_day		date,
  route			smallint,
  stop_id		integer,
  departure_time	time without time zone,
  travel_time		integer, -- measured in seconds
  vehicle_number	integer
);

INSERT INTO bus_travel_time (calendar_day, route, stop_id, departure_time, travel_time, vehicle_number)
  SELECT calendar_day, route, stop_id_a, departure_time, extract(epoch from travel_time), vehicle_number
  FROM apcdata_bus_travel_time;

INSERT INTO train_travel_time (calendar_day, route, stop_id, departure_time, travel_time, vehicle_number)
  SELECT calendar_day, route, stop_id_a, departure_time, extract(epoch from travel_time), vehicle_number
  FROM apcdata_train_travel_time;
