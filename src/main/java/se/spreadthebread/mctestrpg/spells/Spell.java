package se.spreadthebread.mctestrpg.spells;

import org.bukkit.entity.Player;

public abstract class Spell {

    private int id;
    private String name;
    private String combo;
    private int requiredLevel;

    public Spell(int id, String name, String combo, int requiredLevel){
        this.id = id;
        this.name = name;
        this.combo = combo;
        this.requiredLevel = requiredLevel;
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }
    
    public String getCombo(){
        return combo;
    }

    public int getRequiredLevel(){
        return requiredLevel;
    }

    public abstract void getEffect(Player player);
    
}