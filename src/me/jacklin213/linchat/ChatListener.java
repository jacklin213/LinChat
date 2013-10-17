package me.jacklin213.linchat;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener{
	
	public static LinChat plugin;
	
	public ChatListener(LinChat instance) {
		plugin = instance;
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent event){
		Player player = event.getPlayer();
		String playerName = player.getName();
		String message = event.getMessage();
		boolean defaultChatPlugin = plugin.configHandler.isDefaultChatPlugin();
		if (defaultChatPlugin){
			if (plugin.groupManager.isInGroup(playerName)){
				if (plugin.configHandler.isUsingChatPermissions()){
					if (player.hasPermission("linchat.chat")){
						event.setFormat(applyFormatting(player, message));
					} else {
						plugin.MSG.commandReply(player, ChatColor.RED + "Sorry you do not have the permission to chat");
						event.setCancelled(true);
					}
				} else {
					event.setFormat(applyFormatting(player, message));
				}
			}
		} else {
			if (plugin.groupManager.isInGroup(playerName)){
				if (plugin.configHandler.isUsingChatPermissions()){
					if (player.hasPermission("linchat.chat")){
						event.setFormat(applyFormatting(player, message));
					} else {
						plugin.MSG.commandReply(player, ChatColor.RED + "Sorry you do not have the permission to chat");
						event.setCancelled(true);
					}
				} else {
					event.setFormat(applyFormatting(player, message));
				}
			}
		}
	}
	
	public String applyFormatting(Player player, String message){
		String format = plugin.groupManager.applyFormating(player);
		if (player.hasPermission("linchat.color")){
			String replaced = format.replace("{message}", message);
			String formatted = ChatColor.translateAlternateColorCodes('&', replaced);
			return formatted;
		} else {
			String formatted = ChatColor.translateAlternateColorCodes('&', format);
			String replaced = formatted.replace("{message}", message);
			return replaced;
		}
	}

}
