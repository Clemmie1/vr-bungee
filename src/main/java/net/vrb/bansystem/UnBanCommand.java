package net.vrb.bansystem;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class UnBanCommand extends Command {
    public UnBanCommand() {
        super("vrunban");
    }

    @Override
    public void execute(CommandSender sender, String[] args){

        if (sender.hasPermission("bansystem")){
            if (args.length == 0) {
                sender.sendMessage("§cИспользуйте: /vrunban <ник>");
                return;
            }

            String username = args[0];

            if(BANAPI.isUserExists(username)){
                BANAPI.deleteBanPlayer(username);
                sender.sendMessage("§a"+ username +" был разбанен");

            } else {
                sender.sendMessage("§cУ этого играко нет бана");
            }
        } else {
            sender.sendMessage("§cУ вас нет прав для выполнения этой команды");
        }

    }
}
