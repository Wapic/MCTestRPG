package se.spreadthebread.mctestrpg.items;

import java.util.ArrayList;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Wand
 */
public class Wand extends CustomItem {
    JavaPlugin plugin;

    public Wand(JavaPlugin plugin) {
        super("Magic Wand", "A plain but functional Magic Wand", Material.STICK, 0);
        this.plugin = plugin;
    }

    public void initRecipe(){
        ItemStack item = new ItemStack(getItem());
        ItemMeta meta = item.getItemMeta();
        ArrayList<String> metalore = new ArrayList<String>();
        meta.setDisplayName(getName());
        metalore.add(getLore());
        meta.setLore(metalore);
        item.setItemMeta(meta);

        NamespacedKey key = new NamespacedKey(plugin, getName());
        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape("x", "x");

        recipe.setIngredient('x', Material.STICK);
        plugin.getServer().addRecipe(recipe);
    }
    
}