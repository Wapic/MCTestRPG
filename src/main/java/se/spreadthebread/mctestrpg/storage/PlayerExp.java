package se.spreadthebread.mctestrpg.storage;

import lombok.Builder;
import lombok.Data;
/**
 * PlayerExp
 */
@Builder
@Data
public class PlayerExp {
    private int combatExp;
    private int miningExp;
    private int diggingExp;
    private int woodcuttingExp;
    private int smithingExp;
    private int meleeExp;
    private int rangedExp;
    private int magicExp;
    private int defenseExp;
}