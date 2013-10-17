package me.jacklin213.linchat.commands;

import me.jacklin213.linchat.LinChat;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHelp extends SubCommand{

	private LinChat LC;
	
	public CommandHelp(LinChat instance){
		LC = instance;
	}
	
	@Override
	public void start(CommandSender sender, String[] args, String permissionNode) {
		if (Bukkit.getConsoleSender() == sender){
			LC.log.info("==========[Commands]==========");
			LC.log.info("/linchat or /lc to use LinChat Commands");
			LC.log.info("/lc help - displays this");
			LC.log.info("/lc reload - Reloads all configuration");
		}
		if (sender instanceof Player){
			Player player = (Player) sender;
			if (player.hasPermission(permissionNode)){
				LC.MSG.displayHelp(player);
			}
		}
	}

}
