package main.java;

import main.java.organisms.Organism;
import main.java.utils.Pair;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public class RectangleWorld extends World implements Serializable {

    private final Pair[] directions = {new Pair(0, 1), new Pair(1, 0), new Pair(0, -1), new Pair(-1, 0)};

    public RectangleWorld(int width, int height) {
        this.width = width;
        this.height = height;
        turn = 0;
        organisms = new ArrayList<>();
        logs = "Zwykly swiat\n";
    }

    public Pair[] getMoves() {
        return directions;
    }
}


