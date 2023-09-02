package net.vrb;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.vrb.Providers.MySQL;
import net.vrb.bansystem.BanCommand;
import net.vrb.bansystem.KickCommand;
import net.vrb.bansystem.TempBanCommand;
import net.vrb.bansystem.UnBanCommand;
import net.vrb.bot.cmd.*;
import net.vrb.mutesystem.MuteCommand;
import net.vrb.mutesystem.MuteEvent;
import net.vrb.mutesystem.TempMuteCommand;
import net.vrb.mutesystem.UnMuteCommand;
import net.vrb.server.events.CheckPlayer2FA;
import net.vrb.server.events.Motd;
import net.vrb.server.events.ServerUnavailable;
import net.vrb.webauth.AuthCommand;
import net.vrb.wlsystem.ServerEvent;
import net.vrb.wlsystem.WhiteListCommand;

import java.util.List;

public class VRB extends Plugin {

    public static final String BOT_TOKEN = "5994811952:AAE-59EpNKF8YelftGzxbWvdvTo3fveyzK4";
    public static TelegramBot bot = new TelegramBot(BOT_TOKEN);

    private static VRB instance;

    @Override
    public void onEnable() {

        //ProxyServer.getInstance().getPluginManager().registerListener(this, new ServerUnavailable());

        ProxyServer.getInstance().getPluginManager().registerListener(this, new Motd());
        ProxyServer.getInstance().getPluginManager().registerListener(this, new CheckPlayer2FA());
        ProxyServer.getInstance().getPluginManager().registerListener(this, new MuteEvent());
        ProxyServer.getInstance().getPluginManager().registerListener(this, new ServerEvent());

        ProxyServer.getInstance().getPluginManager().registerCommand(this, new WhiteListCommand());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new TempBanCommand());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new BanCommand());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new KickCommand());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new UnBanCommand());

        ProxyServer.getInstance().getPluginManager().registerCommand(this, new TempMuteCommand());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new UnMuteCommand());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new MuteCommand());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new CloseAllSession());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new closeSubscriptions());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new AuthCommand());

        MySQL.connect();

        ServerAPI.setServerStatus(true);


        bot.setUpdatesListener(new UpdatesListener() {
            @Override
            public int process(List<Update> list) {
                for (Update update: list){

                    new Start(update);
                    new AddAccount(update);
                    new CloseSessionAccount(update);
                    new KickAccount(update);
                    new ListAccount(update);
                    new RemoveAccount(update);
                    new ConfirmAuth(update);
                    new WebAuth(update);
                }
                return UpdatesListener.CONFIRMED_UPDATES_ALL;
            }

        });

        //        String message = "Hello from Java BungeeCord!\n\ndsasda";
//
//        SendMessage request = new SendMessage(1290300905, message);
//        AnswerPreCheckoutQuery answerCheckout = new AnswerPreCheckoutQuery(id, "Sorry, item not available");
//        SendResponse response = bot.execute(request);
//
//        if (response.isOk()) {
//            System.out.println("Message sent successfully");
//        } else {
//            System.out.println("Failed to send message");
//        }
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
        ServerAPI.setServerStatus(false);
        MySQL.disconnect();
    }

//    public static void newMsgFormUser(Update update){
//        new Start(update);
//        new ConfirmAuth(update);
//        new AddAccount(update);
//        new RemoveAccount(update);
//        new ListAccount(update);
//        new KickAccount(update);
//        new CloseSessionAccount(update);
//
//    }

    public static VRB getInstance() {
        return instance;
    }
}
