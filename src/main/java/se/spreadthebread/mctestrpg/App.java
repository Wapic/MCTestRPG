package se.spreadthebread.mctestrpg;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import se.spreadthebread.mctestrpg.commands.Bank;
import se.spreadthebread.mctestrpg.commands.Skills;
import se.spreadthebread.mctestrpg.commands.Troll;
import se.spreadthebread.mctestrpg.gui.BankGui;
import se.spreadthebread.mctestrpg.gui.SkillsGui;
import se.spreadthebread.mctestrpg.gui.TrollGui;
import se.spreadthebread.mctestrpg.items.ItemManager;
import se.spreadthebread.mctestrpg.skills.Skill;
import se.spreadthebread.mctestrpg.skills.SkillManager;
import se.spreadthebread.mctestrpg.spells.SpellManager;
import se.spreadthebread.mctestrpg.storage.Database;
import se.spreadthebread.mctestrpg.storage.PlayerData;
import se.spreadthebread.mctestrpg.troll.Trolls;
import se.spreadthebread.mctestrpg.troll.TrollDrop;
import se.spreadthebread.mctestrpg.troll.TrollInventory;
import se.spreadthebread.mctestrpg.troll.TrollManager;

public class App extends JavaPlugin {
    PlayerData pData;
    Database database;
    
    TrollGui trollGui;
    SkillsGui skillsGui;
    BankGui bankGui;
    TrollDrop trollDrop;
    TrollInventory trollInventory;
    public static SpellManager spellManager;
    public static SkillManager skillManager;
    public static TrollManager trollManager;
    public static ItemManager itemManager;


    @Override
    public void onEnable() {
        //generate default config file
        getConfig().addDefault("Database.address", "localhost");
        getConfig().addDefault("Database.port", 9999);
        getConfig().addDefault("Database.username", "root");
        getConfig().addDefault("Database.password", "root");
        getConfig().addDefault("Database.table", "PlayerData");
        saveDefaultConfig();


        database = new Database(
        getConfig().getString("Database.address"),
        getConfig().getInt("Databse.port"), 
        getConfig().getString("Database.username"),
        getConfig().getString("Database.password"),
        getConfig().getString("Database.table"));

        pData = new PlayerData(database);

        //Initialize managers
        spellManager = new SpellManager();
        skillManager = new SkillManager(pData);
        trollManager = new TrollManager();
        itemManager = new ItemManager(this);

        //Setup managers
        trollManager.setup();
        spellManager.setup();
        skillManager.setup();
        itemManager.setup();

        //Skills
        for(Skill s : skillManager.skills){
            getServer().getPluginManager().registerEvents(s, this);
        }

        //Trolls
        for(Trolls troll : trollManager.troll){
            getServer().getPluginManager().registerEvents(troll, this);
        }

        //Commands
        trollGui = new TrollGui(trollDrop, trollInventory);
        skillsGui = new SkillsGui(pData);
        bankGui = new BankGui(pData);

        //register commands
        getCommand("troll").setExecutor(new Troll(trollGui));
        getCommand("skills").setExecutor(new Skills(skillsGui));
        getCommand("bank").setExecutor(new Bank(bankGui));

        //register remaining events
        getServer().getPluginManager().registerEvents(skillsGui, this);
        getServer().getPluginManager().registerEvents(pData, this);
        getServer().getPluginManager().registerEvents(trollGui, this);
        getServer().getPluginManager().registerEvents(bankGui, this);
    }

    @Override
    public void onDisable() {
        //Upload user experience when server turns off
        for(Player player : Bukkit.getOnlinePlayers()){
            database.updateUserExp(player, pData.getPlayerExp(player.getUniqueId()));
        }
    }
}