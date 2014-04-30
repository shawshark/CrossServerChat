package me.shawshark.crossserverchat;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerListener implements Listener {
	
	@EventHandler
	public void OnChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		String message = event.getMessage();
		
		/* if message doesn't start with '!cross' return. */
		if(!message.startsWith(CrossServerChat.ListenFor)) {
			return;
		}
		
		/* If player doesn't have permissions, return. */
		if(!player.hasPermission("Crosschat.use")) {
			event.setCancelled(true);
			player.sendMessage(ChatColor.DARK_BLUE + "[Cross-Server-Chat] You don't have permissions to use '!cross'");
			return;
		}
		
		if(message.split(" ").length == 1) {
			event.setCancelled(true);
			player.sendMessage(ChatColor.RED + "Proper usage: " + CrossServerChat.ListenFor + " <message>");
			return;
		}
		
		/* Send the message to all servers using this plugin on the network. */
		CrossServerChat.messageRequest(
				player.getName() + CrossServerChat.split + message.replace(CrossServerChat.ListenFor + " ", "") + CrossServerChat.split + CrossServerChat.username
		);
		
		/* This is just to notifiy the user that it was sent, It actually looks like the message wasn't sent on the sending server. */
		player.sendMessage(ChatColor.RED + "global message sent!");
		
		/* Just to remove the !cross in the actual message on that server. */
		event.setMessage(event.getMessage().replace(CrossServerChat.ListenFor + " ", ""));
	}
}
