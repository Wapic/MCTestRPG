package se.spreadthebread.mctestrpg.spells;

import java.util.ArrayList;

/**
 * SpellManager
 */
public class SpellManager {
    public ArrayList<Spell> spells = new ArrayList<>();

    public void setup(){
        spells.add(new Blink());
        spells.add(new Heal());
        spells.add(new SuperJump());
    }
    
}