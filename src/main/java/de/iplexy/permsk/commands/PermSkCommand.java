package de.iplexy.permsk.commands;

import de.iplexy.permsk.SkPerm;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import static de.iplexy.permsk.SkPerm.PermissionPlugin;
import static de.iplexy.permsk.SkPerm.prefix;

public class PermSkCommand implements CommandExecutor {
    //TODO Finish command
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length>0){
            if(args[0].equalsIgnoreCase("info")){
                sender.sendMessage(prefix+"PermSk v"+ SkPerm.getInstance().getDescription().getVersion());
                sender.sendMessage(prefix+"Skript: v"+Bukkit.getPluginManager().getPlugin("Skript").getDescription().getVersion());
                if (Bukkit.getPluginManager().getPlugin("Vault") != null){
                    sender.sendMessage(prefix+"Vault: §2§l✔");
                }else{
                    sender.sendMessage(prefix+"Vault: §4§l✗");
                }
                sender.sendMessage(prefix+"Permission Plugin: "+PermissionPlugin);
            }else{
                commandNotFound(sender);
            }
        }else {
            commandNotFound(sender);
        }
        return false;
    }

    private void commandNotFound(CommandSender sender){
        sender.sendMessage(prefix+"That's not a valid command.");
    }
}
