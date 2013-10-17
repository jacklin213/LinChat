package me.jacklin213.linchat.commands;

import me.jacklin213.linchat.LinChat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandReload extends SubCommand{

	private LinChat LC;

	public CommandReload(LinChat instance) {
		LC = instance;
	}

	@Override
	public void start(CommandSender sender, String[] args, String permissionNode) {
		if (Bukkit.getConsoleSender() == sender){
			LC.reload();
		}
		if (sender instanceof Player){
			Player player = (Player) sender;
			if (player.hasPermission(permissionNode)){
				LC.reload();
				LC.MSG.commandReply(player, ChatColor.GREEN + "Reloaded!");
			}
		}
	}
}
