package edu.gatech;

import java.util.Comparator;

public class MiniPairComparator implements Comparator<MiniPair> {
    @Override
    public int compare(MiniPair x, MiniPair y) { return x.getValue() - y.getValue(); }
}
