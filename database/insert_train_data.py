#!/usr/bin/python3

import sys
import random
import pdb

def read_lines_from_file(file_name):
  return [line.strip() for line in open(file_name, "r").readlines()]

def great_circle_distance(lat1, long1, lat2, long2): # returns miles
  # For now, use Euclidean distance formula (assuming fixed degrees per mile in both directions) until I can get a hold of the Great Circle formula
  degrees_per_mile = 70
  return degrees_per_mile * ((lat2 - lat1)**2 + (long2 - long1)**2) ** 0.5

def seconds_to_formatted_time(seconds):
  # Assumes 'seconds' is the number of seconds elapsed since midnight
  return str(int(seconds / 3600)) + ":" + str(int(seconds / 60) % 60) + ":" + str(seconds % 60)

output_file = sys.argv[1]
file_stream = open(output_file, "w")
trains = 12
routes = 2
min_stops_per_route = 12
max_stops_per_route = 18
min_stop_id = 1000000
current_stop_id = min_stop_id
stop_names = read_lines_from_file("train_stop_names.txt")
route_names = read_lines_from_file("train_route_names.txt")
min_route_id = 1000
min_vehicle_number = 3000
orientations = ["NS", "EW"]
directions = {"NS": ["Northbound", "Southbound"], "EW": ["Eastbound", "Westbound"]}
min_latitude = 33.432372000000001
max_latitude = 34.105822000000003
mean_latitude = (min_latitude + max_latitude) / 2
min_longitude = -84.669803000000002
max_longitude = -84.088588999999999
mean_longitude = (min_longitude + max_longitude) / 2
train_capacity = 200
train_speed = 50 # mph; top speed is 70 mph
start_time = 17100 # 4:45:00 am
end_time = 75600 # 9:00:00 pm; it should be 1:00 am, but that crosses calendar days and makes things complicated; can't be later than 9:00 pm because routes will extend past end_time, and don't want it to run past midnight

calendar_days = ("2016-07-01", "2016-07-02", "2016-07-03", "2016-07-04", "2016-07-05", "2016-07-06", "2016-07-07", "2016-07-08")
route_ids = range(min_route_id, min_route_id + routes)
vehicle_numbers = range(min_vehicle_number, min_vehicle_number + trains)
routes = {}

center_stop = [{"stop_id": current_stop_id, "stop_name": stop_names[0],
                "latitude": mean_latitude, "longitude": mean_longitude}]
current_stop_id += 1

for route_id in route_ids: # add stops to routes
  orientation = orientations[(route_id - min_route_id) % len(orientations)]
  routes[orientation] = {"route_id": route_id, "route_name": route_names[route_id - min_route_id],
                         "orientation": orientation, "stops": []}
  num_stops_on_route = random.randint(min_stops_per_route, max_stops_per_route)
  if orientation=="NS":
    longitude = mean_longitude
    latitude = min_latitude
  elif orientation=="EW":
    latitude = mean_latitude
    longitude = min_longitude
  for stop_num in range(num_stops_on_route):
    if orientation=="NS":
      prev_latitude = latitude
      latitude += min(max_latitude - latitude, random.gauss(1, 0.2) * (max_latitude - latitude) / (num_stops_on_route - stop_num))
      if latitude >= mean_latitude and prev_latitude < mean_latitude:
        routes[orientation]["stops"] += center_stop
    elif orientation=="EW":
      prev_longitude = longitude
      longitude += min(max_longitude - longitude, random.gauss(1, 0.2) * (max_longitude - longitude) / (num_stops_on_route - stop_num))
      if longitude >= mean_longitude and prev_longitude < mean_longitude:
        routes[orientation]["stops"] += center_stop
    routes[orientation]["stops"] += [{"stop_id": current_stop_id, "stop_name": stop_names[current_stop_id - min_stop_id],
                                      "latitude": latitude, "longitude": longitude}]
    current_stop_id += 1

  list_of_stops = routes[orientation]["stops"].copy()[1:-1]
  list_of_stops.reverse()
  routes[orientation]["stops"] += list_of_stops

file_stream.write("DELETE FROM apcdata WHERE vehicle_type='T';\r\n")
file_stream.write("INSERT INTO apcdata (calendar_day, route, route_name, direction, stop_id, stop_name, arrival_time, departure_time, ons, offs, latitude, longitude, vehicle_number, vehicle_type) VALUES\r\n")

first_write = True
for calendar_day in calendar_days:
  for train in vehicle_numbers:
    orientation = random.choice(orientations)
    route = routes[orientation]
    route_id = route["route_id"]
    route_name = route["route_name"]
    vehicle_number = train
    vehicle_type = "T"
    riders_on_train = 0
    stop_index = 0
    current_time = start_time
    latitude = None
    longitude = None
    direction = directions[orientation][0]
    while current_time < end_time:
      for stop in route["stops"]:
        stop_id = stop["stop_id"]
        stop_name = stop["stop_name"]
        prev_latitude = latitude
        prev_longitude = longitude
        latitude = stop["latitude"]
        longitude = stop["longitude"]
        offs = min(riders_on_train, max(0, int(random.gauss(1, 0.2) * train_capacity / len(route["stops"]))))
        ons = min(train_capacity * 1.2 - riders_on_train, max(0, int(random.gauss(1, 0.2) * 2 * (1 - stop_index / (len(route["stops"]) - 1)) * train_capacity / len(route["stops"]))))
        riders_on_train += ons - offs
        if prev_latitude != None and prev_longitude != None:
          if direction=="Northbound" and latitude < prev_latitude:
            direction="Southbound"
          if direction=="Southbound" and latitude > prev_latitude:
            direction="Northbound"
          if direction=="Eastbound" and longitude < prev_longitude:
            direction="Westbound"
          if direction=="Westbound" and longitude > prev_longitude:
            direction="Eastbound"
          current_time += int(3600 * great_circle_distance(prev_latitude, prev_longitude, latitude, longitude) / train_speed)
        arrival_time = seconds_to_formatted_time(current_time)
        current_time += random.randint(0, 121) # Wait anywhere between 0 and 2 minutes
        departure_time = seconds_to_formatted_time(current_time)
        stop_index += 1

        if not first_write:
          file_stream.write(",\r\n")
        else:
          first_write = False
        file_stream.write("('" + calendar_day
          + "', "  + str(route_id)
          + ", '"  + str(route_id) + ": " + route_name
          + "', '" + direction
          + "', "  + str(stop_id)
          + ", '"  + stop_name
          + "', '" + arrival_time
          + "', '" + departure_time
          + "', "  + str(ons)
          + ", "   + str(offs)
          + ", "   + str(latitude)
          + ", "   + str(longitude)
          + ", "   + str(vehicle_number)
          + ", '"  + vehicle_type
          + "')")

file_stream.write(";\r\n")
file_stream.close()
