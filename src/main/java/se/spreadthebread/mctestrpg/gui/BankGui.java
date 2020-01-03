package se.spreadthebread.mctestrpg.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import se.spreadthebread.mctestrpg.storage.PlayerData;

/**
 * BankGui
 */
public class BankGui implements Listener {
    PlayerData pData;
    private HashMap<UUID, ArrayList<ItemStack>> playerItems = new HashMap<UUID, ArrayList<ItemStack>>();
    private HashMap<UUID, Inventory> playerInventory = new HashMap<UUID, Inventory>();

    public BankGui(PlayerData pData){
        this.pData = pData;
    }

    public void openGui(Player player){
        player.openInventory(playerInventory.getOrDefault(player.getUniqueId(), Bukkit.createInventory(player, InventoryType.CHEST, "Bank")));
    }

    public void addItems(Player player){
            for(ItemStack i : playerItems.get(player.getUniqueId())){
                playerInventory.get(player.getUniqueId()).addItem(new ItemStack(i.getType(), i.getAmount()));
            }
    }


    @EventHandler
    public void quitEvent(PlayerQuitEvent event){
        ArrayList<ItemStack> items = new ArrayList<ItemStack>();
        Player player = event.getPlayer();
        for(ItemStack i : playerInventory.get(player.getUniqueId()).getContents()) {
            items.add(new ItemStack(i.getType(), i.getAmount()));
        }
        playerItems.put(player.getUniqueId(), items);
    }
    
    @EventHandler
    public void joinEvent(PlayerJoinEvent event){
        
    }
}