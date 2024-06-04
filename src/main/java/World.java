package main.java;

import main.java.organisms.Organism;
import main.java.organisms.animals.Human;
import main.java.utils.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


public abstract class World implements Serializable {
    int width;
    int height;
    int turn;
    ArrayList<Organism> organisms;
    Human human;
    String logs;

    static World active = null;
    static public World getActive(String name, int width, int height) {
        active = switch(name) {
            case "Rectangle" -> new RectangleWorld(width, height);
            case "Hexagonal" -> new HexWorld(width, height);
            default -> throw new IllegalStateException("Unexpected value: " + name);
        };

        return active;
    }
    static public World getActive() {
        return active;
    }

    public World swapType() {
        if(active instanceof RectangleWorld) {
            active = new HexWorld(active.width, active.height);
        }else{
            active = new RectangleWorld(active.width, active.height);
        }
        return active;
    }

    public static void setActive(World newWorld) {
        active = newWorld;
    }


    public void turn() {
        logs = "";
        if(getHuman().getStrength() == -1) {
            logs="Czlowiek nie zyje\n";
        } else {
            if (getHuman().getAbilityCounter() > 4) {
                logs = "Umiejetnosc czlowieka jest aktywna\n";
            } else {
                logs = "Umiejetnosc czlowieka nie jest aktywna\n";
            }
        }

        addLog("Tura: "+ ++turn + "\n");
        List<Organism> filteredOrganisms = organisms.stream()
                .filter(o -> o.getStrength() != -1)
                .collect(Collectors.toList());

        organisms = new ArrayList<>(filteredOrganisms);
        organisms.sort(Organism::compare);
        int size = organisms.size();

        for(int i = 0; i < size; i++)
            organisms.get(i).action();

    }

    public void addLog(String log) {
        logs += log + "\n";
    }

    public boolean isWithin(Pair position) {
        return position.getFirst() >= 0 &&
                position.getFirst() <= width &&
                position.getSecond() >= 0 &&
                position.getSecond() <= height;
    }

    public void addOrganism(String name, Pair position) {
        Organism temp = Organism.create(this, name, position);
        organisms.add(temp);
        addLog(temp.getClass().getSimpleName() + " zostal dodany na pole " + position.toString());
    }

    public Organism getOrganism(Pair position) {
        return organisms.stream()
                .filter(org -> org.getPosition().equals(position))
                .findFirst().orElse(null);
    }

    public Human getHuman(){
        return this.human;
    }

    public abstract Pair[] getMoves();

    void init(){
        addOrganism("animals.Human", new Pair(new Random().nextInt(width+1), new Random().nextInt(height+1)));
        this.human = (Human) organisms.get(0);
        addOrganism("animals.Fox", new Pair(new Random().nextInt(width+1), new Random().nextInt(height+1)));
        addOrganism("animals.Antelope", new Pair(new Random().nextInt(width+1), new Random().nextInt(height+1)));
        addOrganism("animals.Sheep", new Pair(new Random().nextInt(width+1), new Random().nextInt(height+1)));
        addOrganism("animals.Wolf", new Pair(new Random().nextInt(width+1), new Random().nextInt(height+1)));
        addOrganism("animals.Turtle", new Pair(new Random().nextInt(width+1), new Random().nextInt(height+1)));
        addOrganism("plants.Dandelion", new Pair(new Random().nextInt(width+1), new Random().nextInt(height+1)));
        addOrganism("plants.Grass", new Pair(new Random().nextInt(width+1), new Random().nextInt(height+1)));
        addOrganism("plants.Guarana", new Pair(new Random().nextInt(width+1), new Random().nextInt(height+1)));
        addOrganism("plants.Wolfberries", new Pair(new Random().nextInt(width+1), new Random().nextInt(height+1)));
        addOrganism("plants.HSosnowskyi", new Pair(new Random().nextInt(width+1), new Random().nextInt(height+1)));
    }
}
