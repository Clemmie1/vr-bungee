package net.vrb.mutesystem;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class MuteEvent implements Listener {

    @EventHandler
    public void checkMute(ChatEvent event){
        ProxiedPlayer player = (ProxiedPlayer) event.getSender();

        String nickname = player.getName();

        if (!event.isCommand()){
            if (MUTEAPI.isUserExists(nickname)){
                event.setCancelled(true);
                player.sendMessage("§cУ вас есть активный мут за нарушение игрового процесса");
            }
        }

    }

}
