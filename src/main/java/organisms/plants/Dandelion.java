package main.java.organisms.plants;

import main.java.utils.Pair;

import java.awt.*;

public class Dandelion extends Plant {
    public Dandelion(Pair position){
        super(Color.YELLOW, 0, position);
    }

    public void action() {
        for(int i = 0; i < 3; i++) {
            super.action();
        }
    }
}
