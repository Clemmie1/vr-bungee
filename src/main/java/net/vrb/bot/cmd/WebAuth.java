package net.vrb.bot.cmd;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import net.vrb.Providers.MySQL;
import net.vrb.webauth.WEBAPI;

import java.security.SecureRandom;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WebAuth {
    public static final String BOT_TOKEN = "5994811952:AAE-59EpNKF8YelftGzxbWvdvTo3fveyzK4";
    public static TelegramBot bot = new TelegramBot(BOT_TOKEN);
    private static final String ALLOWED_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public WebAuth(Update update) {
        TelegramBot bot = new TelegramBot(BOT_TOKEN);
        Message message = update.message();

        if (message != null) {
            String command = message.text();
            long userId = update.message().from().id();

            if (command.equals("Войти в аккаунт")) {
                SendMessage send = new SendMessage(userId, "❗Используйте:\n/web <ник>");
                bot.execute(send);
            }

            if (command.startsWith("/web ")) {
                String nickname = command.substring(4);
                if (API2FA.isUserExists2Fa(userId, nickname)){

//                    String token = getGenToken(30);
//                    WEBAPI.createAuthToken(nickname, token);

                    SendMessage sendMessage = new SendMessage(userId, "Перейди, чтобы пройти авторизацию " + nickname);
                    bot.execute(sendMessage);

//                    InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup(
//                            new InlineKeyboardButton[]{
//                                    new InlineKeyboardButton("Перейти").url("https://vanillareborn.net/auth/"+token+"?ti="+userId)
//                            });
//
//                    sendMessage.replyMarkup(inlineKeyboard);
                } else {
                    SendMessage send = new SendMessage(userId, "❗Ошибка.\nУ вас нет прав");
                    bot.execute(send);
                }

            } else if (command.equals("/web")) {
                SendMessage send = new SendMessage(userId, "❗Ошибка.\nНеправильный формат команды. Используйте /web <ник>");
                bot.execute(send);
            }
        }


    }

    public static String getGenToken(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Размер токена должен быть положительным числом.");
        }

        SecureRandom secureRandom = new SecureRandom();
        StringBuilder tokenBuilder = new StringBuilder(size + 8); // Добавляем место для "VR_AUTH_"

        // Добавляем приставку "VR_AUTH_"
        tokenBuilder.append("VR_AUTH_");

        for (int i = 0; i < size; i++) {
            // Получаем случайный индекс из набора разрешенных символов
            int randomIndex = secureRandom.nextInt(ALLOWED_CHARACTERS.length());
            // Добавляем символ из набора к токену
            tokenBuilder.append(ALLOWED_CHARACTERS.charAt(randomIndex));
        }

        return tokenBuilder.toString();
    }
}
