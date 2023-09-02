package net.vrb.wlsystem.cmd;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.vrb.wlsystem.WLAPI;

public class WhiteListSystemRemove {
    public static void sendCreateGuild(CommandSender sender, String[] args){
        if (args.length > 1){

            String username = args[1];

            if (WLAPI.isUserExists(username)){
                sender.sendMessage("§aУдаляю игрока из белого спиок...");
                WLAPI.removeFormWhiteList(username);
                sender.sendMessage("§aИгрок "+username+" успешно удалён из белого спиок!");

                ProxiedPlayer player = ProxyServer.getInstance().getPlayer(username);

                if (player != null){
                    player.disconnect("§a§lVanillaReborn\n\n§cБаза данных была обновлена.\n\nПожалуйста, перезайти на сервер.");
                }

            } else {
                sender.sendMessage("§cНет игрока в базе данных с таким ником");
            }



        } else  {
            sender.sendMessage("§cИспользуйте: /wl remove <ник>");
        }
    }
}
