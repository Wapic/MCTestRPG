package se.spreadthebread.mctestrpg.troll;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

/**
 * TrollInventoryEvent
 */
public class TrollInventory extends Trolls implements Listener {

    private ArrayList<UUID> activeTargets = new ArrayList<UUID>();
    private ArrayList<Inventory> randomInv = new ArrayList<Inventory>();
    private ArrayList<Material> invMaterials = new ArrayList<Material>();

    public TrollInventory() {
        super("Random Inventories", Material.CHEST, "A random inventory will open when interacting with chests, anvils, furnaces, etc.", 0);
        initInventories();
    }

    private void initInventories() {
        for (InventoryType inv : InventoryType.values()) {
            if(inv.isCreatable()) {
                randomInv.add(Bukkit.createInventory(null, inv));
            }
        }
        invMaterials.add(Material.CHEST);
        invMaterials.add(Material.ANVIL);
        invMaterials.add(Material.CRAFTING_TABLE);
        invMaterials.add(Material.ENCHANTING_TABLE);
        invMaterials.add(Material.BEACON);
        invMaterials.add(Material.DROPPER);
        invMaterials.add(Material.BREWING_STAND);
        invMaterials.add(Material.HOPPER);
        invMaterials.add(Material.PINK_SHULKER_BOX);
        invMaterials.add(Material.ENDER_CHEST);
        invMaterials.add(Material.DISPENSER);
    }

    @Override
    public boolean checkTarget(Player target) {
        return activeTargets.contains(target.getUniqueId());
    }

    @Override
    public void removeTarget(Player target) {
        activeTargets.remove(target.getUniqueId());
    }

    @Override
    public void addTarget(Player target) {
        activeTargets.add(target.getUniqueId());
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if(!checkTarget(event.getPlayer())) return;
        if(!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        if(!invMaterials.contains(event.getClickedBlock().getType())) return;
        event.setCancelled(true);
        event.getPlayer().openInventory(randomInv.get(new Random().nextInt(randomInv.size() - 1)));
    }
}