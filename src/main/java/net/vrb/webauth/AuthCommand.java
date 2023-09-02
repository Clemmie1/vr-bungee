package net.vrb.webauth;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.vrb.VRB;
import net.vrb.bot.cmd.API2FA;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

public class AuthCommand extends Command {

    private static final String ALLOWED_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public AuthCommand() {
        super("web");
    }

    @Override
    public void execute(CommandSender sender, String[] strings){
        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) sender;
            String name = player.getName();

            if (!WEBAPI.isUserExists(name)){
                String token = getGenToken(30);
                WEBAPI.createAuthToken(name, token);

                TextComponent message = new TextComponent("§7Нажми сюда, чтобы пройти авторизацию");

                long userId = API2FA.getUserId(name);

                message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://vanillareborn.net/auth/" + token + "?ti=" + userId));
                player.sendMessage(message);

            } else {
                sender.sendMessage("§cУ вас уже есть токен.");
            }


        } else {
            sender.sendMessage("Команда доступна только игрокам.");
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
