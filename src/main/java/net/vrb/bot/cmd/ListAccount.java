package net.vrb.bot.cmd;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import net.vrb.Providers.MySQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ListAccount {
    public static final String BOT_TOKEN = "5994811952:AAE-59EpNKF8YelftGzxbWvdvTo3fveyzK4";

    public ListAccount(Update update){

        TelegramBot bot = new TelegramBot(BOT_TOKEN);
        Message message = update.message();
        if (message != null){
            String command = message.text();

            if (command.equals("Список привязок") || command.equals("/list")){

                try {
                    PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM `vr_2fa` WHERE userId = "+update.message().from().id()+" ");
                    ResultSet rs = ps.executeQuery();
                    int i = 1;

                    boolean dataFound = false;
                    while (rs.next()) {
                        dataFound = true;
                        SendMessage send = new SendMessage(update.message().from().id(), "#"+i+++". "+rs.getString("account_name")+" - "+(rs.getString("status_ip") != null && rs.getString("status_ip").equals("1") ? "Активный" : "Неактивный")+"\n\n" +
                                "Последний вход: "+ (rs.getString("last_join") != null ? rs.getString("last_join") : "¯\\_(ツ)_/¯") +"\n"+
                                "Последний вход с IP: "+ (rs.getString("last_ip") != null ? rs.getString("last_ip") : "¯\\_(ツ)_/¯") +"\n"+
                                "Привязан: "+ (rs.getString("created") != null ? rs.getString("created") : "¯\\_(ツ)_/¯")
                        );
                        bot.execute(send);
                    }
                    if (!dataFound) {
                        SendMessage send = new SendMessage(update.message().from().id(), "❗ Ошибка.\nУ вас нет не одной привязки");
                        bot.execute(send);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        }


    }
}
