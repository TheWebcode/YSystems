package io.github.thewebcode.ybungeecore.event;

import io.github.thewebcode.ybungeecore.YBungeeCore;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class Eventlistener implements Listener {
    @EventHandler
    public void onProxyPing(ProxyPingEvent event){
        String motd = YBungeeCore.get().getMessage("bungeecord.motd");

        ServerPing response = event.getResponse();
        response.setDescriptionComponent(new TextComponent(motd));
        event.setResponse(response);
    }
}
