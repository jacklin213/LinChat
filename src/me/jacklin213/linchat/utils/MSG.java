package me.jacklin213.linchat.utils;

import me.jacklin213.linchat.LinChat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MSG {
	
	private LinChat LC;
	
	public MSG(LinChat instance){
		LC = instance;
	}
	
	public void commandReply(Player player, String message){
		if (player != null){
			player.sendMessage(LC.configHandler.getPluginName() + " " + message);
		}
	}
	
	public void displayBasicInfo(CommandSender sender){
		sender.sendMessage(LC.configHandler.getPluginName() + ChatColor.GREEN +" made by" + ChatColor.GOLD + " jacklin213");
	}
	
	public void displayHelp(Player player){
		player.sendMessage(ChatColor.GOLD + "============" + ChatColor.RED + "[" + ChatColor.GREEN + "LinChat" + ChatColor.WHITE + ":" + ChatColor.YELLOW + "Help" + ChatColor.RED + "]" + ChatColor.GOLD + " ============ ");
		player.sendMessage(ChatColor.GOLD + "/linchat " + ChatColor.WHITE + " - " + ChatColor.AQUA + "Shows plugin name and author.");
		/*player.sendMessage(ChatColor.GOLD + "/linchat create <groupname>" + ChatColor.WHITE + ":" + ChatColor.AQUA + "Creates a new group");*/
		/*player.sendMessage(ChatColor.GOLD + "/linchat info" + ChatColor.WHITE + " - " + ChatColor.AQUA + "Shows the full plugin info.");*/
		player.sendMessage(ChatColor.GOLD + "/linchat help" + ChatColor.WHITE + " - " + ChatColor.AQUA + "Displays this page.");
		player.sendMessage(ChatColor.GOLD + "/linchat reload" + ChatColor.WHITE + " - " + ChatColor.AQUA + "Reloads the configuration.");
		/*player.sendMessage(ChatColor.GOLD + "/linchat set <player> <groupname>" + ChatColor.WHITE + " - " + ChatColor.AQUA + "Moves/Adds player to specified group");*/
	}
	
	public void underConstruction(CommandSender sender){
		if (sender instanceof Player){
			commandReply((Player) sender, ChatColor.RED + "Command is underconstuction");
		}
		if (Bukkit.getConsoleSender() == sender){
			LC.log.info("Command is underconstruction");
		}
	}
}
