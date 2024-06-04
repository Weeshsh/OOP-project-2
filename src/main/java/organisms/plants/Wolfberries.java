package main.java.organisms.plants;

import main.java.organisms.Organism;
import main.java.utils.Pair;

import java.awt.*;

public class Wolfberries extends Plant{
    public Wolfberries(Pair position) {
        super(new Color(107,5,170), 99, position);
    }

    public void collision(Organism second) {
        this.delete();
        second.delete();
    }
}
