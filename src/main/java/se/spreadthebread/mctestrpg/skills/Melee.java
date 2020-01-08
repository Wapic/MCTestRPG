package se.spreadthebread.mctestrpg.skills;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import se.spreadthebread.mctestrpg.storage.PlayerData;

/**
 * Melee
 */
public class Melee extends Skill implements Listener{

    PlayerData pData;

    public Melee(PlayerData pData) {
        super("Melee", Material.STONE_SWORD, 2, 1, pData, true);
        this.pData = pData;
    }

    @Override
    public int getCurrentExp(Player player) {
        return pData.getPlayerExp(player.getUniqueId()).getMeleeExp();
    }

    @Override
    public int getCurrentLevel(Player player){
        return pData.xpToLevel(getCurrentExp(player));
    }
    
    public void event(EntityDamageByEntityEvent event){
        if(event.getDamager() instanceof Player) {
            if(event.getCause().equals(DamageCause.ENTITY_ATTACK) || event.getCause().equals(DamageCause.ENTITY_SWEEP_ATTACK)){
                int exp = (int) event.getFinalDamage();
                Player player = (Player) event.getDamager();
                int level = getCurrentLevel(player);
                getPlayerExp(player).setMeleeExp(getCurrentExp(player) + exp);
                levelUpEvent(player, level);
            } 
        }
    }
}