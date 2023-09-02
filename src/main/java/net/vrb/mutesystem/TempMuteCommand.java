package net.vrb.mutesystem;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.vrb.Utility;
import net.vrb.bansystem.BANAPI;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TempMuteCommand extends Command {

    public TempMuteCommand() {
        super("vrtempmute");
    }

    @Override
    public void execute(CommandSender sender, String[] args){
        if (sender.hasPermission("bansystem")){

            if (args.length < 2) {
                sender.sendMessage("§cИспользуйте: /vrtempmute <ник> <время> <причина>");
                sender.sendMessage("§7Пожалуйста, обязательно укажи причину");
                sender.sendMessage("\n");
                sender.sendMessage("§7Если ты бебра, вот:");
                sender.sendMessage("§7<число>d - мут в днях");
                sender.sendMessage("§7<число>m - мут в минутах");
                sender.sendMessage("§7<число>s - мут в секундах");
                return;
            }

            String username = args[0];
            String muteTime = args[1];
            String muteReason = args.length > 2 ? String.join(" ", Arrays.copyOfRange(args, 2, args.length)) : "Без причины";

            long duration;
            String durationText;
            try {
                duration = parseBanTime(muteTime);
                durationText = formatBanDuration(duration);
            } catch (IllegalArgumentException e) {
                sender.sendMessage("§cНедопустимая продолжительность временного мута");
                return;
            }

            String timestamp = Utility.getFutureTimestamp((int) duration, TimeUnit.SECONDS);

            if(!MUTEAPI.isUserExists(username)){
                MUTEAPI.createTempMutePlayer(username, timestamp, muteReason);
                ProxiedPlayer banPlayer = ProxyServer.getInstance().getPlayer(username);

                for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
                    player.sendMessage("\n\n\n§7VR > §e" + username + "§c временно выдали мут на §e" + durationText + "§c. Причина: §e" + muteReason);
                }

                if (banPlayer != null){
                    banPlayer.sendMessage("\n\n\n§cВам временно выдали мут.\n\n§cПричина: §e" + muteReason + "\n§cСрок: §e" + durationText);
                }

            } else {
                sender.sendMessage("§cУ игрока §e"+username+"§c уже есть временный мут!");
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
