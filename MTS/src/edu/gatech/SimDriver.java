
package edu.gatech;

import java.util.*;
import java.sql.*;
//import org.postgresql.Driver;

public class SimDriver {
    private static SimQueue simEngine;
    private static BusSystem busModel;
    private static TrainSystem trainModel;
    private static Random randGenerator;
    private static Timestamp sysTime; //this variable can be used to calculate trafficFactor and SpeedFactor
    private static double BUS_TRAFFIC_FACTOR; //higher traffic = higher trafficFactor
    private static double BUS_SPEED_FACTOR; //higherSpeed = higher speed factor
    private static double TRAIN_SPEED_FACTOR;
    private static SimDriver simDriver = new SimDriver();

    public static SimDriver getSimDriver() {
        return simDriver;
    }

    private SimDriver() {
        simEngine = new SimQueue();
        busModel = new BusSystem();
        trainModel = new TrainSystem();
        BUS_TRAFFIC_FACTOR = 1;
        BUS_SPEED_FACTOR = 1;
        TRAIN_SPEED_FACTOR = 1;
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
            processTokens(tokens);
        } while (!tokens[0].equals("quit"));

        takeCommand.close();
    }

    /**
     * Process tokens
     * @param tokens Tokens received from file or command prompt
     */
    public void processTokens( String[] tokens) {
        switch (tokens[0]) {
            //implement to set system time through command "set_system_time"
            case "set_simulation_cond": //format set_simulation_cond busTrafficFactor BUS_SPEED_FACTOR TRAIN_SPEED_FACTOR
                if(tokens[1] != null) { BUS_TRAFFIC_FACTOR = Double.parseDouble(tokens[1]); }
                else { BUS_TRAFFIC_FACTOR = 1; }
                if(tokens[2] != null){ BUS_SPEED_FACTOR = Double.parseDouble(tokens[2]); }
                else { BUS_SPEED_FACTOR = 1;}
                if(tokens[3] != null ){ TRAIN_SPEED_FACTOR = Double.parseDouble(tokens[3]);}
                else { TRAIN_SPEED_FACTOR = 1;}
                break;

            //this command will be used by used to set travel timefactor for a particular stop on particular route
            case "set_traveltime_factor": //format: set_traveltime_factor,bus/train,routeID,stopID,factor

                setBusTravelTimeFactor( Integer.parseInt(tokens[2]), Double.parseDouble(tokens[4]), Integer.parseInt(tokens[3]) );
                /*
                int factor = (int)(Double.parseDouble(tokens[4])*100);
                if(tokens[1].toLowerCase().equals("bus")){
                    try{
                        busModel.getRoute(Integer.parseInt(tokens[2])).changeTravelTimeFactor(factor,Integer.parseInt(tokens[3]));
                        System.out.println("travel time factor for bus stop: " + tokens[3] +" on route: "+tokens[2]+"is changed to " + tokens[4]);
                    }
                    catch(NullPointerException ex){
                        System.out.println("invalid stopID and/or routeID input");
                    }
                }

                else if(tokens[1].toLowerCase().equals("train")){
                    try {
                        trainModel.getRoute(Integer.parseInt(tokens[2])).changeTravelTimeFactor(factor, Integer.parseInt(tokens[3]));
                        System.out.println("travel time factor for train stop: " + tokens[3] +" on route: "+tokens[2]+"is changed to " + tokens[4]);
                    }
                    catch(NullPointerException ex){
                        System.out.println("invalid stopID and/or routeID input");
                    }
                }
                else{
                    System.out.println("invalid input");
                }
                */
                break;

            case "add_rider"://format: add_rider0,bus/train1,stopID2,noOfRiders3,targetStopID4,targetRouteID5
                addRider(tokens[1], Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4]), Integer.parseInt(tokens[5]));
                break;
            case "add_rider_random_targetStop"://format: add_rider_random_targetStop,bus/train,stopID,noOfRiders,targetROuteID //this is supposed to be called by VehicleStop class and both user
                if(tokens[1].toLowerCase().equals("bus")){
                    BusRoute busRoute = busModel.getRoute(Integer.parseInt(tokens[4]));
                    Object[] values = busRoute.stopsOnRoute.values().toArray();
                    int randomBusStopID;
                    do{
                        Random generator = new Random();
                        int[] randomValue = (int[])(values[generator.nextInt(values.length)]);
                        randomBusStopID = randomValue[0];
                    }
                    while(randomBusStopID == Integer.parseInt(tokens[2])) ;
                    busModel.getStop(Integer.parseInt(tokens[2])).makeRiders(Integer.parseInt(tokens[3]), randomBusStopID, Integer.parseInt(tokens[4]));

                }
                else if(tokens[1].toLowerCase().equals("train")){
                    TrainRoute trainRoute = trainModel.getRoute(Integer.parseInt(tokens[4]));
                    Object[] values = trainRoute.stopsOnRoute.values().toArray();
                    int randomTrainStopID;
                    do{
                        Random generator = new Random();
                        int[] randomValue = (int[])(values[generator.nextInt(values.length)]);
                        randomTrainStopID = randomValue[0];
                    }
                    while(randomTrainStopID == Integer.parseInt(tokens[2])) ;
                    if(randomTrainStopID !=Integer.parseInt(tokens[2])) {
                        trainModel.getStop(Integer.parseInt(tokens[2])).makeRiders(Integer.parseInt(tokens[3]), randomTrainStopID, Integer.parseInt(tokens[4]));
                    }
                }
                else{
                    System.out.println("invalid input");
                }
                break;


            case "add_event":
                addEventToSimEngine( Integer.parseInt(tokens[1]), tokens[2], Integer.parseInt(tokens[3]) );
                break;
            case "add_bus_stop"://format:add_bus_stop busstopID bustopName Xcoord Ycoord
                int busStopID = addBusStop(Integer.parseInt(tokens[1]), tokens[2], Double.parseDouble(tokens[3]), Double.parseDouble(tokens[4]));
                System.out.println(" new bus stop: " + Integer.toString(busStopID) + " created");
                break;

            case "add_train_stop"://format: add_train_stop trainstopID trainsTopName Xcoord Ycoord
                int trainStopID = addTrainStop(Integer.parseInt(tokens[1]), tokens[2], Double.parseDouble(tokens[3]), Double.parseDouble(tokens[4]));
                System.out.println(" new train stop: " + Integer.toString(trainStopID) + " created");
                break;
            case "add_bus_route": //format: add_bus_route busrouteID busrouteNo busrouteName
                int busRouteID = addBusRoute(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), tokens[3]);
                System.out.println(" new bus route: " + Integer.toString(busRouteID) + " created");
                break;

            case "add_train_route": //format: add_train_route trainrouteID trainrouteNo trainrouteName
                int trainRouteID = addTrainRoute(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), tokens[3]);
                System.out.println(" new train route: " + Integer.toString(trainRouteID) + " created");
                break;
            case "add_bus"://format: add_bus uniqueBusID routeNoForTheBus indexOfBusStoponBusRoute(inputLocation) capacityOFTheBus SpeedOfTeeBus
                int busID = addBus(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4]), Integer.parseInt(tokens[5]));
                System.out.println(" new bus: " + Integer.toString(busID) + " created");
                break;
            case "add_train"://format: add_train uniqueTrainID routeNoForTheTrain indexOfTrainStopOnTrainRoute(inputLocation) capacityOfTheTrain SpeedOfTheTrain
                int trainID = addTrain(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4]), Integer.parseInt(tokens[5]));
                System.out.println(" new train: " + Integer.toString(trainID) + " created");
                break;
            case "extend_bus_route": //format: extend_bus_route busRouteID busStopID avgTravelTimefromPreviousStop
                extendBusRoute(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]),Integer.parseInt(tokens[3]));
                break;
            case "extend_train_route": //format: extend_train_route trainRouteID trainStopID avgTravelTimefromPreviousStop
                extendTrainRoute( Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]),Integer.parseInt(tokens[3]) );
                break;

            //this command will generate specified number of riders on each busstop, and will randomly assign them target busstop.
            //It will also make sure that generated riders have routeID that matches with the busroute passing through bustop.
            //psuedo code
            // for each busRoute in busModel
            //      for each busStop on the busRoute
            //          randomly select any busstop on the route
            //              create Rider on that particular stop, with randomly selected targetstop ID(check that randomly selected targetbusstop is not same as currentBusStop)

            case "make_random_riders_bus"://format: make_random_riders_bus minValue maxValue //TODO: RB- DONE
                createRandomBusRiders(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]) );
                break;
            case "make_random_riders_train"://format: make_random_riders_train minValue maxValue //TODO: RB - DONE
                createRandomTrainRiders(Integer.parseInt(tokens[1]),Integer.parseInt(tokens[2]));
                break;
            case "upload_real_data":
                uploadMARTAData();  //uncomment this after uploadMARTAData is implemented
                break;
            case "step_once":
                stepOnce();
                break;
            case "step_multi"://format: steP-multi,noOfEvents,frequency,waitInSeconds
                switch( tokens.length ) {
                    case 1:
                        System.out.println("Invalid step_multi command");
                        break;
                    case 2:
                        stepMulti( tokens.length, Integer.parseInt(tokens[1]), -1, -1, -1 );
                        break;
                    case 3:
                        stepMulti( tokens.length, Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), -1, -1 );
                        break;
                    case 4:
                        stepMulti( tokens.length, Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]), -1 );
                        break;
                    case 5:
                        stepMulti( tokens.length, Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4]) );
                        break;
                }
                break;
            case "system_report":
                System.out.println(" system report - stops, Vehicles and routes:");
                int busSystemRiders= 0; int trainSystemRiders = 0;
                for (BusStop singleBusStop: busModel.getStops().values()) { singleBusStop.displayInternalStatus(); busSystemRiders += singleBusStop.riders.size();}
                for (Bus singleBus: busModel.getBuses().values()) { singleBus.displayInternalStatus(); busSystemRiders += singleBus.passengers.size();}
                for (BusRoute singleBusRoute: busModel.getRoutes().values()) { singleBusRoute.displayInternalStatus(); }
                for (TrainStop singleTrainStop: trainModel.getStops().values()) { singleTrainStop.displayInternalStatus(); trainSystemRiders += singleTrainStop.riders.size(); }
                for (Train singleTrain: trainModel.getTrains().values()) { singleTrain.displayInternalStatus(); trainSystemRiders += singleTrain.passengers.size();}
                for (TrainRoute singleTrainRoute: trainModel.getRoutes().values()) { singleTrainRoute.displayInternalStatus(); }
                System.out.print("Total Bus System Riders: "+Integer.toString(busSystemRiders)+". ");
                System.out.println("Total Train System Riders: "+Integer.toString(trainSystemRiders)+".");

                break;
            case "display_model":
                busModel.displayModel();
                trainModel.displayModel();
                break;
            case "quit":
                System.out.println(" stop the command loop");
                break;
            default:
                System.out.println(" command not recognized");
                break;
        }
    }

    public void setBusTravelTimeFactor( int routeId, double factor, int stopId ) {
        int changedFactor = (int)(factor*100);
        busModel.getRoute(routeId).changeTravelTimeFactor(changedFactor,stopId);
        System.out.println("travel time factor for bus stop: " + stopId +" on route: "+routeId+"is changed to " + factor);
    }

    public void stepOnce() {
        simEngine.triggerNextEvent(busModel,trainModel);
        System.out.println(" queue activated for 1 event");
    }

    public int stepMulti(int noOfTokens, int noOfEvents, int frequency, int waitTime, int graphVizFrequency ) {
        int result = 1;
        System.out.println(" queue activated for " + noOfEvents + " event(s)");
        for (int i = 0; i < noOfEvents; i++) {
            // display the number of events completed for a given frequency
            if (noOfTokens >= 3) {
                if (i % frequency == 0) { System.out.println("> " + Integer.toString(i) + " events completed"); }
            }

            // execute the next event
            simEngine.triggerNextEvent(busModel, trainModel);

            // pause after each event for a given number of seconds
            if (noOfTokens >= 4) {
                try { Thread.sleep(waitTime * 1000); }
                catch (InterruptedException e) { e.printStackTrace(); }
            }
            // regenerate the model display (Graphviz dot file) for a given frequency
            if (noOfTokens >= 5) {
                if (i % graphVizFrequency == 0) { busModel.displayModel(); trainModel.displayModel(); }
            }
        }
        return result;
    }

    public void addEventToSimEngine(int evtRank, String evtType, int evtId) {
        simEngine.addNewEvent(evtRank, evtType, evtId);
        System.out.print(" new event - rank: " + evtRank);
        System.out.println(" type: " + evtType + " ID: " + evtId + " created");
    }

    public int createRandomBusRiders(int minRiders, int maxRiders) {
        return createBusRiders(minRiders, maxRiders);
    }

    public int createRandomTrainRiders(int minRiders, int maxRiders) {
        return createTrainRiders(minRiders,maxRiders);
    }

    public void extendTrainRoute( int routeId, int nextStopId, int avgTravelTime) {
        trainModel.appendStopToRoute(routeId, nextStopId,avgTravelTime);
        System.out.println(" train stop: " + nextStopId + " appended to train route " + routeId);
    }

    public void extendBusRoute( int routeId, int nextStopId, int avgTravelTime) {
        busModel.appendStopToRoute(routeId, nextStopId,avgTravelTime);
        System.out.println(" bus stop: " + nextStopId + " appended to bus route " + routeId);
    }

    public void addRider( String vehicle, int stopId, int noOfRiders, int targetStopId, int targetRouteId ) {
        if(vehicle.toLowerCase().equals("bus")){
            busModel.getStop(stopId).makeRiders(noOfRiders,targetStopId,targetRouteId);
        }
        else if(vehicle.toLowerCase().equals("train")){
            trainModel.getStop(stopId).makeRiders(noOfRiders,targetStopId,targetRouteId);
        }
        else{
            System.out.println("invalid input");
        }
    }

    public int addBusStop(int busStopID, String busStopName, double busStopXCoord, double busStopYCoord) {
        return busModel.makeStop(busStopID, busStopName, busStopXCoord, busStopYCoord);
    }

    public int addTrainStop(int trainStopID, String trainStopName, double trainStopXCoord, double trainStopYCoord) {
        return trainModel.makeStop(trainStopID, trainStopName, trainStopXCoord, trainStopYCoord);
    }

    public int addBusRoute( int busRouteID, int busRouteNo, String busRouteName) {
        return busModel.makeRoute( busRouteID, busRouteNo, busRouteName );
    }

    public int addTrainRoute( int trainRouteID, int trainRouteNo, String trainRouteName) {
        return trainModel.makeRoute( trainRouteID, trainRouteNo, trainRouteName );
    }

    public int addBus(int uniqueBusID, int routeNoForTheBus, int indexOfBusStopOnBusRoute, int capacityOfTheBus, int speedOfTheBus ) {
        return busModel.makeBus(uniqueBusID, routeNoForTheBus, indexOfBusStopOnBusRoute, capacityOfTheBus, speedOfTheBus);
    }

    public int addTrain(int uniqueTrainID, int routeNoForTheTrain, int indexOfTrainStopOnTrainRoute, int capacityOfTheTrain, int speedOfTheTrain ) {
        return trainModel.makeTrain(uniqueTrainID, routeNoForTheTrain, indexOfTrainStopOnTrainRoute, capacityOfTheTrain, speedOfTheTrain);
    }

    public void appendBusStopToRoute( int busRouteID, int busStopId, int avgTravelTimeFromPrevStop ) {
        busModel.appendStopToRoute( busRouteID, busStopId, avgTravelTimeFromPrevStop );
    }

    public void appendTrainStopToRoute( int trainRouteID, int trainStopId, int avgTravelTimeFromPrevStop ) {
        trainModel.appendStopToRoute( trainRouteID, trainStopId, avgTravelTimeFromPrevStop );
    }

    public int uploadMARTAData() {
        ResultSet rs;
        int result = -1;
        int recordCounter;
        int busCapacity = 40;
        int trainCapacity = 200;

        Integer stopID, routeID, travelTime;
        String stopName, routeName;
        // String direction;
        Double latitude, longitude;

        // intermediate data structures needed for assembling the routes
        HashMap<Integer, ArrayList<Integer>> busRouteLists = new HashMap<Integer, ArrayList<Integer>>();
        ArrayList<Integer> targetListBus;
        ArrayList<Integer> busTravelTimeList = new ArrayList<Integer>();
        ArrayList<Integer> circularBusRouteList = new ArrayList<Integer>();
        HashMap<Integer, ArrayList<Integer>> trainRouteLists = new HashMap<Integer, ArrayList<Integer>>();
        ArrayList<Integer> targetListTrain;
        ArrayList<Integer> trainTravelTimeList = new ArrayList<Integer>();
        ArrayList<Integer> circularTrainRouteList = new ArrayList<Integer>();

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

            // create the bus stops
            System.out.print(" extracting and adding the bus stops: ");
            recordCounter = 0;
            rs = stmt.executeQuery("SELECT * FROM apcdata_bus_stops");
            while (rs.next()) {
                stopID = rs.getInt("min_stop_id");
                stopName = rs.getString("stop_name");
                latitude = rs.getDouble("latitude");
                longitude = rs.getDouble("longitude");

                busModel.makeStop(stopID,stopName,latitude,longitude);
                recordCounter++;
            }
            System.out.println(Integer.toString(recordCounter) + " added");

            // create the train stops
            System.out.print(" extracting and adding the train stops: ");
            recordCounter = 0;
            rs = stmt.executeQuery("SELECT * FROM apcdata_train_stops");
            while (rs.next()) {
                stopID = rs.getInt("min_stop_id");
                stopName = rs.getString("stop_name");
                latitude = rs.getDouble("latitude");
                longitude = rs.getDouble("longitude");

                trainModel.makeStop(stopID,stopName,latitude,longitude);
                recordCounter++;
            }
            System.out.println(Integer.toString(recordCounter) + " added");

            // create the bus routes
            System.out.print(" extracting and adding the bus routes: ");
            recordCounter = 0;
            rs = stmt.executeQuery("SELECT * FROM apcdata_bus_routes");
            while (rs.next()) {
                routeID = rs.getInt("route");
                routeName = rs.getString("route_name");

                busModel.makeRoute(routeID, routeID, routeName);
                recordCounter++;

                // initialize the list of stops for the route as needed
                busRouteLists.putIfAbsent(routeID, new ArrayList<Integer>());
            }
            System.out.println(Integer.toString(recordCounter) + " added");

            // create the train routes
            System.out.print(" extracting and adding the train routes: ");
            recordCounter = 0;
            rs = stmt.executeQuery("SELECT * FROM apcdata_train_routes");
            while (rs.next()) {
                routeID = rs.getInt("route");
                routeName = rs.getString("route_name");

                trainModel.makeRoute(routeID, routeID, routeName);
                recordCounter++;

                // initialize the list of stops for the route as needed
                trainRouteLists.putIfAbsent(routeID, new ArrayList<Integer>());
            }
            System.out.println(Integer.toString(recordCounter) + " added");

            // add the bus stops to all of the bus routes
            System.out.print(" extracting and assigning bus stops to the bus routes: ");
            recordCounter = 0;
            rs = stmt.executeQuery("SELECT route, stop_id, travel_time FROM bus_travel_time WHERE (route, vehicle_number) IN (SELECT DISTINCT route, MIN(vehicle_number) vehicle_number FROM bus_travel_time GROUP BY route) ORDER BY route, departure_time;");
            // for each route, only select one vehicle's interpretation of the stops on the route, and of the travel times between stops
            while (rs.next()) {
                routeID = rs.getInt("route");
                stopID = rs.getInt("stop_id");
                travelTime = rs.getInt("travel_time");
                // direction = rs.getString("direction");

                // TODO
                //          Need to order the results, and filter out duplicate traversals of the same route.
                //          Ditto for the train code below.

                targetListBus = busRouteLists.get(routeID);
                if (!targetListBus.contains(stopID)) {
                    busModel.appendStopToRoute(routeID, stopID, travelTime);
                    recordCounter++;
                    targetListBus.add(stopID);
                    busTravelTimeList.add(travelTime);
                    // if (direction.equals("Clockwise")) { circularBusRouteList.add(routeID); }
                }
            }
            System.out.println(Integer.toString(recordCounter) + " assigned");

            // add the train stops to all of the train routes
            System.out.print(" extracting and assigning train stops to the train routes: ");
            recordCounter = 0;
            rs = stmt.executeQuery("SELECT route, stop_id, travel_time FROM train_travel_time WHERE (route, vehicle_number) IN (SELECT DISTINCT route, MIN(vehicle_number) vehicle_number FROM train_travel_time GROUP BY route) ORDER BY route, departure_time;");
            // for each route, only select one vehicle's interpretation of the stops on the route, and of the travel times between stops
            while (rs.next()) {
                routeID = rs.getInt("route");
                stopID = rs.getInt("stop_id");
                travelTime = rs.getInt("travel_time");
                // direction = rs.getString("direction");

                targetListTrain = trainRouteLists.get(routeID);
                if (!targetListTrain.contains(stopID)) {
                    trainModel.appendStopToRoute(routeID, stopID, travelTime);
                    recordCounter++;
                    targetListTrain.add(stopID);
                    trainTravelTimeList.add(travelTime);
                    // if (direction.equals("Clockwise")) { circularTrainRouteList.add(routeID); }
                }
            }

            // add the reverse "route back home" stops for two-way bus routes
            for (Integer reverseRouteID : busRouteLists.keySet()) {
                if (!circularBusRouteList.contains(reverseRouteID)) {
                    targetListBus = busRouteLists.get(reverseRouteID);
                    for (int i = targetListBus.size() - 1; i > 0; i--) {
                        busModel.appendStopToRoute(reverseRouteID, targetListBus.get(i), busTravelTimeList.get(i+1));
                    }
                }
            }

            // add the reverse "route back home" stops for two-way train routes
            for (Integer reverseRouteID : trainRouteLists.keySet()) {
                if (!circularTrainRouteList.contains(reverseRouteID)) {
                    targetListTrain = trainRouteLists.get(reverseRouteID);
                    for (int i = targetListTrain.size() - 1; i > 0; i--) {
                        trainModel.appendStopToRoute(reverseRouteID, targetListTrain.get(i), trainTravelTimeList.get(i+1));
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
                int minBuses = rs.getInt("min_vehicles");
                int avgBuses  = rs.getInt("avg_vehicles");
                int maxBuses = rs.getInt("max_vehicles");

                int routeLength = busModel.getRoute(routeID).getLength();
                int suggestedBuses = randomBiasedValue(minBuses, avgBuses, maxBuses);
                int busesOnRoute = Math.max(1, Math.min(routeLength / 2, suggestedBuses));

                int startingPosition = 0;
                int skip = Math.max(1, routeLength / busesOnRoute);
                for (int i = 0; i < busesOnRoute; i++) {
                    busModel.makeBus(busID, routeID, startingPosition + i * skip, busCapacity, 1);
                    simEngine.addNewEvent(0,"move_bus", busID++);
                    recordCounter++;
                }
            }
            System.out.println(Integer.toString(recordCounter) + " added");

            // create the trains and related event(s)
            System.out.print(" extracting and adding the trains and events: ");
            recordCounter = 0;
            int trainID = 0;
            rs = stmt.executeQuery("SELECT * FROM apcdata_train_distributions");
            while (rs.next()) {
                routeID = rs.getInt("route");
                int minTrains = rs.getInt("min_vehicles");
                int avgTrains  = rs.getInt("avg_vehicles");
                int maxTrains = rs.getInt("max_vehicles");

                int routeLength = trainModel.getRoute(routeID).getLength();
                int suggestedTrains = randomBiasedValue(minTrains, avgTrains, maxTrains);
                int trainsOnRoute = Math.max(1, Math.min(routeLength / 2, suggestedTrains));

                int startingPosition = 0;
                int skip = Math.max(1, routeLength / trainsOnRoute);
                for (int i = 0; i < trainsOnRoute; i++) {
                    trainModel.makeTrain(trainID, routeID, startingPosition + i * skip, trainCapacity, 1);
                    simEngine.addNewEvent(0,"move_train", trainID++);
                    recordCounter++;
                }
            }
            System.out.println(Integer.toString(recordCounter) + " added");

            /*
            // create the bus rider-passenger generator and associated event(s)
            System.out.print(" extracting and adding the bus rider frequency timeslots: ");
            recordCounter = 0;
            rs = stmt.executeQuery("SELECT * FROM apcdata_bus_rider_distributions");
            while (rs.next()) {
                stopID = rs.getInt("min_stop_id");
                int timeSlot = rs.getInt("time_slot");
                int minOns = rs.getInt("min_ons");
                int avgOns  = rs.getInt("avg_ons");
                int maxOns = rs.getInt("max_ons");
                int minOffs = rs.getInt("min_offs");
                int avgOffs = rs.getInt("avg_offs");
                int maxOffs = rs.getInt("max_offs");

                //TODO: Discuss with team
                //busModel.getStop(stopID).addArrivalInfo(timeSlot, minOns, avgOns, maxOns, minOffs, avgOffs, maxOffs);
                recordCounter++;
            }
            System.out.println(Integer.toString(recordCounter) + " added");

            // create the train rider-passenger generator and associated event(s)
            System.out.print(" extracting and adding the train rider frequency timeslots: ");
            recordCounter = 0;
            rs = stmt.executeQuery("SELECT * FROM apcdata_train_rider_distributions");
            while (rs.next()) {
                stopID = rs.getInt("min_stop_id");
                int timeSlot = rs.getInt("time_slot");
                int minOns = rs.getInt("min_ons");
                int avgOns  = rs.getInt("avg_ons");
                int maxOns = rs.getInt("max_ons");
                int minOffs = rs.getInt("min_offs");
                int avgOffs = rs.getInt("avg_offs");
                int maxOffs = rs.getInt("max_offs");

                //TODO: Discuss with team
                //trainModel.getStop(stopID).addArrivalInfo(timeSlot, minOns, avgOns, maxOns, minOffs, avgOffs, maxOffs);
                recordCounter++;
            }
            System.out.println(Integer.toString(recordCounter) + " added");
            */

            // Generate random bus and train riders
            createRandomBusRiders(20, 50);
            createRandomTrainRiders(20, 50);

            result = 0;

        } catch (Exception e) {
            System.err.println("Discovered exception: ");
            System.err.println(e.getMessage());
        }
        return result;
    }

    public int createBusRiders(int minValue, int maxValue){
        int busRiders = 0;
        for(BusRoute busRoute: busModel.getRoutes().values()){
            for(int[] busstopArray : busRoute.stopsOnRoute.values()){
                int currentBusStopID = busstopArray[0];
                int noOfRider = minValue + (int)(Math.random()*(maxValue-minValue+1)); //creates number of rider anywhere between 5 to 15
                int targetRouteID = busRoute.getID();
                for(int i=1;i<=noOfRider;i++){
                    Random generator = new Random();
                    Object[] values = busRoute.stopsOnRoute.values().toArray();
                    int[] randomValue = (int[])(values[generator.nextInt(values.length)]);
                    int randomBusStopID = randomValue[0];
                    if(randomBusStopID != busstopArray[0]) {
                        (busModel.getStop(busstopArray[0])).makeRiders(1, randomBusStopID, targetRouteID);
                        busRiders++;
                    }
                    else{
                        i--;
                    }
                }
            }
        }
        System.out.println("total " + busRiders + " bus riders created");
        return busRiders;
    }

    public int createTrainRiders(int minValue, int maxValue){
        int trainRiders = 0;
        for(TrainRoute trainRoute: trainModel.getRoutes().values()){
            for(int[] trainstopArray : trainRoute.stopsOnRoute.values()){
                int currentTrainStopID = trainstopArray[0];
                int noOfRiders =  minValue + (int)(Math.random()*(maxValue-minValue+1));//creates number of riders anywhere between 20 to 60
                int targetTrainRouteID = trainRoute.getID();
                for(int i=1;i<=noOfRiders;i++){
                    Random generatorT = new Random();
                    Object[] valuesT = trainRoute.stopsOnRoute.values().toArray();
                    int[] randomValueT = (int[])(valuesT[generatorT.nextInt(valuesT.length)]);
                    int randomTrainStopID = randomValueT[0];
                    if(randomTrainStopID != trainstopArray[0]) {
                        (trainModel.getStop(trainstopArray[0])).makeRiders(1, randomTrainStopID, targetTrainRouteID);
                        trainRiders++;
                    }
                    else{
                        i--;
                    }
                }
            }
        }
        System.out.println("total " + trainRiders + " train riders created");
        return trainRiders;
    }



    private static int randomBiasedValue(int lower, int middle, int upper) {
        int lowerRange = randGenerator.nextInt(middle - lower + 1) + lower;
        int upperRange = randGenerator.nextInt(upper - middle + 1) + middle;
        return (lowerRange + upperRange) /2;
    }

    public List<Integer> getBusStopIds() {
        List<Integer> retList = new ArrayList<>();
        int sizeOfList = busModel.getStops().size();
        if( sizeOfList > 0 ) {
            for( Integer tmpId: busModel.getStops().keySet() ) {
                retList.add(tmpId);
            }
        }
        return retList;
    }

    public List<Integer> getBusIds() {
        List<Integer> retList = new ArrayList<>();
        int sizeOfList = busModel.getBuses().size();
        if( sizeOfList > 0 ) {
            for( Integer tmpId: busModel.getBuses().keySet() ) {
                retList.add(tmpId);
            }
        }
        return retList;
    }

    public List<Integer> getBusRouteIds() {
        List<Integer> retList = new ArrayList<>();
        int sizeOfList = busModel.getRoutes().size();
        if( sizeOfList > 0 ) {
            for( Integer tmpId: busModel.getRoutes().keySet() ) {
                retList.add(tmpId);
            }
        }
        return retList;
    }

    public List<Integer> getTrainIds() {
        List<Integer> retList = new ArrayList<>();
        int sizeOfList = trainModel.getTrains().size();
        if( sizeOfList > 0 ) {
            for( Integer tmpId: trainModel.getTrains().keySet() ) {
                retList.add(tmpId);
            }
        }
        return retList;
    }

    public List<Integer> getTrainStopIds() {
        List<Integer> retList = new ArrayList<>();
        int sizeOfList = trainModel.getStops().size();
        if( sizeOfList > 0 ) {
            for( Integer tmpId: trainModel.getStops().keySet() ) {
                retList.add(tmpId);
            }
        }
        return retList;
    }

    public List<Integer> getTrainRouteIds() {
        List<Integer> retList = new ArrayList<>();
        int sizeOfList = trainModel.getRoutes().size();
        if( sizeOfList > 0 ) {
            for( Integer tmpId: trainModel.getRoutes().keySet() ) {
                retList.add(tmpId);
            }
        }
        return retList;
    }

    public HashMap<Integer, Bus> getBuses() {
        return busModel.getBuses();
    }

    public HashMap<Integer, BusStop> getBusStops() {
        return busModel.getStops();
    }

    public HashMap<Integer, BusRoute> getBusRoutes() {
        return busModel.getRoutes();
    }

    public HashMap<Integer, Train> getTrains() {
        return trainModel.getTrains();
    }

    public HashMap<Integer, TrainStop> getTrainStops() {
        return trainModel.getStops();
    }

    public HashMap<Integer, TrainRoute> getTrainRoutes() {
        return trainModel.getRoutes();
    }

    public double getBustrafficFactor() {
        return BUS_TRAFFIC_FACTOR;
    }

    public double getBusspeedFactor() {
        return BUS_SPEED_FACTOR;
    }

    public double getTrainSpeedFactor() {
        return TRAIN_SPEED_FACTOR;
    }

    public void setBustrafficFactor(double bustrafficFactor) {
        BUS_TRAFFIC_FACTOR = bustrafficFactor;
    }

    public void setBusspeedFactor(double busspeedFactor) {
        BUS_SPEED_FACTOR = busspeedFactor;
    }

    public void setTrainSpeedFactor(double trainSpeedFactor) {
        TRAIN_SPEED_FACTOR = trainSpeedFactor;
    }
}

