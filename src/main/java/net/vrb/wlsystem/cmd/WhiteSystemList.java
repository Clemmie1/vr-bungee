package net.vrb.wlsystem.cmd;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.vrb.Providers.MySQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WhiteSystemList {
    public static void sendCreateGuild(CommandSender sender){
        try {
            PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM `whitelist`");

            ResultSet rs = ps.executeQuery();

            int i = 1;
            sender.sendMessage("§a======= §bБелый список §a=======");
            sender.sendMessage("");
            sender.sendMessage("§7Ник игрока | Дата добавления");
            sender.sendMessage("");
            while (rs.next()){
                sender.sendMessage("§7"+ i++ +".§f " + rs.getString("account_name") + " - " + rs.getString("created"));
            }



        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
