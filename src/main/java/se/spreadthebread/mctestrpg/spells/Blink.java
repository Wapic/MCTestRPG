package se.spreadthebread.mctestrpg.spells;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

/**
 * Blink
 */
public class Blink extends Spell {

    public Blink(){
        super(0, "Blink", "RRL", 1);
    }

    /*
    TODO: update plugin for newer version and replace code 
    */
    @Override
	public void getEffect(Player player) {
        Location loc = player.getLocation();
        Vector dir = loc.getDirection();
        if(loc.getPitch() >= 65) return;
        dir.normalize();
        dir.multiply(6);
        loc.add(dir);
        player.teleport(loc.add(0, 2, 0));
        player.playSound(loc, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 1f);
    }
}