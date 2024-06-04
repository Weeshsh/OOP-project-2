package main.java.organisms.animals;

import main.java.utils.Pair;
import java.awt.Color;
import java.util.Random;

public class Antelope extends Animal {
    public Antelope(Pair position){
        super(new Color(191,168,133),4,4,position);
    }

    public void action(){
        increaseAge();
        Pair[] moves = world.getMoves();
        int move = new Random().nextInt(moves.length);

        while(!setPosition(getPosition().add(moves[move].multiply(2)))){
            move++;
            move%=moves.length;
            if(move == 2*moves.length){
                world.addLog("Antylopa nie miala gdzie sie ruszyc");
                return;
            }
        }
    }

    public boolean escaped() {
        if(new Random().nextInt(2) != 0) {
            return false;
        }
        Pair[] moves = world.getMoves();
        int move = 0;
        Pair newPosition = moves[move];
        while(!world.isWithin(newPosition) | world.getOrganism(newPosition) != null){
            move++;
            if(move == moves.length) {
                world.addLog("Antylopa na " + getPosition() + " nie ma dokad uciec");
                return false;
            }
        }

        world.addLog("Antylopa na " + getPosition() + " uciekla");
        return true;
    }
}
