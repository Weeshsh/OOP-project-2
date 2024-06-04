package main.java.organisms;

import main.java.utils.Pair;
import main.java.World;
import main.java.organisms.animals.Human;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.awt.Color;

public abstract class Organism implements Serializable {
    private int strength;
    private final Color color;
    private final int initiative;
    private int age;
    protected World world;
    private Pair position;
    private Pair oldPosition;


    protected Organism(Color color, int strength, int initiative, Pair position){
        world = World.getActive();
        this.color = color;
        this.strength = strength;
        this.initiative = initiative;
        this.age = 0;
        this.position = position;
    }

    public static Organism create(World world, String name, Pair position) {
        try{
            if(!name.contains("main.java.organisms")){
                name = "main.java.organisms." + name;
            }
            return (Organism) Class.forName(name).getConstructor(Pair.class).newInstance(position);
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | InvocationTargetException | IllegalAccessException e){
            e.printStackTrace();
        }
        return null;
    }

    public abstract void action();

    public abstract void collision(Organism second);

    public void delete() {
        if(this instanceof Human){
            if(((Human) this).getAbilityCounter() > 5) {
                world.addLog("Czlowiek uniknal smierci!");
                this.reverseMove();
                return;
            }
        }

        position = new Pair(-1,-1);
        strength = -1;
    }

    public int getStrength() {
        return strength;
    }

    public int getAge() {
        return age;
    }

    public Pair getPosition() {
        return position;
    }

    public Color getColor() {
        return color;
    }

    public void strengthten(int str){
        this.strength += str;
    }

    public void increaseAge(){
        this.age++;
    }

    public boolean escaped() {
        return false;
    }

    public boolean setPosition(Pair position){
        if(!world.isWithin(position)){
          return false;
        }
        oldPosition = this.position;
        Organism second = world.getOrganism(position);
        if(second != null && second != this) {
            second.collision(this);
        }
        world.addLog(this.getClass().getSimpleName() + " ruch na " + position);
        this.position = position;
        return true;
    }

    public void reverseMove() {
        setPosition(oldPosition);
    }

    public int compare(Organism second){
        if(this.initiative < second.initiative){
            return 1;
        }
        if(this.initiative > second.initiative){
            return -1;
        }
        return Integer.compare(this.age, second.age);
    }
}
