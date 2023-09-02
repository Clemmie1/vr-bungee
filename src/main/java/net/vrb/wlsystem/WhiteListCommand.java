package net.vrb.wlsystem;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.vrb.wlsystem.cmd.WhiteListSystemAdd;
import net.vrb.wlsystem.cmd.WhiteListSystemRemove;
import net.vrb.wlsystem.cmd.WhiteSystemList;

public class WhiteListCommand extends Command {

    public WhiteListCommand() {
        super("wl");
    }

    @Override
    public void execute(CommandSender sender, String[] args){

        if (sender instanceof ProxiedPlayer){

            if (sender.hasPermission("whitelist")){
                if(args.length == 0){
                    WhiteListHelpList.sendHelpList(sender);
                } else {
                    if (args[0].equals("add")){
                        WhiteListSystemAdd.sendCreateGuild(sender, args);
                    } else if (args[0].equals("remove")){
                        WhiteListSystemRemove.sendCreateGuild(sender, args);
                    } else if (args[0].equals("list")){
                        WhiteSystemList.sendCreateGuild(sender);
                    } else {
                        WhiteListHelpList.sendHelpList(sender);
                    }
                }
            } else {
                sender.sendMessage("§cУ вас нет прав для выполнения этой команды");
            }

        }
    }

}
