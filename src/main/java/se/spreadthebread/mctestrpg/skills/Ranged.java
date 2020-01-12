package se.spreadthebread.mctestrpg.skills;

import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import se.spreadthebread.mctestrpg.storage.PlayerData;

/**
 * Ranged
 */
public class Ranged extends Skill implements Listener{

    PlayerData pData;

    public Ranged(PlayerData pData) {
        super("Ranged", Material.BOW, 1, 1, pData, true);
        this.pData = pData;
    }

    @Override
    public int getCurrentExp(Player player) {
        return pData.getPlayerExp(player.getUniqueId()).getRangedExp();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void event(EntityDamageByEntityEvent event){
        if(event.getDamager() instanceof Arrow) {
            Arrow arrow = (Arrow) event.getDamager();
            if(event.getCause().equals(DamageCause.PROJECTILE) && arrow.getShooter() instanceof Player) {
                int exp = (int) event.getFinalDamage();
                Player player = (Player) arrow.getShooter();
                int level = getCurrentLevel(player);
                getPlayerExp(player).setRangedExp(getCurrentExp(player) + exp);
                levelUpEvent(player, level);
            }
        }
    }
}