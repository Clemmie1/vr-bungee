package net.vrb.bot.cmd;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public class KickAccount {
    public static final String BOT_TOKEN = "5994811952:AAE-59EpNKF8YelftGzxbWvdvTo3fveyzK4";

    public KickAccount(Update update){

        TelegramBot bot = new TelegramBot(BOT_TOKEN);
        Message message = update.message();
        if (message != null){
            String command = message.text();

            if (command.equals("Кикнуть с сервера")){
                SendMessage send = new SendMessage(update.message().from().id(), "❗ Вы офлайн");
                bot.execute(send);
            }
        }

    }
}
