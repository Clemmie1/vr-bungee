package net.vrb.bot.cmd;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public class CloseSessionAccount {
    public static final String BOT_TOKEN = "5994811952:AAEXrqfSaR15u9nivGgvLpo9WEigGq6v7I4";

    public CloseSessionAccount(Update update){

        TelegramBot bot = new TelegramBot(BOT_TOKEN);
        Message message = update.message();
        if (message != null){
            String command = message.text();

            if (command.equals("Закрыть текущию сессию")){
                SendMessage send = new SendMessage(update.message().from().id(), "❗ У вас нет активной сессий");
                bot.execute(send);
            }
        }
    }
}
