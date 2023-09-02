package net.vrb.server.events;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.InlineQuery;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.*;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.vrb.Providers.MySQL;
import net.vrb.Utilities.ServerTimeData;
import net.vrb.Utility;
import net.vrb.bansystem.BANAPI;
import net.vrb.bot.cmd.API2FA;
import net.vrb.wlsystem.WLAPI;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

public class CheckPlayer2FA implements Listener {

    public static final String BOT_TOKEN = "5994811952:AAE-59EpNKF8YelftGzxbWvdvTo3fveyzK4";
    public static TelegramBot bot = new TelegramBot(BOT_TOKEN);

    @EventHandler
    public void Check(PostLoginEvent event){
        ProxiedPlayer player = event.getPlayer();
        String nickname = event.getPlayer().getName();

        String ip = player.getAddress().getAddress().getHostAddress();

        if (API2FA.isUserExists(nickname)){

            if (API2FA.getLastIp(nickname) != null && Objects.equals(API2FA.getLastIp(nickname), ip)){

                if (Objects.equals(API2FA.getStatusIp(nickname), "1")){

                    try{
                        PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT `banned`,`end_ban`,`ban_reason` FROM `bansystem` WHERE banned = '"+nickname+"' ");
                        ResultSet rs = ps.executeQuery();
                        if (rs.next()){
                            Date endBan = rs.getTimestamp("end_ban");
                            if (endBan != null){

                                Date currentTimestamp = Utility.getDate();

                                if (endBan.after(currentTimestamp)){
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
                                    String formattedDate = dateFormat.format(endBan);
                                    player.disconnect("§a§lVanillaReborn" +
                                            "\n\n" +
                                            "§cВаш аккаунт временно заблокирован" +
                                            "\n\n" +
                                            "§7Бан до: §e" + formattedDate + "\n" +
                                            "§7Причина бана: §e" + rs.getString("ban_reason")
                                    );
                                } else {
                                    BANAPI.deleteBanPlayer(nickname);
                                }

                            } else {
                                player.disconnect("§a§lVanillaReborn" +
                                        "\n\n" +
                                        "§cВаш аккаунт заблокирован" +
                                        "\n" +
                                        "§7Причина бана: §e" + rs.getString("ban_reason")
                                );
                            }


                        } else {
                            API2FA.updateLastJoin(nickname);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                } else {
                    player.disconnect("§l§cVanillaReborn 2FA" +
                            "\n\n" +
                            "§7Необходимо потвердить вход на сервер." +
                            "\n\n" +
                            "§7Бот отправил вам подтверждение входа." +
                            "\n" +
                            "§7t.me/VanillaReborn2FA_bot");

                    SendMessage request = new SendMessage(API2FA.getUserId(nickname), "✉ Авторизация." +
                            "\n\n" +nickname+ " запрашивает доступ входа на сервер.");


                    String[] array = {"ConfirmAuth", nickname};
                    String callbackData = Arrays.toString(array);
                    callbackData = callbackData.substring(1, callbackData.length() - 1);

                    String[] array1 = {"CancelAuth", nickname};
                    String callbackData1 = Arrays.toString(array1);
                    callbackData1 = callbackData1.substring(1, callbackData1.length() - 1);

                    InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup(
                            new InlineKeyboardButton("✅").callbackData(callbackData),
                            new InlineKeyboardButton("⛔").callbackData(callbackData1)
                    );

                    request.replyMarkup(inlineKeyboard);
                    bot.execute(request);
                }

            } else {
                API2FA.updateLastIp(nickname, ip);
                API2FA.updateStatusIp(nickname, 0);
                player.disconnect("§l§cVanillaReborn 2FA" +
                        "\n\n" +
                        "§7Необходимо потвердить вход на сервер. Так как у вас новый IP." +
                        "\n\n" +
                        "§7Бот отправил вам подтверждение входа." +
                        "\n" +
                        "§7t.me/VanillaReborn2FA_bot");

                SendMessage request = new SendMessage(API2FA.getUserId(nickname), "✉ Авторизация." +
                        "\n\n" +
                        nickname + " запрашивает доступ входа на сервер." +
                        "\n\n" +
                        "IP: "+ip+" / "+ ServerTimeData.getTimeData() +" MSK" +
                        "\n\n" +
                        "❗ Если это не Вы заходили на сервер, не подтверждайте авторизацию.");


                String[] array = {"ConfirmAuth", nickname};
                String callbackData = Arrays.toString(array);
                callbackData = callbackData.substring(1, callbackData.length() - 1);

                String[] array1 = {"CancelAuth", nickname};
                String callbackData1 = Arrays.toString(array1);
                callbackData1 = callbackData1.substring(1, callbackData1.length() - 1);

                InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup(
                        new InlineKeyboardButton("✅").callbackData(callbackData),
                        new InlineKeyboardButton("⛔").callbackData(callbackData1)
                );

                request.replyMarkup(inlineKeyboard);
                bot.execute(request);
            }
        } else {
            player.disconnect("§l§cVanillaReborn 2FA" +
                    "\n\n" +
                    "§7Ваш аккаунт ещё не подключён к двухэтапной аутентификации. Советуем сделать это как можно быстрее." +
                    "\n\n" +
                    "§7Напиши нашему боту §f!add " + nickname +
                    "\n" +
                    "§7t.me/VanillaReborn2FA_bot " + ip);
        }

    }

//    public static void Active(Update update){
//        InlineQuery callbackQuery = update.inlineQuery();
//        if (callbackQuery.query().equals("callback_data")){
//            SendMessage send = new SendMessage(1, "ok");
//            bot.execute(send);
//        }
//    }

}
