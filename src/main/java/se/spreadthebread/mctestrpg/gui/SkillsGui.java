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
import se.spreadthebread.mctestrpg.skills.Skill;
import se.spreadthebread.mctestrpg.storage.PlayerData;

/**
 * TrollGuiEvent
 */
public class SkillsGui implements InventoryHolder,Listener{

    private Inventory inv;
    PlayerData pData;

    public SkillsGui(PlayerData pData){
        inv = Bukkit.createInventory(this, 9, "Skills");
        initItems();
        this.pData = pData;
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }

    private ItemStack createItem(Material material, String name){
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return item;
    }

    public void initItems(){
        for(Skill s : App.skillManager.skills){
            inv.setItem(s.getId(), createItem(s.getItem(), s.getName()));
        }
    }

    private void updateLore(Player player){
        for(ItemStack i : inv.getContents()){
            if(i == null) continue;
            ArrayList<String> metalore = new ArrayList<String>();
            ItemMeta meta = i.getItemMeta();
            for(Skill skill : App.skillManager.skills){
                if(!(meta.getDisplayName().equals(skill.getName()))) continue;
                metalore.add("Level: " + skill.getCurrentLevel(player));
                metalore.add("Exp: " + skill.getCurrentExp(player) + "/" + pData.levelToXP(skill.getCurrentLevel(player) + 1));
            }
            meta.setLore(metalore);
            i.setItemMeta(meta);
        }
    }

    public void openGui(Player player){
        updateLore(player);
        player.openInventory(inv);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        ItemStack item = event.getCurrentItem();
        if(event.getInventory().getHolder() != this) return;
        if(item == null || item.getType() == Material.AIR) return;
        event.setCancelled(true);
    }
    
}