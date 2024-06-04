package main.java.organisms.plants;
import main.java.organisms.Organism;
import main.java.utils.Pair;
import java.awt.Color;

public class Guarana extends Plant{
    public Guarana(Pair position){
        super(new Color(139,69,19), 0, position);
    }

    public void collision(Organism second) {
        second.strengthten(3);
        this.delete();
    }
}
