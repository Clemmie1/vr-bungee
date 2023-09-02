package net.vrb.bansystem;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.Arrays;

public class BanCommand extends Command {

    public BanCommand() {
        super("vrban");
    }

    @Override
    public void execute(CommandSender sender, String[] args){

        if (sender.hasPermission("bansystem")){
            if (args.length < 1) {
                sender.sendMessage("§cИспользуйте: /vrban <ник> <причина>");
                sender.sendMessage("§7Пожалуйста, обязательно укажи причину");
                return;
            }

            String username = args[0];
            String banReason = args.length > 1 ? String.join(" ", Arrays.copyOfRange(args, 1, args.length)) : "Без причины";

            if(!BANAPI.isUserExists(username)){

                ProxiedPlayer banPlayer = ProxyServer.getInstance().getPlayer(username);

                BANAPI.createBanPlayer(username, banReason);

                if (banPlayer != null){
                    banPlayer.disconnect("§a§lVanillaReborn\n\n§cВаш аккаунт заблокирован за нарушение игрового процесса.");
                }

                for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
                    player.sendMessage("\n\n\n§7VR > §e" + username + "§c был забанен. Причина: §e" + banReason);
                }
            } else {
                sender.sendMessage("§cИгрок §e"+username+"§c уже забанен!");
            }
        } else {
            sender.sendMessage("§cУ вас нет прав для выполнения этой команды");
        }

    }
}
