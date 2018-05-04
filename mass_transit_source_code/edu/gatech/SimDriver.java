package edu.gatech;

import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Random;
import java.sql.*;
import java.util.Properties;

public class SimDriver {
    private static SimQueue simEngine;
    private static BusSystem martaModel;
    private static Random randGenerator;

    public SimDriver() {
        simEngine = new SimQueue();
        martaModel = new BusSystem();
        randGenerator = new Random();
    }

    public void runInterpreter() {
        final String DELIMITER = ",";
        Scanner takeCommand = new Scanner(System.in);
        String[] tokens;

        do {
            System.out.print("# main: ");
            String userCommandLine = takeCommand.nextLine();
            tokens = userCommandLine.split(DELIMITER);

            switch (tokens[0]) {
                case "add_event":
                    simEngine.addNewEvent(Integer.parseInt(tokens[1]), tokens[2], Integer.parseInt(tokens[3]));
                    System.out.print(" new event - rank: " + Integer.parseInt(tokens[1]));
                    System.out.println(" type: " + tokens[2] + " ID: " + Integer.parseInt(tokens[3]) + " created");
                    break;
                case "add_stop":
                    int stopID = martaModel.makeStop(Integer.parseInt(tokens[1]), tokens[2], Integer.parseInt(tokens[3]), Double.parseDouble(tokens[4]), Double.parseDouble(tokens[5]));
                    System.out.println(" new stop: " + Integer.toString(stopID) + " created");
                    break;
                case "add_route":
                    int routeID = martaModel.makeRoute(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), tokens[3]);
                    System.out.println(" new route: " + Integer.toString(routeID) + " created");
                    break;
                case "add_bus":
                    int busID = martaModel.makeBus(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4]), Integer.parseInt(tokens[5]), Integer.parseInt(tokens[6]));
                    System.out.println(" new bus: " + Integer.toString(busID) + " created");
                    break;
                case "extend_route":
                    martaModel.appendStopToRoute(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]));
                    System.out.println(" stop: " + Integer.parseInt(tokens[2]) + " appended to route " + Integer.parseInt(tokens[1]));
                    break;
                case "upload_real_data":
                    uploadMARTAData();
                    break;
                case "step_once":
                    simEngine.triggerNextEvent(martaModel);
                    System.out.println(" queue activated for 1 event");
                    break;
                case "step_multi":
                    System.out.println(" queue activated for " + Integer.parseInt(tokens[1]) + " event(s)");
                    for (int i = 0; i < Integer.parseInt(tokens[1]); i++) {
                    	// display the number of events completed for a given frequency
                    	if (tokens.length >= 3) {
                    		if (i % Integer.parseInt(tokens[2]) == 0) { System.out.println("> " + Integer.toString(i) + " events completed"); }
                    	}
                    	
                    	// execute the next event
                    	simEngine.triggerNextEvent(martaModel);
                    	
                    	// pause after each event for a given number of seconds
                    	if (tokens.length >= 4) {
                    		try { Thread.sleep(Integer.parseInt(tokens[3]) * 1000); }
                    			catch (InterruptedException e) { e.printStackTrace(); }
                    	}
                    	// regenerate the model display (Graphviz dot file) for a given frequency
                    	if (tokens.length >= 5) {
                    		if (i % Integer.parseInt(tokens[4]) == 0) { martaModel.displayModel();}
                    	}
                    }
                    break;
                case "system_report":
                    System.out.println(" system report - stops, buses and routes:");
                    for (BusStop singleStop: martaModel.getStops().values()) { singleStop.displayInternalStatus(); }
                    for (Bus singleBus: martaModel.getBuses().values()) { singleBus.displayInternalStatus(); }
                    for (BusRoute singleRoute: martaModel.getRoutes().values()) { singleRoute.displayInternalStatus(); }
                    break;
                case "display_model":
                	martaModel.displayModel();
                	break;
                case "quit":
                    System.out.println(" stop the command loop");
                    break;
                default:
                    System.out.println(" command not recognized");
                    break;
            }

        } while (!tokens[0].equals("quit"));

        takeCommand.close();
    }

    private static void uploadMARTAData() {
        ResultSet rs;
        int recordCounter;

        Integer stopID, routeID;
        String stopName, routeName;
        // String direction;
        Double latitude, longitude;

        // intermediate data structures needed for assembling the routes
        HashMap<Integer, ArrayList<Integer>> routeLists = new HashMap<Integer, ArrayList<Integer>>();
        ArrayList<Integer> targetList;
        ArrayList<Integer> circularRouteList = new ArrayList<Integer>();

        try {
    		// connect to the local database system
        	System.out.println(" connecting to the database");
    		String url = "jdbc:postgresql://localhost:5432/martadb";
    		Properties props = new Properties();
    		props.setProperty("user", "postgres");
    		props.setProperty("password", "cs6310");
    		props.setProperty("ssl", "true");

			Connection conn = DriverManager.getConnection(url, props);
			Statement stmt = conn.createStatement();

			// create the stops
        	System.out.print(" extracting and adding the stops: ");
        	recordCounter = 0;
            rs = stmt.executeQuery("SELECT * FROM apcdata_stops");
            while (rs.next()) {
                stopID = rs.getInt("min_stop_id");
                stopName = rs.getString("stop_name");
                latitude = rs.getDouble("latitude");
                longitude = rs.getDouble("longitude");

                martaModel.makeStop(stopID,stopName,0,latitude,longitude);
                recordCounter++;
            }
            System.out.println(Integer.toString(recordCounter) + " added");

            // create the routes
        	System.out.print(" extracting and adding the routes: ");
        	recordCounter = 0;
            rs = stmt.executeQuery("SELECT * FROM apcdata_routes");
            while (rs.next()) {
                routeID = rs.getInt("route");
                routeName = rs.getString("route_name");

                martaModel.makeRoute(routeID, routeID, routeName);
                recordCounter++;

                // initialize the list of stops for the route as needed
                routeLists.putIfAbsent(routeID, new ArrayList<Integer>());
            }
            System.out.println(Integer.toString(recordCounter) + " added");

            // add the stops to all of the routes
        	System.out.print(" extracting and assigning stops to the routes: ");
        	recordCounter = 0;
            rs = stmt.executeQuery("SELECT * FROM apcdata_routelist_oneway");
            while (rs.next()) {
                routeID = rs.getInt("route");
                stopID = rs.getInt("min_stop_id");
                // direction = rs.getString("direction");

                targetList = routeLists.get(routeID);
                if (!targetList.contains(stopID)) {
                    martaModel.appendStopToRoute(routeID, stopID);
                    recordCounter++;
                    targetList.add(stopID);
                    // if (direction.equals("Clockwise")) { circularRouteList.add(routeID); }
                }
            }

            // add the reverse "route back home" stops for two-way routes
            for (Integer reverseRouteID : routeLists.keySet()) {
                if (!circularRouteList.contains(reverseRouteID)) {
                    targetList = routeLists.get(reverseRouteID);
                    for (int i = targetList.size() - 1; i > 0; i--) {
                        martaModel.appendStopToRoute(reverseRouteID, targetList.get(i));
                    }
                }
            }
            System.out.println(Integer.toString(recordCounter) + " assigned");

            // create the buses and related event(s)
        	System.out.print(" extracting and adding the buses and events: ");
        	recordCounter = 0;
            int busID = 0;
            rs = stmt.executeQuery("SELECT * FROM apcdata_bus_distributions");
            while (rs.next()) {
                routeID = rs.getInt("route");
                int minBuses = rs.getInt("min_buses");
                int avgBuses  = rs.getInt("avg_buses");
                int maxBuses = rs.getInt("max_buses");

                int routeLength = martaModel.getRoute(routeID).getLength();
                int suggestedBuses = randomBiasedValue(minBuses, avgBuses, maxBuses);
                int busesOnRoute = Math.max(1, Math.min(routeLength / 2, suggestedBuses));

                int startingPosition = 0;
                int skip = Math.max(1, routeLength / busesOnRoute);
                for (int i = 0; i < busesOnRoute; i++) {
                    martaModel.makeBus(busID, routeID, startingPosition + i * skip, 0, 10, 1);
                    simEngine.addNewEvent(0,"move_bus", busID++);
                    recordCounter++;
                }
            }
            System.out.println(Integer.toString(recordCounter) + " added");

            // create the rider-passenger generator and associated event(s)
        	System.out.print(" extracting and adding the rider frequency timeslots: ");
        	recordCounter = 0;
            rs = stmt.executeQuery("SELECT * FROM apcdata_rider_distributions");
            while (rs.next()) {
                stopID = rs.getInt("min_stop_id");
                int timeSlot = rs.getInt("time_slot");
                int minOns = rs.getInt("min_ons");
                int avgOns  = rs.getInt("avg_ons");
                int maxOns = rs.getInt("max_ons");
                int minOffs = rs.getInt("min_offs");
                int avgOffs = rs.getInt("avg_offs");
                int maxOffs = rs.getInt("max_offs");

                martaModel.getStop(stopID).addArrivalInfo(timeSlot, minOns, avgOns, maxOns, minOffs, avgOffs, maxOffs);
                recordCounter++;
            }
            System.out.println(Integer.toString(recordCounter) + " added");


        } catch (Exception e) {
            System.err.println("Discovered exception: ");
            System.err.println(e.getMessage());
        }
    }

    private static int randomBiasedValue(int lower, int middle, int upper) {
        int lowerRange = randGenerator.nextInt(middle - lower + 1) + lower;
        int upperRange = randGenerator.nextInt(upper - middle + 1) + middle;
        return (lowerRange + upperRange) /2;
    }

}
