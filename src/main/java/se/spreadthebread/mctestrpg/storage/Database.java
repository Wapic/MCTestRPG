package se.spreadthebread.mctestrpg.storage;

import java.util.UUID;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bukkit.entity.Player;

import se.spreadthebread.mctestrpg.App;
import se.spreadthebread.mctestrpg.skills.Skill;

import static com.mongodb.client.model.Filters.*;

/**
 * SavePlayer
 */
public class Database {

    private MongoCollection<Document> collection;
    private MongoDatabase db;
    private MongoClient client;

    public Database(String address, int port, String username, String password, String database) {
        client = MongoClients.create("mongodb+srv://" + username + ":" + password + "@" +
        address + "/?retryWrites=true&w=majority");
        db = client.getDatabase(database);
        collection = db.getCollection("players");
    }
 
    /**
     * Update users experience or create if they don't exist in the database
     * @param player object
     * @param playerExp PlayerExp
     * @param pData PlayerData
     */
    public void updateUserExp(Player player, PlayerExp playerExp) {
        Document doc = new Document("uuid", player.getUniqueId());
        if(collection.find(doc).first() == null) {
            createUser(player, playerExp);
            return;
        }
        doc.put("Name", player.getDisplayName());
        for(Skill skill: App.skillManager.skills) {
            doc.put(skill.getName(), skill.getCurrentExp(player));
        }
        collection.replaceOne(eq("uuid", player.getUniqueId()), doc);  
    }

    /** 
    Create user if none was found when updateUserExp() was called
    */
    public void createUser(Player player, PlayerExp playerExp){
        Document doc = new Document("uuid", player.getUniqueId());
        doc.put("Name", player.getDisplayName());
        for(Skill skill: App.skillManager.skills) {
            doc.put(skill.getName(), skill.getCurrentExp(player));
        }
        collection.insertOne(doc);
    }

    /**
     * Get the user experience if they exist in the database otherwise return default exp values
     * @param uuid
     * @return Experience from database or default exp if none existant
     */
    public PlayerExp getUserExp(UUID uuid) {
        Document doc = new Document("uuid", uuid);
        FindIterable<Document> iterableDoc = collection.find(doc);
        if(iterableDoc.first() == null){
            return PlayerExp.builder()
            .combatExp(1)
            .diggingExp(1)
            .miningExp(1)
            .woodcuttingExp(1)
            .smithingExp(1)
            .meleeExp(1)
            .rangedExp(1)
            .magicExp(1)
            .defenseExp(1)
            .build();
        }
        return PlayerExp.builder()
        .combatExp(iterableDoc.first().getInteger("Combat"))
        .diggingExp(iterableDoc.first().getInteger("Digging"))
        .miningExp(iterableDoc.first().getInteger("Mining"))
        .woodcuttingExp(iterableDoc.first().getInteger("Woodcutting"))
        .smithingExp(iterableDoc.first().getInteger("Smithing"))
        .meleeExp(iterableDoc.first().getInteger("Melee"))
        .rangedExp(iterableDoc.first().getInteger("Ranged"))
        .magicExp(iterableDoc.first().getInteger("Magic"))
        .defenseExp(iterableDoc.first().getInteger("Defense"))
        .build();
    }
}