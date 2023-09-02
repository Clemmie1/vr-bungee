package net.vrb.wlsystem;

import net.md_5.bungee.api.CommandSender;

public class WhiteListHelpList {
    public static void sendHelpList(CommandSender sender){
        sender.sendMessage("§a======= §bБелый список §a=======");
        sender.sendMessage("");
        sender.sendMessage("§e/wl add §7- Добавить игрока");
        sender.sendMessage("§e/wl list §7- Список игроков");
        sender.sendMessage("§e/wl remove §7- Удалить игрока");
    }
}
