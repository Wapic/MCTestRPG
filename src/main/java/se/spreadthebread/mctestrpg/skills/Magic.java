package se.spreadthebread.mctestrpg.skills;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import se.spreadthebread.mctestrpg.App;
import se.spreadthebread.mctestrpg.items.CustomItem;
import se.spreadthebread.mctestrpg.spells.Spell;
import se.spreadthebread.mctestrpg.storage.PlayerData;

/**
 * Magic
 */
public class Magic extends Skill implements Listener {

    PlayerData pData;
    HashMap<UUID, String> combo = new HashMap<UUID, String>();
    BossBar boss = Bukkit.createBossBar("NO COMBO FOUND", BarColor.GREEN, BarStyle.SEGMENTED_6, BarFlag.PLAY_BOSS_MUSIC);
    BukkitTask task;

    public Magic(PlayerData pData) {
        super("Magic", Material.STICK, 7, 1, pData);
        this.pData = pData;
    }

    @Override
    public int getCurrentExp(Player player) {
        return pData.getPlayerExp(player.getUniqueId()).getMagicExp();
    }

    @Override
    public int getCurrentLevel(Player player) {
        return pData.xpToLevel(getCurrentExp(player));
    }

    public String getCombo(Player player) {
        return combo.get(player.getUniqueId());
    }

    public void addCombo(Player player, String action){
        String nextAction = getCombo(player) == null ? action : getCombo(player) + action;
        displayCombo(player, nextAction);
        combo.put(player.getUniqueId(), nextAction);
        if(task != null) {
            task.cancel();
        }
    }

    public void clearCombo(Player player) {
        if(getCombo(player) != null && getCombo(player).length() >= 3) {
            combo.remove(player.getUniqueId());
            boss.removePlayer(player);
        }
    }

    public void displayCombo(Player player, String nextAction){
        if(boss.getPlayers().contains(player)){
            boss.removePlayer(player);
        }
        boss.setTitle(nextAction);
        boss.setProgress(nextAction.length() / 3);
        boss.addPlayer(player);
    }

    @EventHandler
    public void event(PlayerInteractEvent event) {
        if(!event.getMaterial().equals(Material.STICK)) return;
        if(!event.getItem().hasItemMeta()) return;
        for(CustomItem i : App.itemManager.item){
            if(!event.getItem().getItemMeta().getDisplayName().equals(i.getName())) return;
        }
        Player player = event.getPlayer();
        clearCombo(player);

        switch (event.getAction()) {
        case RIGHT_CLICK_AIR:
            addCombo(player, "R");
            player.playSound(player.getLocation(), Sound.BLOCK_LEVER_CLICK, 1f, 1f);
            break;
        case RIGHT_CLICK_BLOCK:
            addCombo(player, "R");
            player.playSound(player.getLocation(), Sound.BLOCK_LEVER_CLICK, 1f, 1f);
            break;
        case LEFT_CLICK_AIR:
            addCombo(player, "L");
            player.playSound(player.getLocation(), Sound.BLOCK_LEVER_CLICK, 1f, 1f);
            break;
        case LEFT_CLICK_BLOCK:
            addCombo(player, "L");
            player.playSound(player.getLocation(), Sound.BLOCK_LEVER_CLICK, 1f, 1f);
            break;
        default:
            System.out.println("Not a valid magic action");
            return;
        }

        for(Spell s : App.spellManager.spells) {
            if(getCombo(player).equals(s.getCombo())){
                int level = getCurrentLevel(player);
                if(s.getRequiredLevel() > level) return;
                s.getEffect(player);
                getPlayerExp(player).setMagicExp(getCurrentExp(player) + 5);
                levelUpEvent(player, level);
                clearCombo(player);
            }
        }

        task = new BukkitRunnable(){
            @Override
            public void run() {
                combo.remove(player.getUniqueId());
                boss.removePlayer(player);
            }
        }.runTaskLater(App.getPlugin(App.class), 80);

    }
}