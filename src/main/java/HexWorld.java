package main.java;

import main.java.organisms.Organism;
import main.java.utils.Pair;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class HexWorld extends World implements Serializable {
    private final Pair[] directions = {new Pair(0, 1), new Pair(1, 0), new Pair(0, -1), new Pair(-1, 0), new Pair(-1,1), new Pair(1,-1)};

    public HexWorld(int width, int height) {
        this.width = width;
        this.height = height;
        turn = 0;
        organisms = new ArrayList<>();
        logs = "Heksagonalny swiat\n";
    }


    public Pair[] getMoves() {
        return directions;
    }
}


