package se.spreadthebread.mctestrpg.troll;

import java.util.ArrayList;

/**
 * TrollManager
 */
public class TrollManager {
    public ArrayList<Trolls> troll = new ArrayList<Trolls>();

    public void setup(){
        troll.add(new TrollDrop());
        troll.add(new TrollInventory());
    }
    
}