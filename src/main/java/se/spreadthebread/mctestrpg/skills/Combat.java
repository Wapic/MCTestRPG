package se.spreadthebread.mctestrpg.skills;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import se.spreadthebread.mctestrpg.storage.PlayerData;

public class Combat extends Skill{

    PlayerData pData;

    public Combat(PlayerData pData) {
        super("Combat", Material.DIAMOND_SWORD, 0, 1, pData, false);
        this.pData = pData;
    }

    @Override
    public int getCurrentExp(Player player) {
        return pData.getPlayerExp(player.getUniqueId()).getCombatExp();
    }

}