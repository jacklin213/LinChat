package me.jacklin213.linchat.configuration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import me.jacklin213.linchat.LinChat;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class GroupManager {
	
	public static LinChat plugin;
	
	private ArrayList<String> groupList;
	/*private HashMap<String, String> groups; */
	private HashMap<String, ArrayList<String>> groups; 
	private FileConfiguration config;
	private String groupFolder;
	
	public GroupManager(LinChat instance) {
		plugin = instance;
	}
	
	public GroupManager(){
		setConfig(plugin.configHandler.getConfig());
		setGroupList();
		setGroupFolder(plugin.configHandler.getPluginFolder());
		createGroupFolder();
		groups = new HashMap<String, ArrayList<String>>();
		loadGroups();
		updateGroups();
	}
	
	// For testing/debugging purposes development side
	public void test(){
		plugin.log.info(((ArrayList<String>) getGroupConfig("Admin").getStringList("Players")).toString());
		plugin.log.info("Group contains " + groups.get("Admin").toString());
		for (String player : groups.get("Admin")){
			plugin.log.info(player);
		}
	}
	
	// Methods
	public String applyFormating(Player player){
		String playerName = player.getName();
		String format =  plugin.configHandler.getGlobalFormat();
		String groupName = getPlayersGroup(playerName);
		String chatColor = getGroupChatColor(groupName);
		String prefix = getGroupPrefix(groupName);
		String suffix = getGroupSuffix(groupName);
		//Formating must be done this way
		String acc = format.replace("{chatcolor}", chatColor);
		String agn = acc.replace("{groupname}", groupName);
		String ap = agn.replace("{prefix}", prefix);
		String as = ap.replace("{suffix}", suffix);
		String ann = as.replace("{nickname}", player.getDisplayName());
		String aus = ann.replace("{username}", playerName);
		return aus;
	}
	
	public void createGroupFolder(){
		File folder = new File(groupFolder);
		if (!folder.exists()) {
			folder.mkdir();
		}
	}
	
	public void loadGroups(){
		for (String groupName : groupList){
			getGroupConfig(groupName);
		}
	}
	
	public void reloadManager(){
		groups.clear();
		groupList.clear();
		setConfig(plugin.configHandler.getConfig());
		setGroupList();
		setGroupFolder(plugin.configHandler.getPluginFolder());
		createGroupFolder();
		groups = new HashMap<String, ArrayList<String>>();
		loadGroups();
		updateGroups();
	}
	
	public void updateGroup(String groupName){
		ArrayList<String> converter = (ArrayList<String>) getGroupConfig(groupName).getStringList("Players");
		if (groups.containsKey(groupName)){
			groups.remove(groupName);
			groups.put(groupName, converter);
		} else {
			groups.put(groupName, converter);
		}
		if (converter.size() == 1){
			plugin.log.info(groupName + " now contains: " + converter.size() + " player");
		} else {
			plugin.log.info(groupName + " now contains: " + converter.size() + " players");
		}
	}
	
	public void updateGroups(){
		for (String groupName : groupList){
			updateGroup(groupName);
		}
		plugin.log.info("Groups updated");
	}
	
	//Booleans	
	public boolean isInGroup(String playerName){
		for (String groupName : groupList){
			if (groups.get(groupName).contains(playerName) || groups.get(groupName).contains(playerName.toLowerCase())){
				return true;
			}
		}
		return false;
	}
	
	public boolean isNull(Object object){
		if (object == null){
			return true;
		}
		return false;
	}
	
	//Group File stuff
	
	public void createGroupConfig(String fileName){
		File groupFile = new File(groupFolder + File.separator + fileName + ".yml");
		try {
			if (!groupFile.exists()){
				groupFile.createNewFile();
				YamlConfiguration groupConfig  = YamlConfiguration.loadConfiguration(groupFile);
				
				groupConfig.set("ChatColor", "");
				groupConfig.set("Prefix", "");
				groupConfig.set("Suffix", "");
				groupConfig.set("Players", "");
				groupConfig.save(groupFile);
				plugin.log.info("Created " + fileName + ".yml");
			}
		} catch (IOException e){
			 plugin.log.severe("Unable to create configuration for the Group: " + fileName + ".yml");
			 plugin.log.severe("Please send the error to jacklin213 on http://dev.bukkit.org/profiles/jacklin213");
			 e.printStackTrace();
		}
	}
	
	public YamlConfiguration getGroupConfig(String fileName){
		File groupFile = new File(groupFolder + File.separator + fileName + ".yml");
		 if (!groupFile.exists()) {
			 plugin.log.warning("Group file: " + fileName + ".yml does not exist.");
			 if (groupList.contains(fileName)){
				 createGroupConfig(fileName);
			 }
			 return null;
		 }
		 YamlConfiguration groupConfig = YamlConfiguration.loadConfiguration(groupFile);
		 return groupConfig;
	}
	
	public void reloadGroupConfig(String fileName){
		if (isNull(getGroupConfig(fileName))){
			plugin.log.severe("Error in reloading configuration for group: " + fileName + ".yml");
		}
		plugin.log.info("Group File: " + fileName + ".yml reloaded");
	}
	
	public void saveGroupConfig(String fileName, YamlConfiguration groupConfig){
		try{
			groupConfig.save(new File(getGroupFolder(), fileName + ".yml"));
			plugin.getLogger().info("Group config: " + fileName + ".yml has been saved");
		} catch (Exception ex){ 
			plugin.getLogger().severe("Could not save player config: " + fileName);
		}
	}
	
	//Getters and Setters
	public String getGroupFolder(){
		return groupFolder;
	}
	
	public ArrayList<String> getGroupList(){
		return groupList;
	}
	
	public String getGroupChatColor(String groupName){
		String chatColor = ChatColor.translateAlternateColorCodes('&', getGroupConfig(groupName).getString("ChatColor"));
		if (isNull(chatColor)){
			plugin.log.severe("Unable to load chatcolor for " + groupName + ".yml");
		}
		return chatColor;
	}
	
	public String getGroupPrefix(String groupName){
		String prefix = ChatColor.translateAlternateColorCodes('&', getGroupConfig(groupName).getString("Prefix"));
		if (isNull(prefix)){
			plugin.log.severe("Unable to load prefix for " + groupName + ".yml");
		}
		return prefix;
	}
	
	public String getGroupSuffix(String groupName){
		String suffix = ChatColor.translateAlternateColorCodes('&', getGroupConfig(groupName).getString("Suffix"));
		if (isNull(suffix)){
			plugin.log.severe("Unable to load suffix for " + groupName + ".yml");
		}
		return suffix;
	}
	
	public String getPlayersGroup(String playerName){
		for (String groupName : groupList){
			if (groups.get(groupName).contains(playerName) || groups.get(groupName).contains(playerName.toLowerCase())){
				return groupName;
			}
		}
		return null;
	}
	
	public YamlConfiguration getPlayersGroupConfig(String playerName){
		for (String groupName : groupList){
			if (groups.get(groupName).contains(playerName) || groups.get(groupName).contains(playerName.toLowerCase())){
				return getGroupConfig(groupName);
			}
		}
		return null;
	}
	
	public void setGroupList(){
		ArrayList<String> groupList = (ArrayList<String>) config.getStringList("Groups");
		if (isNull(groupList)){
			plugin.log.severe("Unable to get GroupList, is your groups empty OR does it contain 2 of the same values?");
		}
		plugin.log.info("Loaded Group List with " + groupList.size() + " groups");
		this.groupList = groupList;
	}
	
	public void setConfig(FileConfiguration config){
		this.config = config;
	}
	
	public void setGroupFolder(String pluginFolder){
		this.groupFolder = pluginFolder + File.separator + "Groups";
	}
}
