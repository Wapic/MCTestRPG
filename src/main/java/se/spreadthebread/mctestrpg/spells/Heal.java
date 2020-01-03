package se.spreadthebread.mctestrpg.spells;

import org.bukkit.entity.Player;

/**
 * Heal
 */
public class Heal extends Spell{

    public Heal() {
        super(1, "Heal", "LLR", 1);
    }

    @Override
    public void getEffect(Player player) {
        if(player.getHealth() >= 17){
            player.setHealth(20);
            return;
        }
        player.setHealth(player.getHealth() + 3);
    }
}