package main.java.organisms.animals;

import main.java.utils.Pair;

import java.awt.*;

public class Human extends Animal {
    Pair nextMove;
    int abilityCounter = 0;

    public Human(Pair position) {
        super(new Color(226,187,31),5,4,position);
        nextMove = new Pair(0,0);
    }

    public void setNextMove(int a, int b){
        if(!world.isWithin(this.getPosition()))
            return;

        nextMove = new Pair(a, b);
    }

    public void action(){
        increaseAge();

        if(abilityCounter > 0){
            abilityCounter--;
        }

        if(nextMove.getFirst() == 0 && nextMove.getSecond() == 0){
            return;
        }

        setPosition(getPosition().add(nextMove));
        nextMove = new Pair(0,0);
    }

    public void activateAbility(){
        if(abilityCounter > 0){
            System.out.println("Czlowiek nie moze aktywowac umiejetnosci");
            return;
        }
        System.out.println("Czlowiek aktywowal umiejetnosc");

        abilityCounter = 9;
    }

    public int getAbilityCounter(){
        return this.abilityCounter;
    }
}
