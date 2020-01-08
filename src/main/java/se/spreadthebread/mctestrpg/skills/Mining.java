package se.spreadthebread.mctestrpg.skills;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import se.spreadthebread.mctestrpg.storage.PlayerData;

/**
 * Mining
 */
public class Mining extends Skill implements Listener {

    PlayerData pData;
    ArrayList<Material> mats = new ArrayList<Material>();

    public Mining(PlayerData pData) {
        super("Mining", Material.STONE_PICKAXE, 6, 1, pData, false);
        this.pData = pData;
        initMiningMats();
    }

    @Override
    public int getCurrentExp(Player player) {
        return pData.getPlayerExp(player.getUniqueId()).getMiningExp();
    }

    @Override
    public int getCurrentLevel(Player player) {
        return pData.xpToLevel(getCurrentExp(player));
    }

    public void initMiningMats(){
        mats.add(Material.STONE);
        mats.add(Material.COAL_ORE);
        mats.add(Material.DIAMOND_ORE);
        mats.add(Material.EMERALD_ORE);
        mats.add(Material.REDSTONE_ORE);
        mats.add(Material.GOLD_ORE);
        mats.add(Material.IRON_ORE);
        mats.add(Material.LAPIS_ORE);
        mats.add(Material.NETHER_QUARTZ_ORE);
    }

    @EventHandler
    public void event(BlockBreakEvent event){
        if(!mats.contains(event.getBlock().getType())) return;
        Player player = event.getPlayer();
        int xp = event.getExpToDrop();
        int level = getCurrentLevel(player);
        getPlayerExp(player).setMiningExp(getCurrentExp(player) + 4 + xp);
        levelUpEvent(player, level);
    }
}