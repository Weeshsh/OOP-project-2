package main.java.organisms.animals;

import main.java.utils.Pair;
import main.java.organisms.Organism;

import java.util.Random;
import java.awt.Color;

public class Animal extends Organism {
    public Animal(Color color, int strength, int initiative, Pair position){
        super(color, strength, initiative, position);
    }

    public void action() {
        increaseAge();
        Pair[] moves = world.getMoves();
        int move = new Random().nextInt(moves.length);

        while(!setPosition(getPosition().add(moves[move%moves.length]))){
            move++;
            if(move == 2*moves.length){
                world.addLog(this.getClass().getSimpleName() + " nie mial gdzie sie ruszyc");
                return;
            }
        }
    }

    public void collision(Organism second) {
        if (this.getClass().equals(second.getClass())) {
            if(this.getAge() < 3 || second.getAge() < 3) {
                return;
            }

            try {
                Pair[] moves = world.getMoves();
                int move = 0;

                while (!world.isWithin(getPosition().add(moves[move])) | world.getOrganism(getPosition().add(moves[move])) != null) {
                    move++;
                    if (move == moves.length) {
                        world.addLog("Nie ma miejsca na " + this.getClass().getSimpleName());
                        return;
                    }
                }

                reverseMove();
                world.addOrganism(this.getClass().getCanonicalName(), getPosition().add(moves[move]));
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }

            world.addLog("Urodzil sie " + this.getClass().getSimpleName());
            return;
        }
        if(this.escaped() || second.escaped()){
            world.addLog("Jedno ze zwierzat ucieklo");
            return;
        }
        if(this.getStrength() >= second.getStrength()){
            world.addLog(this.getClass().getSimpleName() + " zabil " + second.getClass().getSimpleName() + " na " + this.getPosition());
            second.delete();
        } else {
            world.addLog(second.getClass().getSimpleName() + " zabil " + this.getClass().getSimpleName() + " na " + second.getPosition());
            this.delete();
        }
    }
}
