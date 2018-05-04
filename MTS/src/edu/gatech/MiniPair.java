package edu.gatech;

public class MiniPair {
    private Integer pairID;
    private Integer pairValue;
    private Integer  pairTime;

    public MiniPair(int inputID, int inputValue, int inputTime) {
        pairID = inputID;
        pairValue = inputValue;
        pairTime = inputTime;
    }
    public MiniPair(int inputID, int inputValue) {
        pairID = inputID;
        pairValue = inputValue;
        pairTime = 0;
    }

    public MiniPair() {
        pairID = 0;
        pairValue = 0;
        pairTime=0;
    }

    public Integer getID() { return pairID; }
    public Integer getValue() { return pairValue; }
    public Integer getTime() { return pairTime; }
}
