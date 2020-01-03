package se.spreadthebread.mctestrpg.skills;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceExtractEvent;
import se.spreadthebread.mctestrpg.storage.PlayerData;

/**
 * Smithing
 */
public class Smithing extends Skill implements Listener {

    PlayerData pData;

    public Smithing(PlayerData pData) {
        super("Smithing", Material.FURNACE, 8, 1, pData);
        this.pData = pData;
    }

    @Override
    public int getCurrentExp(Player player) {
        return pData.getPlayerExp(player.getUniqueId()).getSmithingExp();
    }

    @Override
    public int getCurrentLevel(Player player) {
        return pData.xpToLevel(getCurrentExp(player));
    }

    @EventHandler
    public void event(FurnaceExtractEvent event) {
        int exp = 4 * event.getItemAmount();
        int level = getCurrentLevel(event.getPlayer());
        getPlayerExp(event.getPlayer()).setSmithingExp(getCurrentExp(event.getPlayer()) + event.getExpToDrop() + exp);
        levelUpEvent(event.getPlayer(), level);
    }
}