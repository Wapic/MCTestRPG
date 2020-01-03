package se.spreadthebread.mctestrpg.items;

import org.bukkit.Material;

/**
 * CustomItems
 */
public abstract class CustomItem {

    private final String name;
    private final int id;
    private final Material item;
    private final String lore;

    public CustomItem(final String name, final String lore,final Material item, final int id) {
        this.name = name;
        this.id = id;
        this.item = item;
        this.lore = lore;
    }

    public String getName(){
        return name;
    }

    public int getId(){
        return id;
    }

    public String getLore(){
        return lore;
    }

    public Material getItem(){
        return item;
    }
    
}