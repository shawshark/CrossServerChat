package me.shawshark.crossserverchat;

import java.io.UnsupportedEncodingException;

import org.bukkit.Bukkit;

import lilypad.client.connect.api.event.EventListener;
import lilypad.client.connect.api.event.MessageEvent;

public class LilyListener {
	
	@EventListener
	public void onMessage(MessageEvent event) {
		String[] data = null;
		
		if(!event.getChannel().equalsIgnoreCase(CrossServerChat.channel)) { 
			return; 
		}
		
		try { 
			data = event.getMessageAsString().split(CrossServerChat.split); 
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		/* So the sending server doesn't broadcast the message. */
		if(data[2].equalsIgnoreCase(CrossServerChat.username)) {
			return;
		}
		
		Bukkit.broadcastMessage(
				CrossServerChat.format.replace("{player}", data[0]).replace("{message}", data[1])
		);
	}
}
