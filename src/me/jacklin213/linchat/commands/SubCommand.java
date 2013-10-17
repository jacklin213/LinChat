package me.jacklin213.linchat.commands;

import org.bukkit.command.CommandSender;

public abstract class SubCommand {
	
	public abstract void start(CommandSender sender, String[] args, String permissionNode);
}
