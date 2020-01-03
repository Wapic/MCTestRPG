package se.spreadthebread.mctestrpg.troll;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
/**
 * TrollEvent
 */
public abstract class Trolls implements Listener{

    private final String name;
    private final Material item;
    private final String description;
    private final int id;

    public Trolls(final String name, final Material item, final String description, final int id){
        this.name = name;
        this.item = item;
        this.description = description;
        this.id = id;
    }

    /**
     * Get the name of the troll
     * @return troll name
     */
    public String getName(){
        return name;
    }

    /**
     * Gets the item used to represent troll
     * @return Material representing troll
     */
    public Material getItem(){
        return item;
    }

    /**
     * Gets the description of the troll
     * @return description of troll
     */
    public String getDescription(){
        return description;
    }

    /**
     * Gets the Id of the troll
     * @return Troll id
     */
    public int getId(){
        return id;
    }

    public abstract boolean checkTarget(Player target);
    public abstract void addTarget(Player target);
    public abstract void removeTarget(Player target);
}