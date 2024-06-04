package main.java.organisms.animals;

import main.java.utils.Pair;
import java.awt.Color;
import java.util.Random;

public class Fox extends Animal{
    public Fox(Pair position) {
        super(new Color(255,165,0),3,7,position);
    }

    public void action(){
        increaseAge();
        Pair[] moves = world.getMoves();
        for(int i = new Random().nextInt(moves.length); i < moves.length*2; i++){
            Pair newPosition = getPosition().add(moves[i % moves.length]);

            if(!world.isWithin(newPosition)){
                continue;
            }

            if(world.getOrganism(newPosition) == null){
                setPosition(newPosition);
                return;
            }

            if(world.getOrganism(newPosition).getStrength() <= this.getStrength()){
                setPosition(newPosition);
                return;
            }
        }
        world.addLog("Lis nie mial dokad uciec");
    }
}
