package edu.gatech;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.HashMap;

public class BusStop extends VehicleStop {

    public BusStop() {
        this.ID = -1;
    }

    public BusStop(int uniqueValue){
        this.ID = uniqueValue;
        this.stopName = "";
        this.xCoord = 0.0;
        this.yCoord = 0.0;
    }

    public BusStop(int uniqueValue, String inputName, double inputXCoord, double inputYCoord) {
        this.ID = uniqueValue;
        this.stopName = inputName;
        this.xCoord = inputXCoord;
        this.yCoord = inputYCoord;
        this.riders = new ArrayList<Rider>();
    }

    public BusStop(int uniqueValue, String inputName, double inputXCoord, double inputYCoord, List<Rider> riders)
    {
        this.ID = uniqueValue;
        this.stopName = inputName;
        this.xCoord = inputXCoord;
        this.yCoord = inputYCoord;
        this.riders = riders;
    }

}
