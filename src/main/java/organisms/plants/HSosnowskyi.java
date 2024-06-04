package main.java.organisms.plants;

import main.java.utils.Pair;
import main.java.organisms.animals.Animal;
import main.java.organisms.Organism;
import java.awt.*;

public class HSosnowskyi extends Plant  {
    public HSosnowskyi(Pair position) {
        super(new Color(209,192,219), 10, position);
    }

    public void action() {
        Pair[] neighbours = world.getMoves();
        for(Pair neighbour : neighbours){
            Pair neighbourPos = getPosition().add(neighbour);
            if(world.getOrganism(neighbourPos) instanceof Animal){
                world.getOrganism(neighbourPos).delete();
            }
        }
        super.action();
    }

    public void collision(Organism second) {
        this.delete();
        second.delete();
    }
}
