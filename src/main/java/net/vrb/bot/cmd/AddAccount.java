package net.vrb.bot.cmd;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.vrb.Providers.MySQL;
import org.checkerframework.checker.units.qual.A;
import org.jetbrains.annotations.NotNull;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddAccount {
    public static final String BOT_TOKEN = "5994811952:AAE-59EpNKF8YelftGzxbWvdvTo3fveyzK4";

    public AddAccount(Update update){

        TelegramBot bot = new TelegramBot(BOT_TOKEN);
        Message message = update.message();


        if (message != null){
            String command = message.text();
            long userId = update.message().from().id();



            if (command.equals("Добавить привязку")){
                SendMessage send = new SendMessage(userId, "❗ Используйте:\n!add <ник>");
                bot.execute(send);

                System.out.println("Отправка от " + userId);
            }

            if (command.startsWith("/add ")) {
                String nickname = command.substring(5);
                System.out.println("Отправка от " + userId);
//                int userId = Math.toIntExact(update.message().from().id());

                try {
                    PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT COUNT(*) as count FROM `vr_2fa` WHERE `userId` = ?");
                    ps.setLong(1, userId);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        int count = rs.getInt("count");
                        if (count == 3) {
                            SendMessage send = new SendMessage(userId, "❗ Ошибка.\nВы достигли лимита 2FA");
                            bot.execute(send);
                        } else {
                            if (!API2FA.isUserExists(nickname)){
                                API2FA.setPlayerIn(nickname, update.message().from().id());
                                SendMessage send = new SendMessage(userId, "✅ Аккаунт "+nickname+" был добавлен!\nТеперь ваш аккаунт под защитой.");
                                bot.execute(send);

                                for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
                                    if (player.hasPermission("vr2fa.logs")) {
                                        player.sendMessage("§eVRB 2FA Logs > §f"+nickname+" привязал привязку");
                                    }

                                }
                            } else {
                                SendMessage send = new SendMessage(userId, " Ошибка.\n"+nickname+" уже привязан к 2FA");
                                bot.execute(send);
                            }
                        }
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } else if (command.equals("/add")){
                SendMessage send = new SendMessage(userId, "❗Ошибка.\nНеправильный формат команды. Используйте /add <ник>");
                bot.execute(send);
            }
        }
    }
}
