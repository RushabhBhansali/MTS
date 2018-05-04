package edu.gatech;

public class MtsMain {

    public static void main(String[] args) {
        System.out.println("Mass Transit Simulation System Starting...");
        if( null != args && args.length > 0 ) {
            if( "-ui".equalsIgnoreCase(args[0]) ) {
                MtsUi.main(null);
            }
        } else {
            SimDriver commandInterpreter = SimDriver.getSimDriver();
            commandInterpreter.runInterpreter();
        }
    }
}
