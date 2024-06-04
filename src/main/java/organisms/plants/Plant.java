package main.java.organisms.plants;

import main.java.utils.Pair;
import main.java.organisms.Organism;
import java.awt.Color;
import java.util.Random;

public class Plant extends Organism {
    public Plant(Color color, int strength, Pair position){
        super(color, strength, 0, position);
    }

    public void action() {
        increaseAge();
        Pair[] moves = world.getMoves();
        if(new Random().nextInt(99) == 0){
            for(int i = new Random().nextInt(moves.length); i <= 2*moves.length; i++) {

                if(!world.isWithin(this.getPosition().add(moves[i % moves.length])))
                    continue;
                if(world.getOrganism(this.getPosition().add(moves[i % moves.length])) == null){
                    try {
                        world.addOrganism(this.getClass().getCanonicalName(), this.getPosition().add(moves[i % moves.length]));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return;
                }
            }
        }
    }

    public void collision(Organism second) {
        if(this.getClass().equals(second.getClass()))
            return;
        if(this.getStrength() >= second.getStrength()){
            world.addLog(this.getClass().getSimpleName() + " zdeptal " + second.getClass().getSimpleName());
            second.delete();
        } else {
            world.addLog(second.getClass().getSimpleName() + " zdeptal " + this.getClass().getSimpleName());
            this.delete();
        }
    }
}
