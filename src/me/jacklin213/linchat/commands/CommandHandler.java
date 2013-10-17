package me.jacklin213.linchat.commands;

import me.jacklin213.linchat.LinChat;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandHandler implements CommandExecutor{
	
	private LinChat LC;
	
	public CommandHandler(LinChat instance){
		LC = instance;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (commandLabel.equalsIgnoreCase("linchat") || commandLabel.equalsIgnoreCase("lc")){
			if (args.length == 0){
				LC.MSG.displayBasicInfo(sender);
				return true;
			}
			if (args.length == 1){
				if (args[0].equalsIgnoreCase("reload")){
					LC.cmdReload.start(sender, args, "linchat.reload");
					return true;
				}
				if (args[0].equalsIgnoreCase("help")){
					LC.cmdHelp.start(sender, args, "linchat.help");
					return true;
				}
			}
			if (args.length == 2){
				if (args[0].equalsIgnoreCase("display")|| args[0].equalsIgnoreCase("show")){
					LC.MSG.underConstruction(sender);
					return true;
				}
			} else {
				sender.sendMessage("Not enough arguments, check /lc help - for help");
				return true;
			}
		}
		return false;
	}

}
