package se.spreadthebread.mctestrpg.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import se.spreadthebread.mctestrpg.gui.BankGui;

/**
 * Bank
 */
public class Bank implements CommandExecutor {

    private BankGui bankGui;

    public Bank(BankGui bankGui) {
        this.bankGui = bankGui;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            bankGui.openGui((Player) sender);
            return true;
        }
        return false;
    }

    
}