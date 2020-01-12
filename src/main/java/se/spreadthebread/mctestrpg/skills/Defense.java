package se.spreadthebread.mctestrpg.skills;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import se.spreadthebread.mctestrpg.storage.PlayerData;

/**
 * Defense
 */
public class Defense extends Skill implements Listener {

    ArrayList<DamageCause> causes = new ArrayList<DamageCause>();
    PlayerData pData;

    public Defense(PlayerData pData) {
        super("Defense", Material.SHIELD, 3, 1, pData, true);
        this.pData = pData;
        causes.add(DamageCause.ENTITY_ATTACK);
        causes.add(DamageCause.ENTITY_EXPLOSION);
        causes.add(DamageCause.ENTITY_SWEEP_ATTACK);
        causes.add(DamageCause.PROJECTILE);

    }

    @Override
    public int getCurrentExp(Player player) {
        return getPlayerExp(player).getDefenseExp();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void event(EntityDamageByEntityEvent event){
        if(!causes.contains(event.getCause())) return;
        if(event.getEntity() instanceof Player){
            int exp = (int) event.getFinalDamage();
            Player player = (Player) event.getEntity();
            int level = getCurrentLevel(player);
            getPlayerExp(player).setDefenseExp(getCurrentExp(player) + exp);
            levelUpEvent(player, level);
        }
    }
}