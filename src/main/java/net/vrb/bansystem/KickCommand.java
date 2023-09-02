package net.vrb.bansystem;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.Arrays;

public class KickCommand extends Command {
    public KickCommand() {
        super("vrkick");
    }

    @Override
    public void execute(CommandSender sender, String[] args){

        if (sender.hasPermission("bansystem")){
            if (args.length < 1) {
                sender.sendMessage("§cИспользуйте: /vrkick <ник> <причина>");
                sender.sendMessage("§7Пожалуйста, обязательно укажи причину");
                return;
            }

            String username = args[0];
            String kickReason = args.length > 1 ? String.join(" ", Arrays.copyOfRange(args, 1, args.length)) : "Без причины";

            ProxiedPlayer kickPlayer = ProxyServer.getInstance().getPlayer(username);

            if (kickPlayer != null){
                kickPlayer.disconnect("§a§lVanillaReborn\n\n§cВас кикнули\n" + "§cПрична: §e" + kickReason);

                for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
                    player.sendMessage("\n\n\n§7VR > §e" + username + "§c был кикнут. Причина: §e" + kickReason);
                }

            } else {
                sender.sendMessage("§cИгрок не найден");
            }
        } else {
            sender.sendMessage("§cУ вас нет прав для выполнения этой команды");
        }

    }
}
