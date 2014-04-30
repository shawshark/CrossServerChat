package me.shawshark.crossserverchat;

import java.io.UnsupportedEncodingException;
import java.util.Collections;

import lilypad.client.connect.api.Connect;
import lilypad.client.connect.api.request.RequestException;
import lilypad.client.connect.api.request.impl.MessageRequest;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class CrossServerChat extends JavaPlugin {
	
	public static Connect connect;
	public static String channel = "CrossServerChat", split = "SplitString";
	
	public static String format;
	public static String username;
	public static String ListenFor;
	
	public void onEnable() {
		
	    saveDefaultConfig();
		
	    format = ChatColor.translateAlternateColorCodes('&', getConfig().getString("format"));
		
	    connect = Bukkit.getServer().getServicesManager().getRegistration(Connect.class).getProvider();
	    connect.registerEvents(new LilyListener());
	    
	    username = connect.getSettings().getUsername();
	    
	    ListenFor = getConfig().getString("ListenFor");
	    if(ListenFor.contains(" ")) {
	    	System.out.println("[Cross Server Chat] ListenFor Command contains a space, setting default.. '!cross'");
	    	ListenFor = "!cross";
	    }
	    
	    getServer().getPluginManager().registerEvents(new PlayerListener(), this);
	}
	
	public void onDisable() {
	   connect.unregisterEvents(new LilyListener());
	   connect = null;
	}
	
	public static void messageRequest(String message) {
	   try {
        	MessageRequest request = new MessageRequest(Collections.<String> emptyList(), channel, message);
            	connect.request(request); 
           } catch (UnsupportedEncodingException | RequestException e) {
        	e.printStackTrace();
           }
	}
}
