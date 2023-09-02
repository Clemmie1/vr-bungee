package net.vrb.wlsystem.cmd;

import net.md_5.bungee.api.CommandSender;
import net.vrb.wlsystem.WLAPI;

public class WhiteListSystemAdd {
    public static void sendCreateGuild(CommandSender sender, String[] args){

        if (args.length > 1){
            String username = args[1];

            if (WLAPI.isUserExists(username)){
                sender.sendMessage("§cИгрок уже состоит в белом списке");
                sender.sendMessage("§cМожно удалить из списка: /wl remove " + username);
            } else {
                sender.sendMessage("§aДобавляю игрока в белый спиок...");
                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                WLAPI.addToWhiteList(username);
                                sender.sendMessage("§aИгрок "+username+" успешно добавлен в белый список!");
                            }
                        },
                        700
                );
            }

        } else {
            sender.sendMessage("§cИспользуйте: /wl add <ник>");
        }

    }
}
