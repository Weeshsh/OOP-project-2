package main.java.utils;

import java.io.Serializable;

public class Pair implements Serializable {
    private final int first;
    private final int second;

    public Pair(int first, int second) {
        this.first = first;
        this.second = second;
    }

    public int getFirst() {
        return first;
    }

    public int getSecond() {
        return second;
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }

    public boolean equals(Pair second) {
        return this.first == second.first && this.second == second.second;
    }

    public Pair add(Pair second) {
        return new Pair(this.first + second.first, this.second + second.second);
    }

    public Pair multiply(int multiplier){
        return new Pair(this.first * multiplier, this.second * multiplier);
    }
}
