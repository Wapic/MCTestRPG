package se.spreadthebread.mctestrpg.skills;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import se.spreadthebread.mctestrpg.storage.PlayerData;

/**
 * Digging
 */
public class Digging extends Skill implements Listener {

    ArrayList<Material> mats = new ArrayList<Material>();
    PlayerData pData;

    public Digging(PlayerData pData) {
        super("Digging", Material.STONE_SHOVEL, 4, 1, pData, false);
        this.pData = pData;
        initDiggingMats();
    }

    @Override
    public int getCurrentExp(Player player) {
        return pData.getPlayerExp(player.getUniqueId()).getDiggingExp();
    }

    @Override
    public int getCurrentLevel(Player player) {
        return pData.xpToLevel(getCurrentExp(player));
    }

    public void initDiggingMats() {
        mats.add(Material.DIRT);
        mats.add(Material.GRAVEL);
        mats.add(Material.GRASS);
        mats.add(Material.SAND);
        mats.add(Material.CLAY);
        mats.add(Material.SOUL_SAND);
    }

    @EventHandler
    public void event(BlockBreakEvent event) {
        if(!mats.contains(event.getBlock().getType())) return;
        int level = getCurrentLevel(event.getPlayer());
        getPlayerExp(event.getPlayer()).setDiggingExp(getCurrentExp(event.getPlayer()) + 4);
        levelUpEvent(event.getPlayer(), level);
    }
}