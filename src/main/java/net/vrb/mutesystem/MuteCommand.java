package net.vrb.mutesystem;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.vrb.bansystem.BANAPI;

import java.util.Arrays;

public class MuteCommand extends Command {
    public MuteCommand() {
        super("vrmute");
    }

    @Override
    public void execute(CommandSender sender, String[] args){
        if (sender.hasPermission("bansystem")){
            if (args.length < 1) {
                sender.sendMessage("§cИспользуйте: /vrmute <ник> <причина>");
                sender.sendMessage("§7Пожалуйста, обязательно укажи причину");
                return;
            }

            String username = args[0];
            String muteReason = args.length > 1 ? String.join(" ", Arrays.copyOfRange(args, 1, args.length)) : "Без причины";

            if(!MUTEAPI.isUserExists(username)){
                ProxiedPlayer banPlayer = ProxyServer.getInstance().getPlayer(username);
                MUTEAPI.createMutePlayer(username, muteReason);

                for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
                    player.sendMessage("\n\n\n§7VR > §e" + username + "§c выдали мут. Причина: §e" + muteReason);
                }

                if (banPlayer != null){
                    banPlayer.sendMessage("\n\n\n§cВам выдали мут.\n\n§cПричина: §e" + muteReason);
                }


            } else {
                sender.sendMessage("§cУ игрока §e"+username+"§c уже есть мут!");
            }

        } else {
            sender.sendMessage("§cУ вас нет прав для выполнения этой команды");
        }
    }
}
