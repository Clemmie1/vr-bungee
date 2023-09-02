package net.vrb.bot.cmd;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.*;
import com.pengrad.telegrambot.request.SendMessage;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.vrb.Providers.MySQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class RemoveAccount {
    public static final String BOT_TOKEN = "5994811952:AAE-59EpNKF8YelftGzxbWvdvTo3fveyzK4";
    public static TelegramBot bot = new TelegramBot(BOT_TOKEN);

    public RemoveAccount(Update update){

        Message message = update.message();
        if (message != null){
            String command = message.text();

            Long userId = update.message().from().id();

            if (command.equals("Удалить привязку")){

                SendMessage sendMessage = new SendMessage(userId, "❗Используйте:\n/del <ник>");
                bot.execute(sendMessage);
            }

            if (command.startsWith("/del ")) {
                String nickname = command.substring(5);


                try {
                    PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM `vr_2fa` WHERE `userId` = "+userId+" AND `account_name`='"+nickname+"'");
                    ResultSet rs = ps.executeQuery();

                    boolean dataFound = false;
                    while (rs.next()) {
                        dataFound = true;
                        try {
                            PreparedStatement pss = MySQL.getConnection().prepareStatement("DELETE FROM `vr_2fa` WHERE `account_name` = '"+nickname+"' ");
                            pss.executeUpdate();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        SendMessage send = new SendMessage(userId, "✅ Аккаунт "+nickname+" был удалён!");
                        bot.execute(send);

                        for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
                            if (player.hasPermission("vr2fa.logs")) {
                                player.sendMessage("§eVRB 2FA Logs > §f"+nickname+" удалил привязку");
                            }

                        }
                    }
                    if (!dataFound) {
                        SendMessage send = new SendMessage(userId, "❗Ошибка.\nУ вас нет прав");
                        bot.execute(send);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } else if (command.equals("!r")){
                SendMessage send = new SendMessage(update.message().from().id(), "❗Ошибка.\nНеправильный формат команды. Используйте /del <ник>");
                bot.execute(send);
            }
        }

    }
}
