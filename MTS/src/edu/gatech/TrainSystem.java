package edu.gatech;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class TrainSystem {
    private HashMap<Integer, TrainStop> stops;
    private HashMap<Integer, TrainRoute> routes;
    private HashMap<Integer, Train> trains;

    public TrainSystem() {
        stops = new HashMap<Integer, TrainStop>();
        routes = new HashMap<Integer, TrainRoute>();
        trains = new HashMap<Integer, Train>();
    }

    public TrainStop getStop(int stopID) {
        if (stops.containsKey(stopID)) { return stops.get(stopID); }
        return null;
    }

    public TrainRoute getRoute(int routeID) {
        if (routes.containsKey(routeID)) { return routes.get(routeID); }
        return null;
    }

    public Train getTrain(int trainID) {
        if (trains.containsKey(trainID)) { return trains.get(trainID); }
        return null;
    }

    ////makeStop without riders
    public int makeStop(int uniqueID, String inputName, double inputXCoord, double inputYCoord) {
        stops.put(uniqueID, new TrainStop(uniqueID, inputName, inputXCoord, inputYCoord));
        return uniqueID;
    }

    //makeStop with List of Rider Objects
    public int makeStop(int uniqueID, String inputName, double inputXCoord, double inputYCoord, List<Rider> riders) {
        // int uniqueID = stops.size();
        stops.put(uniqueID, new TrainStop(uniqueID, inputName, inputXCoord, inputYCoord, riders));
        return uniqueID;
    }
    public int makeRoute(int uniqueID, int inputNumber, String inputName) {
        routes.put(uniqueID, new TrainRoute(uniqueID, inputNumber, inputName));
        return uniqueID;
    }

    public int makeTrain(int uniqueID, int inputRoute, int inputLocation, int inputCapacity, int inputSpeed) {
        trains.put(uniqueID, new Train(uniqueID, inputRoute, inputLocation, inputCapacity, inputSpeed));
        return uniqueID;
    }

    public void appendStopToRoute(int routeID, int nextStopID, int avgTravelTime) { routes.get(routeID).addNewStop(nextStopID, avgTravelTime); }

    public HashMap<Integer, TrainStop> getStops() { return stops; }

    public HashMap<Integer, TrainRoute> getRoutes() { return routes; }

    public HashMap<Integer, Train> getTrains() { return trains; }
    
    public void displayModel() {
    	ArrayList<MiniPair> trainNodes, stopNodes;
    	MiniPairComparator compareEngine = new MiniPairComparator();
    	
    	int[] colorScale = new int[] {9, 29, 69, 89, 101};
    	String[] colorName = new String[] {"#000077", "#0000FF", "#000000", "#770000", "#FF0000"};
    	Integer colorSelector, colorCount, colorTotal;
    	
    	try{
            // create new file access path
            String path="./mts_train_digraph.dot";
            File file = new File(path);

            // create the file if it doesn't exist
            if (!file.exists()) { file.createNewFile();}

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            
            bw.write("digraph G\n");
            bw.write("{\n");
    	
            trainNodes = new ArrayList<MiniPair>();
            /*
            TODO: Discuss with team
            */
            for (Train b: trains.values()) { trainNodes.add(new MiniPair(b.getID(), b.getNumberOfPassengers(),b.getSimulationTime())); }
            Collections.sort(trainNodes, compareEngine);


            colorSelector = 0;
            colorCount = 0;
            colorTotal = trainNodes.size();
            for (MiniPair c: trainNodes) {
            	if (((int) (colorCount++ * 100.0 / colorTotal)) > colorScale[colorSelector]) { colorSelector++; }
            	//System.out.println("  train" + c.getID() + " [ label=\"train#" + c.getID() + " | " + c.getValue() + " riding | time is "+c.getTime()+"\", color=\"#000077 \"];\n");
            	bw.write("  train" + c.getID() + " [ label=\"train#" + c.getID() + " | " + c.getValue() + " riding | time is "+c.getTime()+"\", color=\"#000077 \"];\n");
            }
            bw.newLine();
            
            stopNodes = new ArrayList<MiniPair>();
            /*
            TODO: Check with team
            */
            for (TrainStop s: stops.values()) { stopNodes.add(new MiniPair(s.getID(), s.getNumberOfRiders())); }
            Collections.sort(stopNodes, compareEngine);

            colorSelector = 0;
            colorCount = 0;
            colorTotal = stopNodes.size();    	
            for (MiniPair t: stopNodes) {
            	if (((int) (colorCount++ * 100.0 / colorTotal)) > colorScale[colorSelector]) { colorSelector++; }
            	bw.write("  stop" + t.getID() + " [ label=\"stop#" + t.getID() + " | " + t.getValue() + " waiting\", color=\"#FF0000\"];\n");
            }
            bw.newLine();
            
            for (Train m: trains.values()) {
            	Integer prevStop = routes.get(m.getRouteID()).getStopID(m.getPastLocation());
            	Integer nextStop = routes.get(m.getRouteID()).getStopID(m.getLocation());
            	bw.write("  stop" + Integer.toString(prevStop) + " -> train" + Integer.toString(m.getID()) + " [ label=\" dep\" ];\n");
            	bw.write("  train" + Integer.toString(m.getID()) + " -> stop" + Integer.toString(nextStop) + " [ label=\" arr\" ];\n");
            }
    	
            bw.write("}\n");
            bw.close();
    	} catch (Exception e) {
    		System.out.println(e);
    	}
    }
}
