package se.spreadthebread.mctestrpg.commands;

import se.spreadthebread.mctestrpg.gui.TrollGui;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * CommandTroll
 */
public class Troll implements CommandExecutor {

    private TrollGui trollGui;

    public Troll(TrollGui trollGui) {
        this.trollGui = trollGui;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            if(args.length != 1) return false;
            if(!sender.hasPermission("mctestrpg.troll")){
                sender.sendMessage(Bukkit.getPluginCommand("troll").getPermissionMessage());
                return true;
            }
            Player target = Bukkit.getPlayerExact(args[0]);
            if(target == null) {
                sender.sendMessage(args[0] + " is not online!");
                return true;
            }
            trollGui.setTarget(target);
            trollGui.openGui((Player) sender);
            return true;
        }
        return true;
    }
}