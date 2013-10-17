package me.jacklin213.linchat.configuration;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import me.jacklin213.linchat.LinChat;

public class ConfigHandler {
	
	public static LinChat plugin;
	
	private FileConfiguration config;
	private File configFile;
	private String infoColor;
	private String pluginFolder;
	private String pluginName;
	private boolean defaultChatPlugin;
	private boolean usingChatPermissions;
	
	public ConfigHandler(LinChat instance){
		plugin = instance;
	}
	
	public ConfigHandler(String dataFolder, FileConfiguration config){
		pluginFolder = dataFolder;
		setConfig(config);
		createConfig();
		setPluginName();
		setInfoColor();
		isUsingChatPermissions();
	}
	
	public void createConfig(){
		configFile = new File(pluginFolder + File.separator + "config.yml");
		
		if (!configFile.exists()){
			plugin.log.info("Cannot find config.yml, Generating now....");
			plugin.log.info("Config generated !");
			getConfig().options().copyDefaults(true);
			plugin.saveDefaultConfig();
		}
	}
	
	public void reloadConfig(){
		plugin.reloadConfig();
		setConfig(plugin.getConfig());
		plugin.log.info("Config reloaded");
	}

	//Booleans
	public boolean isDefaultChatPlugin() {
		defaultChatPlugin = config.getBoolean("Default", true);
		if (defaultChatPlugin){
			plugin.log.info("Set to default chat plugin");
		} else {
			plugin.log.info("Set to secondary chat plugin");
		}
		return defaultChatPlugin;
	}
	
	
	public boolean isNull(Object object){
		if (object == null){
			return true;
		}
		return false;
	}
	
	public boolean isUsingChatPermissions() {
		usingChatPermissions = config.getBoolean("ChatPermission", isDefaultChatPlugin());
		if (usingChatPermissions){
			plugin.log.info("Using linchat.chat permission for chatting");
		} else {
			plugin.log.info("Disabled linchat.chat Permission");
		}
		return usingChatPermissions;
	}

	//Getters and Setters
	public FileConfiguration getConfig() {
		return config;
	}
	
	public String getInfoColor(){
		return infoColor;
	}
	
	public String getGlobalFormat(){
		String format = config.getString("Format");
		if (isNull(format)){
			plugin.log.severe("Unable to load global format");
			return null;
		}
		return format;
	}
	
	public String getPluginFolder(){
		return pluginFolder;
	}
	
	public String getPluginName() {
		return pluginName;
	}
	
	public void setConfig(FileConfiguration config) {
		this.config = config;
	}
	
	public void setInfoColor(){
			String infoColor = ChatColor.translateAlternateColorCodes('&', config.getString("Plugin.Message.Info"));
			if (isNull(infoColor)){
				plugin.log.severe("Unable to get 'Plugin.Message.Info', Defaulting to &6(Gold)");
				this.infoColor = ChatColor.translateAlternateColorCodes('&', "&6");
			}
			plugin.log.info("Message Info color set to: " + infoColor);
			this.infoColor = infoColor;
	}
	
	public void setPluginName() {
		String pluginName = ChatColor.translateAlternateColorCodes('&', config.getString("Plugin.Name"));
		if (isNull(pluginName)){
			plugin.log.severe("Unable to get 'Plugin.Name', Defaulting to '&c[&aLinChat&c]'");
			this.pluginName = ChatColor.translateAlternateColorCodes('&', "&c[&aLinChat&c]");
		}
		plugin.log.info("Plugin Name set to: " + pluginName);
		this.pluginName = pluginName;
	}

}
