package se.spreadthebread.mctestrpg.commands;

import se.spreadthebread.mctestrpg.gui.SkillsGui;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Command Skills
 */
public class Skills implements CommandExecutor {

    private SkillsGui skillsGui;

    public Skills(SkillsGui skillsGui) {
        this.skillsGui = skillsGui;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(args.length != 0) return false;
            if(!sender.hasPermission("mctestrpg.skills")){
                sender.sendMessage(Bukkit.getPluginCommand("skills").getPermissionMessage());
                return true;
            }
            skillsGui.openGui(player);
            return true;
        }
        return true;
    }
}