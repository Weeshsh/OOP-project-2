package main.java.organisms.animals;

import main.java.utils.Pair;
import main.java.organisms.Organism;

import java.awt.Color;
import java.util.Random;

public class Turtle extends Animal{
    public Turtle(Pair position) {
        super(new Color(13,135,21),2,1,position);
    }

    public void action() {
        if(new Random().nextInt(3) == 0)
            super.action();
    }

    public void collision(Organism second) {
        if(second.getStrength() < 5 && this.getClass() != second.getClass()){
            world.addLog("Zolw odparl atak");
            second.reverseMove();
            return;
        }
        super.collision(second);
    }
}
