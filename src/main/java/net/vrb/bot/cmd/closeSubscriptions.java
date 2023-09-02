package net.vrb.bot.cmd;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.vrb.Providers.MySQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class closeSubscriptions extends Command {
    public static final String BOT_TOKEN = "5994811952:AAE-59EpNKF8YelftGzxbWvdvTo3fveyzK4";

    public closeSubscriptions() {
        super("sendCloseSubscriptions");
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (commandSender.hasPermission("2fa")){
            TelegramBot bot = new TelegramBot(BOT_TOKEN);
            try {
                PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM `vr_2fa`");

                ResultSet rs = ps.executeQuery();

                commandSender.sendMessage("...");

                LocalDateTime expirationDate = LocalDateTime.of(2026, 1, 1, 0, 0);
                LocalDateTime currentDate = LocalDateTime.now();
                long days = ChronoUnit.DAYS.between(currentDate, expirationDate);
                long hours = ChronoUnit.HOURS.between(currentDate, expirationDate) % 24;

                while (rs.next()){

                    Long userId = rs.getLong("userId");
                    String account = rs.getString("account_name");
                    String result = String.format("✉ Уведомление для "+account+"!\n\nВаша подписка Проходка на сервер продлена администратором. Истекает через %d д. %h ч.\n\n❤ Приятной игры!", days, hours);
                    SendMessage send = new SendMessage(userId, result);
                    bot.execute(send);

                    API2FA.updateStatusIp(account, 0);
                }



            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {
            commandSender.sendMessage("§cУ вас нет прав для выполнения этой команды");
        }
    }
}
