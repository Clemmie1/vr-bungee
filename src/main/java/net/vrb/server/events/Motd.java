package net.vrb.server.events;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

public class Motd implements Listener {

    @EventHandler
    public void Motd(ProxyPingEvent e){
        ServerPing serverPing = e.getResponse();
        serverPing.setDescription("§aVanillaReborn\n§r§eЛамповое ванильное выживание");
        e.setResponse(serverPing);
    }
}
