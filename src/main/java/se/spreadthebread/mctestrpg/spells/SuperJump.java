package se.spreadthebread.mctestrpg.spells;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * SuperJump
 */
public class SuperJump extends Spell {

	public SuperJump() {
		super(2, "Super Jump", "LLL", 1);
	}

	@Override
	public void getEffect(Player player) {
		player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 5, 5));
		//player.setVelocity(V)?
	}
	
}