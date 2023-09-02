package net.vrb.bot.cmd;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.ProximityAlertTriggered;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.DeleteMessage;
import com.pengrad.telegrambot.request.SendMessage;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Objects;

public class ConfirmAuth {
    public static final String BOT_TOKEN = "5994811952:AAE-59EpNKF8YelftGzxbWvdvTo3fveyzK4";

    public ConfirmAuth(Update update){

        TelegramBot bot = new TelegramBot(BOT_TOKEN);

        CallbackQuery callbackQuery = update.callbackQuery();
        if (callbackQuery != null) {
            String callbackData = callbackQuery.data();
            String[] array = callbackData.split(",");

            if (Objects.equals(array[0], "ConfirmAuth")) {
                array[1] = array[1].trim();
                API2FA.updateStatusIp(array[1], 1);
                DeleteMessage deleteMessage = new DeleteMessage(callbackQuery.message().chat().id(), callbackQuery.message().messageId());
                bot.execute(deleteMessage);
                SendMessage send = new SendMessage(API2FA.getUserId(array[1]), "✅ Авторизация подтверждена для " + array[1] + "!\n❤ Приятной игры!");
                bot.execute(send);

                for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
                    if (player.hasPermission("vr2fa.logs")) {
                        player.sendMessage("§eVRB 2FA Logs > §f"+array[1]+"  подтвердил авторизацию");
                    }

                }

            } else if (Objects.equals(array[0], "CancelAuth")){
                array[1] = array[1].trim();
                API2FA.updateStatusIp(array[1], 0);
                DeleteMessage deleteMessage = new DeleteMessage(callbackQuery.message().chat().id(), callbackQuery.message().messageId());
                bot.execute(deleteMessage);

                ProximityAlertTriggered proximityAlertTriggered = new ProximityAlertTriggered();

                SendMessage send = new SendMessage(API2FA.getUserId(array[1]), "❗ Авторизация отменена для "+array[1]+"!");

                bot.execute(send);

                for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
                    if (player.hasPermission("vr2fa.logs")) {
                        player.sendMessage("§eVRB 2FA Logs > §f"+array[1]+" отменил подтверждение авторизации");
                    }

                }
            }
        }

    }
}
