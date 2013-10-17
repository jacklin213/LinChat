package me.jacklin213.linchat;

import java.util.logging.Logger;

import me.jacklin213.linchat.commands.CommandHandler;
import me.jacklin213.linchat.commands.CommandHelp;
import me.jacklin213.linchat.commands.CommandReload;
import me.jacklin213.linchat.configuration.ConfigHandler;
import me.jacklin213.linchat.configuration.GroupManager;
import me.jacklin213.linchat.utils.MSG;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class LinChat extends JavaPlugin{
	
	public static LinChat plugin;
	public Logger log;
	public ChatListener CL = new ChatListener(this);
	public CommandHandler commandHandler = new CommandHandler(this);
	public ConfigHandler configHandler = new ConfigHandler(this);
	public GroupManager groupManager = new GroupManager(this);	
	public MSG MSG = new MSG(this);
	
	//Command classes
	public CommandReload cmdReload = new CommandReload(this);
	public CommandHelp cmdHelp = new CommandHelp(this);
	
	@Override
	public void onEnable() {
		setLogger();
		log.info("==============[LinChat]==============");
		//Get CommandHandler
		getCommand("linchat").setExecutor(commandHandler);
		log.info("Commands registered");
		
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(this.CL, this);
		log.info("Listener Registered");
		
		log.info("==============[Config]===============");
		configHandler = new ConfigHandler(this.getDataFolder().getAbsolutePath(), getConfig());
		groupManager = new GroupManager();		
		log.info("==============[Loaded]===============");
		log.info("===============[End!]================");
		
		log.info(String.format("Enabled Version %s by jacklin213", getDescription().getVersion()));
	}
	
	@Override
	public void onDisable() {
		log.info(String.format("Disabled Version %s", getDescription().getVersion()));
	}
	
	public void setLogger(){
		log = getLogger();
	}
	
	public void reload(){
		log.info("==============[Reload]===============");
		configHandler.reloadConfig();
		groupManager.reloadManager();
		log.info("==============[Loaded]===============");
	}
	
}
