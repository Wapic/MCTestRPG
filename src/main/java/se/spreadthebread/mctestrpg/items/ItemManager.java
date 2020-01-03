package se.spreadthebread.mctestrpg.items;

import java.util.ArrayList;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * ItemManager
 */
public class ItemManager {

    public ArrayList<CustomItem> item = new ArrayList<>();
    JavaPlugin plugin;

    public ItemManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void setup() {
        item.add(new Wand(plugin));
    }
    
}