package edu.gatech;

public class MiniPair {
    private Integer pairID;
    private Integer pairValue;

    public MiniPair(int inputID, int inputValue) {
        pairID = inputID;
        pairValue = inputValue;
    }

    public MiniPair() {
        pairID = 0;
        pairValue = 0;
    }

    public Integer getID() { return pairID; }
    public Integer getValue() { return pairValue; }
}
