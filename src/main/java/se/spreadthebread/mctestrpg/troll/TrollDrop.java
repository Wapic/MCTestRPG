package se.spreadthebread.mctestrpg.troll;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

/**
 * TrollDropEvent
 */
public class TrollDrop extends Trolls implements Listener {

    private ArrayList<Material> randomMat = new ArrayList<Material>();
    private ArrayList<UUID> activeTargets = new ArrayList<UUID>();
    
    public TrollDrop(){
        super("Random Drops", Material.DROPPER, "A random item will drop when digging", 1);
        initMaterials();
    }

    private void initMaterials(){
        for (Material mat: Material.values()) {
            if(!mat.isItem()) continue;
            randomMat.add(mat);
        }
    }

    @Override
    public boolean checkTarget(Player target) {
        return activeTargets.contains(target.getUniqueId());
    }

    @Override
    public void addTarget(Player target) {
        activeTargets.add(target.getUniqueId());
    }
    
    @Override
    public void removeTarget(Player target) {
        activeTargets.remove(target.getUniqueId());
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        if(!checkTarget(event.getPlayer())) return;
        event.setCancelled(true);
        event.getBlock().setType(Material.AIR);
        event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(randomMat.get(new Random().nextInt(randomMat.size() - 1))));
    }
}