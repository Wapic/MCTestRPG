package se.spreadthebread.mctestrpg.skills;

import java.util.ArrayList;
import se.spreadthebread.mctestrpg.storage.PlayerData;

public class SkillManager {

    public ArrayList<Skill> skills = new ArrayList<Skill>();
    PlayerData pData;

    public SkillManager(PlayerData pData){
        this.pData = pData;
    }

    public void setup(){
        skills.add(new Combat(pData));
        skills.add(new Melee(pData));
        skills.add(new Ranged(pData));
        skills.add(new Magic(pData));
        skills.add(new Mining(pData));
        skills.add(new Smithing(pData));
        skills.add(new Woodcutting(pData));
        skills.add(new Digging(pData));
        skills.add(new Defense(pData));
    }
}