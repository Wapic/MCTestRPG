package se.spreadthebread.mctestrpg.storage;

import java.util.HashMap;
import java.util.UUID;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * LoadPlayer
 */
public class PlayerData implements Listener {

    private HashMap<UUID, PlayerExp> playerExp = new HashMap<UUID, PlayerExp>();
    private Database database;

    public PlayerData(Database database) {
        this.database = database;
    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        playerExp.put(player.getUniqueId(), database.getUserExp(player.getUniqueId()));
        
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onPlayerQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        database.updateUserExp(player, getPlayerExp(player.getUniqueId()));
        playerExp.remove(player.getUniqueId());
    }

    /**
     * calculates exp
     * @param xp exp
     * @return math
     */
    public int equate(double xp){
        return (int) Math.floor(xp + 300 * Math.pow(2, xp / 7));
    }

    /**
     * Converts level to exp
     * @param level
     * @return exp
     */
    public int levelToXP(int level){
        double xp = 0;
        for(int i = 1; i < level; i++){
            xp += equate(i);
        }
        return (int) Math.floor(xp/4);
    }

    /**
     * Converts exp to Level
     * @param xp Amount of exp
     * @return level
     */
    public int xpToLevel(int xp){
        int level = 1;
        while(levelToXP(level) < xp){
            level++;
        }
        return level - 1;
    }

    /**
     * Gets all experience values for specific player
     * @param playerUUID Players UUID
     * @return Players experience
     */
    public PlayerExp getPlayerExp(UUID playerUUID){
        return playerExp.get(playerUUID);
    }
}