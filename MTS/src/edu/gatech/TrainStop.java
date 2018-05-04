package edu.gatech;

import java.util.ArrayList;
import java.util.List;

public class TrainStop extends VehicleStop {

    public TrainStop() {
        this.ID = -1;
    }

    public TrainStop(int uniqueValue) {
        this.ID = uniqueValue;
        this.stopName = "";
        this.xCoord = 0.0;
        this.yCoord = 0.0;
    }

    public TrainStop(int uniqueValue, String inputName, double inputXCoord, double inputYCoord) {
        this.ID = uniqueValue;
        this.stopName = inputName;
        this.xCoord = inputXCoord;
        this.yCoord = inputYCoord;
        this.riders = new ArrayList<Rider>();
    }

    public TrainStop(int uniqueValue, String inputName, double inputXCoord, double inputYCoord, List<Rider> riders)
    {
        this.ID = uniqueValue;
        this.stopName = inputName;
        this.xCoord = inputXCoord;
        this.yCoord = inputYCoord;
        this.riders = riders;
    }

}
