package se.spreadthebread.mctestrpg.skills;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import se.spreadthebread.mctestrpg.App;
import se.spreadthebread.mctestrpg.storage.PlayerData;
import se.spreadthebread.mctestrpg.storage.PlayerExp;

public abstract class Skill implements Listener{

    private final int id;
    private final String name;
    private final Material item;
    private final int defaultExp;
    PlayerData pData;
    private final boolean isCombatSkill;

    public Skill(final String name, final Material item, final int id, final int defaultExp, PlayerData pData, final boolean isCombatSkill){
        this.id = id;
        this.name = name;
        this.item = item;
        this.defaultExp = defaultExp;
        this.pData = pData;
        this.isCombatSkill = isCombatSkill;
    }

    /**
     * Gets the skill id 
     * @return Skill id
     */
    public int getId(){
        return id;
    }

    /**
     * Check if skill is a combat skill
     * @return
     */
    public boolean isCombatSkill(){
        return isCombatSkill;
    }

    /**
     * Gets the skill name 
     * @return Skill name
     */
    public String getName(){
        return name;
    }

    /**
     * Gets the default experience for this skill
     * @return Skills default experience
     */
    public int getDefaultExp(){
        return defaultExp;
    }

    /**
     * Sets the combat experience of the player to be equal to all combat skills divided by 4
     * @param player
     */
    public void setCombatExp(Player player){
        int level = pData.xpToLevel(getPlayerExp(player).getCombatExp());
        int exp = 0;
        int count = 0;
        for(Skill s : App.skillManager.skills){
            if(s.isCombatSkill){
                exp = exp + s.getCurrentExp(player);
                count++;
            }
        }
        getPlayerExp(player).setCombatExp(exp / count);
        levelUpEvent(player, level);
    }

    /**
     * TODO: improve code
     * Check if previousLevel(level before applying experience) is greater than the current level
     * if so notify the player that they have leveled up
     * @param player object
     * @param previousLevel level before experience is applied
     */
    public void levelUpEvent(Player player, int previousLevel){
        if(previousLevel < getCurrentLevel(player)){
            player.sendTitle("Your" + getName() + " level is now " + getCurrentLevel(player) + "!", "Congratulations!", 10, 60, 10);
            player.spawnParticle(Particle.FIREWORKS_SPARK, player.getLocation(), 10);
            player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_BLAST, 1f, 1f);
        }
    }

    /**
     * Gets players current experience
     * @param player object
     * @return player experience
     */
    public PlayerExp getPlayerExp(Player player){
        return pData.getPlayerExp(player.getUniqueId());
    }
    
    /**
     * Gets the current experience for this skill
     * @param player object
     * @return Skills current experience
     */
    public abstract int getCurrentExp(Player player);
    
    /**
     * Gets the current level for this skill
     * @param player object
     * @return Skills current level
     */
    public int getCurrentLevel(Player player) {
        return pData.xpToLevel(getCurrentExp(player));
    }
    /**
     * Gets the Item used to represent this skill
     * @return Skill item
     */
    public Material getItem(){
        return item;
    }

}