package se.spreadthebread.mctestrpg.spells;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

/**
 * SuperJump
 */
public class SuperJump extends Spell {

	public SuperJump() {
		super(2, "Super Jump", "LLL", 1);
	}

	@Override
	public void getEffect(Player player) {
		Vector sVelocity = ((Entity) player).getVelocity();
		((Entity) player).setVelocity(new Vector(sVelocity.getX(), sVelocity.getY()*5,sVelocity.getZ()));
	}

	
}