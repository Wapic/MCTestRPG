package se.spreadthebread.mctestrpg.gui;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import se.spreadthebread.mctestrpg.App;
import se.spreadthebread.mctestrpg.troll.TrollDrop;
import se.spreadthebread.mctestrpg.troll.Trolls;
import se.spreadthebread.mctestrpg.troll.TrollInventory;

/**
 * TrollGuiEvent
 */
public class TrollGui implements InventoryHolder,Listener{

    private Inventory inv;
    private TrollDrop trollDrop;
    private TrollInventory trollInv;
    private Player target;

    public TrollGui(TrollDrop trollDrop, TrollInventory trollInv){
        this.trollDrop = trollDrop;
        this.trollInv = trollInv;
        inv = Bukkit.createInventory(this, 9, "Troll Options");
        initItems();
    }

    public void setTarget(Player target) {
        this.target = target;
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }

    private ItemStack createItem(Material material, String name, String...description){
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        ArrayList<String> metalore = new ArrayList<String>();
        for (String lore: description) {
            metalore.add(lore);
        }
        meta.setDisplayName(name);
        meta.setLore(metalore);
        item.setItemMeta(meta);
        return item;
    }

    public void openGui(Player player){
        player.openInventory(inv);
    }

    public void initItems(){
        for(Trolls trolls : App.trollManager.troll){
            inv.setItem(trolls.getId(), createItem(trolls.getItem(), trolls.getName(), trolls.getDescription()));
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        if(event.getInventory().getHolder() != this) return;
        if(event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) return;
        Player player = (Player) event.getWhoClicked();
        event.setCancelled(true);
        switch (event.getRawSlot()) {
            case 0:
                if(trollDrop.checkTarget(target)) {
                    trollDrop.removeTarget(target);
                    player.sendMessage("Deactivated random drops for " + target.getDisplayName());
                    return;
                }
                trollDrop.addTarget(target);
                player.sendMessage("Activated random drops for " + target.getDisplayName());
                return;
            case 1:
                if(trollInv.checkTarget(target)) {
                    trollInv.removeTarget(target);
                    player.sendMessage("Deactivated random inventories for " + target.getDisplayName());
                    return;
                }
                trollInv.addTarget(target);
                player.sendMessage("Activated random inventories for " + target.getDisplayName());
                return;
            default:
            break;
        }
    }
    
}