package me.smok.plugstatics;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.IEssentials;
import com.earth2me.essentials.IUser;
import com.earth2me.essentials.User;
import com.earth2me.essentials.register.payment.methods.VaultEco;
import com.wasteofplastic.askyblock.ASkyBlockAPI;

import net.milkbowl.vault.Vault;
import com.earth2me.essentials.api.Economy;

public class JoinPlayer implements Listener{	   
    public Player pl;
    
    public UUID uid;
    public BigDecimal money;
    public int islevel;
    public String name;
    public String nameCon;
    public String uidselect;
    
	@EventHandler
	public void JoinSkyblock(PlayerJoinEvent e){
		pl = e.getPlayer();	
		uid = pl.getUniqueId();
		uidselect = uid.toString();
		
		islevel = ASkyBlockAPI.getInstance().getIslandLevel(uid);
		name = pl.getDisplayName();
		
		Essentials ess = (Essentials) main.getInstance().getServer().getPluginManager().getPlugin("Essentials");
		money = ess.getUser(uid).getMoney();
		
		delDates();
		insertDates();
		
	}
	
	@EventHandler
	public void ExitSkyblock(PlayerQuitEvent e){
		pl = e.getPlayer();	
		uid = pl.getUniqueId();
		uidselect = uid.toString();
		
		islevel = ASkyBlockAPI.getInstance().getIslandLevel(uid);
		name = pl.getPlayer().getName().toString();
		
		Essentials ess = (Essentials) main.getInstance().getServer().getPluginManager().getPlugin("Essentials");
		money = ess.getUser(uid).getMoney();
		
		delDates();
		insertDates();
	}
	
	public void insertDates() {
	//insertar//
		main.getInstance().SQL.update(String.format("INSERT INTO skyblock (uuid, uname, umoney, uislevel) VALUES ('"+uid+"','"+name+"','"+money+"','"+islevel+"')"));
	//fin insertar//
	}
	public void delDates() {
		main.getInstance().SQL.update(String.format("DELETE FROM skyblock WHERE uuid='"+uid+"'"));
	}
	public void updateDates() {
	//Actualizar
		main.getInstance().SQL.update(String.format("UPDATE skyblock SET uislevel='777' WHERE uuid='smok124'"));
	//Fin Actualizar	
	}
	public void selectDates(String uid) {
		//seleccionar//
		ResultSet rs = main.getInstance().SQL.query(String.format("SELECT * FROM skyblock WHERE uuid='"+uidselect+"'"));
			try {
				while (rs.next())
				{
					//System.out.println("uuid="+rs.getObject("uuid")+
					//", nombre="+rs.getObject("uname")+
					//", money="+rs.getObject("umoney")+
					//", islevel="+rs.getObject("uislevel"));
				}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		//fin seleccionar//
	}
}

