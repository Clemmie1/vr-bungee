package net.vrb.server.events;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

import java.util.Objects;

public class ServerUnavailable implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void CheckServerUnavailable(PostLoginEvent event){

        if (!Objects.equals(event.getPlayer().getName(), "Xperikss") && !Objects.equals(event.getPlayer().getName(), "Sir_KoT")){
            event.getPlayer().disconnect("§l§cVanillaReborn 2FA" +
                    "\n\n" +
                    "§7На данный момент наш сервер временно недоступен из-за проведения технических работ.\nНаша команда тщательно работает над улучшением серверной инфраструктуры, чтобы\nобеспечить вам более стабильное и качественное игровое взаимодействие." +
                    "\n\n" +
                    "§evanillareborn.net");
        }
    }

}
