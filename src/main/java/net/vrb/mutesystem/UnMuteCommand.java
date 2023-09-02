package net.vrb.mutesystem;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.vrb.bansystem.BANAPI;

public class UnMuteCommand extends Command {
    public UnMuteCommand() {
        super("vrunmute");
    }

    @Override
    public void execute(CommandSender sender, String[] args){
        if (sender.hasPermission("bansystem")){
            if (args.length == 0) {
                sender.sendMessage("§cИспользуйте: /vrunmute <ник>");
                return;
            }

            String username = args[0];

            if(MUTEAPI.isUserExists(username)){
                MUTEAPI.deleteMutePlayer(username);
                sender.sendMessage("§a"+ username +" снял мут с игрока");

            } else {
                sender.sendMessage("§cУ этого играко нет мута");
            }

        } else {
            sender.sendMessage("§cУ вас нет прав для выполнения этой команды");
        }
    }
}
