package edu.gatech;

import java.util.Comparator;
import java.util.PriorityQueue;

public class SimQueue {
    private static PriorityQueue<SimEvent> eventQueue;
    private Comparator<SimEvent> simComparator;
  //  final static Integer passengerFrequency = 3;

    public SimQueue() {
        simComparator = new SimEventComparator();
        eventQueue = new PriorityQueue<SimEvent>(100, simComparator);
    }

    public void triggerNextEvent(BusSystem busModel, TrainSystem trainModel) {
        if (eventQueue.size() > 0) {
            SimEvent activeEvent = eventQueue.poll();
            activeEvent.displayEvent();
            switch (activeEvent.getType()) {
                case "move_bus":
                    Bus activeBus = busModel.getBus(activeEvent.getID());
                    System.out.print(" the bus being observed is: " + Integer.toString(activeBus.getID())+". ");
                    System.out.println("No of passenger in the bus: " + Integer.toString(activeBus.passengers.size()));


                    // identify the current stop
                    BusRoute activeRoute = busModel.getRoute(activeBus.getRouteID());
                    System.out.println(" the bus is driving on route: " + Integer.toString(activeRoute.getID()));


                    if (activeRoute.getLength() > 0) {
                        int activeLocation = activeBus.getLocation();
                        int activeStopID = activeRoute.getStopID(activeLocation);
                        BusStop activeStop = busModel.getStop(activeStopID);
                        System.out.print(" the bus is currently at stop: " + Integer.toString(activeStop.getID()) + " - " + activeStop.getName());
                        System.out.println(". No of riders on the stop: " + Integer.toString(activeStop.riders.size()));

                        // drop off and pickup new passengers at current stop

                        //TODO: Discuss with team - DONE
                        activeStop.exchangeRiders(activeBus);
                        System.out.println("After rider exchange, No. of passengers on the bus: "+ Integer.toString(activeBus.passengers.size())+ ". No. Of riders at the stop: "+ Integer.toString(activeStop.riders.size()));

                        /*
                        int currentPassengers = activeBus.getPassengers();
                        int passengerDifferential = activeStop.exchangeRiders(activeEvent.getRank(), currentPassengers, activeBus.getCapacity());
                        System.out.println(" passengers pre-stop: " + Integer.toString(currentPassengers) + " post-stop: " + (currentPassengers + passengerDifferential));
                        activeBus.adjustPassengers(passengerDifferential);
                        */

                        // determine next stop
                        int nextLocation = activeRoute.getNextLocation(activeLocation);
                        int nextStopID = activeRoute.getStopID(nextLocation);
                        BusStop nextStop = busModel.getStop(nextStopID);
                        System.out.println(" the bus is heading to stop: " + Integer.toString(nextStopID) + " - " + nextStop.getName() + "\n");

                        // find distance to stop to determine next event time
                        int avgtravelTime =(int)((double)((activeRoute.stopsOnRoute.get(nextLocation))[1]) * (double)((activeRoute.stopsOnRoute.get(nextLocation))[2])/100);
                        int travelTime = (int)(avgtravelTime * SimDriver.getSimDriver().getBustrafficFactor() * SimDriver.getSimDriver().getBusspeedFactor() );
                        activeBus.setLocation(nextLocation);
                        /* //original code - relying on distance
                        Double travelDistance = activeStop.findDistance(nextStop);
                        // conversion is used to translate time calculation from hours to minutes
                        int travelTime = 1 + (travelDistance.intValue() * 60 / activeBus.getSpeed());
                        activeBus.setLocation(nextLocation);
                        */

                        // generate next event for this bus
                        activeBus.setSimulationTime(activeEvent.getRank());
                        eventQueue.add(new SimEvent(activeEvent.getRank() + travelTime, "move_bus", activeEvent.getID()));
                    }
                    break;
                case "move_train":
                    Train activeTrain = trainModel.getTrain(activeEvent.getID());
                    System.out.print(" the train being observed is: " + Integer.toString(activeTrain.getID()));
                    System.out.println(". No of passenger in the train: " + Integer.toString(activeTrain.passengers.size()));

                    // identify the current stop
                    TrainRoute activeTrainRoute = trainModel.getRoute(activeTrain.getRouteID());
                    System.out.println(" the train is driving on route: " + Integer.toString(activeTrainRoute.getID()));

                    if (activeTrainRoute.getLength() > 0) {
                        int activeTrainLocation = activeTrain.getLocation();
                        int activeTrainstopID = activeTrainRoute.getStopID(activeTrainLocation);
                        TrainStop activeTrainStop = trainModel.getStop(activeTrainstopID);
                        System.out.print(" the train is currently at stop: " + Integer.toString(activeTrainStop.getID()) + " - " + activeTrainStop.getName());
                        System.out.println(". No of riders on the stop: " + Integer.toString(activeTrainStop.riders.size()));

                        // drop off and pickup new passengers at current stop

                        //TODO: Discuss with team - DONE
                        activeTrainStop.exchangeRiders(activeTrain);
                        System.out.println("After rider exchange, No. of passengers on the train: "+ Integer.toString(activeTrain.passengers.size())+ ". No. Of riders at the stop: "+ Integer.toString(activeTrainStop.riders.size()));
                        /*
                        int currentPassengers = activeBus.getPassengers();
                        int passengerDifferential = activeStop.exchangeRiders(activeEvent.getRank(), currentPassengers, activeBus.getCapacity());
                        System.out.println(" passengers pre-stop: " + Integer.toString(currentPassengers) + " post-stop: " + (currentPassengers + passengerDifferential));
                        activeBus.adjustPassengers(passengerDifferential);
                        */

                        // determine next stop
                        int nextTrainLocation = activeTrainRoute.getNextLocation(activeTrainLocation);
                        int nextTrainStopID = activeTrainRoute.getStopID(nextTrainLocation);
                        TrainStop nextTrainStop = trainModel.getStop(nextTrainStopID);
                        System.out.println(" the train is heading to stop: " + Integer.toString(nextTrainStopID) + " - " + nextTrainStop.getName() + "\n");

                        // find distance to stop to determine next event time
                        int avgtraveltrainTime = (int)((double)((activeTrainRoute.stopsOnRoute.get(nextTrainLocation))[1]) * (double)((activeTrainRoute.stopsOnRoute.get(nextTrainLocation))[2])/100);
                        int travelTrainTime = (int)(avgtraveltrainTime * SimDriver.getSimDriver().getTrainSpeedFactor() ); //traffic is not a factor for train, speed may vary because of weather factor(train travels slower on wet tracks)
                        activeTrain.setLocation(nextTrainLocation);
                        /* //original code - relying on distance
                        Double travelDistance = activeStop.findDistance(nextStop);
                        // conversion is used to translate time calculation from hours to minutes
                        int travelTime = 1 + (travelDistance.intValue() * 60 / activeBus.getSpeed());
                        activeBus.setLocation(nextLocation);
                        */

                        // generate next event for this bus
                        activeTrain.setSimulationTime(activeEvent.getRank());
                        eventQueue.add(new SimEvent(activeEvent.getRank() + travelTrainTime, "move_train", activeEvent.getID()));
                    }
                    break;
                default:
                    System.out.println(" event not recognized");
                    break;
            }
        } else {
            System.out.println(" event queue empty");
        }
    }

    public void addNewEvent(Integer eventRank, String eventType, Integer eventID) {
        eventQueue.add(new SimEvent(eventRank, eventType, eventID));
    }


}

