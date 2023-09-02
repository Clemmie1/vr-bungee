package net.vrb.bot.cmd;

import com.pengrad.telegrambot.ExceptionHandler;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramException;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import com.pengrad.telegrambot.response.SendResponse;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Start {

    public static final String BOT_TOKEN = "5994811952:AAE-59EpNKF8YelftGzxbWvdvTo3fveyzK4";
    public static TelegramBot bot = new TelegramBot(BOT_TOKEN);


    public Start(Update update){
        Message message = update.message();
        if (message != null) {
            String command = message.text();
            if (command.equals("/start")){
                SendMessage send = new SendMessage(update.message().from().id(), "Привет!\n\nЭто Монобебрик, бот по безопасности вашего аккаунта.");

                Keyboard replyKeyboardMarkup = new ReplyKeyboardMarkup(

                        new String[]{"Добавить привязку", "Список привязок"},
                        new String[]{"Войти в аккаунт"},
                        new String[]{"Удалить привязку"})
                        .oneTimeKeyboard(false)   // optional
                        .resizeKeyboard(true)    // optional
                        .selective(true);

                send.replyMarkup(replyKeyboardMarkup);
                bot.execute(send);
            }
        }

    }

//    public void sendStart(){
//
//        bot.setUpdatesListener(updates -> {
//            updates.forEach(update -> {
//                Long userId = update.message().from().id();
//                Message message = update.message();
//                String command = message.text();
//
//                if (command.equals("/auth")){
//                    SendMessage send = new SendMessage(update.message().from().id(), "Привет!\n\nЭто Монобебрик, бот по безопасности вашего аккаунта.");
//
//                    String nickname = "Xperikss";
//
//                    SendMessage request = new SendMessage(API2FA.getUserId(nickname), "✉ Авторизация." +
//                            "\n\n" +nickname+ " запрашивает доступ входа на сервер.");
//
//
//                    String[] array = {"ConfirmAuth", nickname};
//                    String callbackData = Arrays.toString(array);
//                    callbackData = callbackData.substring(1, callbackData.length() - 1);
//
//                    String[] array1 = {"CancelAuth", nickname};
//                    String callbackData1 = Arrays.toString(array1);
//                    callbackData1 = callbackData1.substring(1, callbackData1.length() - 1);
//
//                    InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup(
//                            new InlineKeyboardButton("Подтвердить").callbackData(callbackData),
//                            new InlineKeyboardButton("Отменить").callbackData(callbackData1)
//                    );
//
//                    request.replyMarkup(inlineKeyboard);
//                    bot.execute(request);
//
////                    Keyboard replyKeyboardMarkup = new ReplyKeyboardMarkup(
////
////                            new String[]{"Добавить привязку", "Список привязок"},
////                            new String[]{"Удалить привязку"})
////                            .oneTimeKeyboard(false)   // optional
////                            .resizeKeyboard(true)    // optional
////                            .selective(true);
////
////                    send.replyMarkup(replyKeyboardMarkup);
////                    bot.execute(send);
//                }
//            });
//
//
//            return UpdatesListener.CONFIRMED_UPDATES_ALL;
//        });
//
//    }


//    public void sendAuth(){
//
//        bot.setUpdatesListener(updates -> {
//            updates.forEach(update ->{
//
//                CallbackQuery callbackQuery = update.callbackQuery();
//
//                if (callbackQuery != null) {
//                    String callbackData = callbackQuery.data();
//                    String[] array = callbackData.split(",");
//
//                    if (Objects.equals(array[0], "ConfirmAuth")){
//                        array[1] = array[1].trim();
//
//                       // SendResponse response = bot.execute(new SendMessage(update.message().from().id(), "ok!"));
//
//                    }
//
//                }
//
//            });
//
//            return UpdatesListener.CONFIRMED_UPDATES_ALL;
//        });
//
//    }

}
