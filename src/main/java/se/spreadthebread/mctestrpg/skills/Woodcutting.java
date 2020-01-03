package se.spreadthebread.mctestrpg.skills;

import java.util.ArrayList;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import se.spreadthebread.mctestrpg.storage.PlayerData;

/**
 * WoodcuttingSkillEvent
 */
public class Woodcutting extends Skill implements Listener {

    ArrayList<Material> mats = new ArrayList<Material>();
    PlayerData pData;

    public Woodcutting(PlayerData pData) {
        super("Woodcutting", Material.STONE_AXE, 5, 1, pData);
        this.pData = pData;
        initWoodcuttingMats();
    }

    public void initWoodcuttingMats(){
        mats.add(Material.ACACIA_LOG);
        mats.add(Material.SPRUCE_LOG);
        mats.add(Material.OAK_LOG);
        mats.add(Material.BIRCH_LOG);
        mats.add(Material.DARK_OAK_LOG);
        mats.add(Material.JUNGLE_LOG);
    }
    
    @Override
    public int getCurrentExp(Player player) {
        return pData.getPlayerExp(player.getUniqueId()).getWoodcuttingExp();
    }

    @Override
    public int getCurrentLevel(Player player) {
        return pData.xpToLevel(getCurrentExp(player));
    }

    @EventHandler
    public void onPlayerCut(BlockBreakEvent event){
        if(!mats.contains(event.getBlock().getType())) return;
        int level = getCurrentLevel(event.getPlayer());
        getPlayerExp(event.getPlayer()).setWoodcuttingExp(getCurrentExp(event.getPlayer()) + 6);
        levelUpEvent(event.getPlayer(), level);
    }    
}