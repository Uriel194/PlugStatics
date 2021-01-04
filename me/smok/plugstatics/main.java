package me.smok.plugstatics;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.earth2me.essentials.IEssentials;

import paulek.mysql.Main;
import paulek.mysql.MySQL;



public class main extends JavaPlugin{
	private static main plugin;
	private static IEssentials ess;
	private YamlConfiguration c;
	FileConfiguration config;
    File cfile;
    //MySQL
    private PluginDescriptionFile pdf;
    
    public String HOST = getConfig().getString("host");
    public String DATABASE = getConfig().getString("database");
    public String USER = getConfig().getString("user");
    public String PASS = getConfig().getString("pass");
   
    public MySQL SQL;
   
    public boolean connected;
    //Fin MySQL
@Override
public void onEnable(){
	  
		plugin = this;
		PluginManager pm = Bukkit.getServer().getPluginManager();
		pm.registerEvents(new JoinPlayer(), this);
	    config = getConfig();
	    config.options().copyDefaults(true);
	    
	    saveConfig();
	    
	    cfile = new File(getDataFolder(), "config.yml");
	    c = new YamlConfiguration();
	    
	    try {
			c.load(cfile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
	    
    ConsoleCommandSender lccs = Bukkit.getServer().getConsoleSender();
    lccs.sendMessage("§7[]=======================[§3§lPlug§d§lStatics§7]===================[]");
    lccs.sendMessage("                      §bStatus: §2ON");
    lccs.sendMessage("                      §aBY: §2SmokPlay");
    lccs.sendMessage("§7[]====================================================[]");
    
    this.pdf = this.getDescription();
    
    this.SQL = new MySQL((Main) Bukkit.getServer().getPluginManager().getPlugin("MySQL"), pdf.getName());
    this.connected = SQL.Connect(HOST, DATABASE, USER, PASS);
   
    if(!this.connected) {
        this.getLogger().info("");
    }
		
}

public void onDisable(){
  saveConfig();
  
  ConsoleCommandSender lccs = Bukkit.getServer().getConsoleSender();
  lccs.sendMessage("§7[]=======================[§3§lPlug§d§lStatics§7]===================[]");
  lccs.sendMessage("                      §bStatus: §cOFF         ");
  lccs.sendMessage("                      §aBY: §2SmokPlay");
  lccs.sendMessage("§7[]====================================================[]");
}
public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
	  Player p = (Player) sender;
	  
	  if (commandLabel.equalsIgnoreCase("plugst") && (p.hasPermission("plugst.admin") || p.isOp())) {
      if (args.length == 0){
      	p.sendMessage("§7[]======[§3§lPlug§d§lStatics§7]======[]");
      	p.sendMessage("§a /plugst reload - reload config");
      	p.sendMessage(" §7[]==========================[]");
      }else if (args.length == 1){
        if (args[0].equalsIgnoreCase("reload")){
        	reloadFiles(cfile, config);
        	p.sendMessage(getConfig().getString("msg_reload").replace("&", "§"));
        }
        }else {
        	return false;
        }
       
    
    }
	return false;
    }

public void saveCustomYml(FileConfiguration ymlConfig, File ymlFile){
	  try{
		  ymlConfig.save(ymlFile);
	  }catch(IOException e){
		  e.printStackTrace();
	  }
}

public void reloadFiles(File file, FileConfiguration configuration){
	  try{
		  configuration.load(file);
	  }catch(Exception e){
		  e.printStackTrace();
	  }
}
public String getMessage(){
	return config.getString("message");
}

public static main getInstance(){
	return plugin;
}
public static IEssentials getInstanceEss(){
	return ess;

}
public FileConfiguration  Config(){
	return c;
}
}