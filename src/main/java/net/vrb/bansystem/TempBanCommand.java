package net.vrb.bansystem;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.vrb.Utility;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TempBanCommand extends Command {
    public TempBanCommand() {
        super("vrtempban");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (sender.hasPermission("bansystem")){
            if (args.length < 2) {
                sender.sendMessage("§cИспользуйте: /vrtempban <ник> <время> <причина>");
                sender.sendMessage("§7Пожалуйста, обязательно укажи причину");
                sender.sendMessage("\n");
                sender.sendMessage("§7Если ты бебра, вот:");
                sender.sendMessage("§7<число>d - бан в днях");
                sender.sendMessage("§7<число>m - бан в минутах");
                sender.sendMessage("§7<число>s - бан в секундах");
                return;
            }

            String username = args[0];
            String banTime = args[1];
            String banReason = args.length > 2 ? String.join(" ", Arrays.copyOfRange(args, 2, args.length)) : "Без причины";


            long duration;
            String durationText;
            try {
                duration = parseBanTime(banTime);
                durationText = formatBanDuration(duration);
            } catch (IllegalArgumentException e) {
                sender.sendMessage("§cНедопустимая продолжительность временного бана");
                return;
            }


            String timestamp = Utility.getFutureTimestamp((int) duration, TimeUnit.SECONDS);

            if(!BANAPI.isUserExists(username)){
                BANAPI.createTempBanPlayer(username, timestamp, banReason);
                ProxiedPlayer banPlayer = ProxyServer.getInstance().getPlayer(username);

                if (banPlayer != null){
                    banPlayer.disconnect("§a§lVanillaReborn\n\n§cВаш аккаунт временно заблокирован за нарушение игрового процесса.");
                }

                for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
                    player.sendMessage("\n\n\n§7VR > §e" + username + "§c временно забанен на §e" + durationText + "§c. Причина: §e" + banReason);
                }
            } else {
                sender.sendMessage("§cИгрок §e"+username+"§c уже временно забанен!");
            }
        } else {
            sender.sendMessage("§cУ вас нет прав для выполнения этой команды");
        }

    }

    private long parseBanTime(String banTime) throws IllegalArgumentException {
        Pattern pattern = Pattern.compile("(\\d+)([dms])");
        Matcher matcher = pattern.matcher(banTime);

        if (matcher.matches()) {
            int value = Integer.parseInt(matcher.group(1));
            String unit = matcher.group(2);

            switch (unit) {
                case "d":
                    return TimeUnit.DAYS.toSeconds(value);
                case "m":
                    return TimeUnit.MINUTES.toSeconds(value);
                case "s":
                    return value;
            }
        }

        throw new IllegalArgumentException();
    }

    private String formatBanDuration(long duration) {
        if (TimeUnit.SECONDS.toDays(duration) > 0) {
            return TimeUnit.SECONDS.toDays(duration) + "д";
        } else if (TimeUnit.SECONDS.toHours(duration) > 0) {
            return TimeUnit.SECONDS.toHours(duration) + "ч";
        } else if (TimeUnit.SECONDS.toMinutes(duration) > 0) {
            return TimeUnit.SECONDS.toMinutes(duration) + "м";
        } else {
            return duration + "с";
        }
    }
}
