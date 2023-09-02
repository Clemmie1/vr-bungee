package net.vrb.wlsystem;

import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.vrb.ServerAPI;

public class ServerEvent implements Listener {

    @EventHandler()
    public void join(PostLoginEvent event){
        ServerAPI.addOnlinePlayer(true);
    }

    @EventHandler()
    public void quit(PlayerDisconnectEvent event){
        ServerAPI.addOnlinePlayer(false);
    }

}
