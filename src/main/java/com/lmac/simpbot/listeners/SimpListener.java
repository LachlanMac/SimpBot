package com.lmac.simpbot.listeners;

import java.util.List;

import com.lmac.simpbot.SimpBot;
import com.lmac.simpbot.dao.DAO;
import com.lmac.simpbot.data.VibinVoiceChannel;
import com.lmac.simpbot.debug.Debug;
import com.lmac.simpbot.data.VibinMember;
import com.lmac.simpbot.data.VibinServer;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.EventListener;

public class SimpListener implements EventListener {
	/*
	 * Listener class that handles several events fired off by a discord guild
	 * 
	 */

	public void sendServerList(MessageChannel channel) {

	}

	public void sendHelp(MessageChannel channel) {

		channel.sendMessage("Command       arg           description\n"
				+ "-------------------------------------------------------\n"
				+ "!help         [none]                  Displays Command List\n"
				+ "!register    [f or m]                Simp or Simpee\n"
				+ "!whitelist   [long]                 Whitelist this server"
				+ "scoreboard  [none]                  Display the Simp Scoreboard").queue();
	}

	// Function that detects events fired off from the discord server

	@Override
	public void onEvent(GenericEvent event) {

		// if the event is a message received event
		if (event instanceof MessageReceivedEvent) {

			MessageReceivedEvent rcvMsg = (MessageReceivedEvent) event;
			checkServer(rcvMsg.getGuild());
			onMessageReceived(rcvMsg);
		} else if (event instanceof GuildVoiceUpdateEvent) {
			onChannelChangedEvent(event);
		} else {

		}

	}

	public VibinServer checkServer(Guild guild) {

		VibinServer s = SimpBot.GetInstance().GetServerByID(guild.getIdLong());

		if (s == null) {
			SimpBot.GetInstance().AddServer(guild);
		}

		return s;
	}

	public void onChannelChangedEvent(GenericEvent event) {
		
		GuildVoiceUpdateEvent e = (GuildVoiceUpdateEvent) event;

		if (e.getChannelJoined() == null) {
			VibinVoiceChannel vs = SimpBot.GetInstance().GetServerByID(e.getChannelLeft().getGuild().getIdLong())
					.getChannelByID(e.getChannelLeft().getIdLong());
			if (vs == null) {
				Debug.GetInstance().console("Server not Configured Yet");
			}
			VibinMember vm = DAO.GetMemberByID(e.getChannelLeft().getGuild().getName(), e.getEntity().getIdLong());
			if (vm == null) {

				vm = DAO.addMember(e.getChannelLeft().getGuild().getName(), e.getEntity().getEffectiveName(),
						e.getEntity().getIdLong());
			}
			vs.removeMember(vm);
		}

		else {

			VibinVoiceChannel vs = SimpBot.GetInstance().GetServerByID(e.getChannelJoined().getGuild().getIdLong())
					.getChannelByID(e.getChannelJoined().getIdLong());
			if (vs == null) {
				Debug.GetInstance().console("Server not Configured Yet");
			}
			VibinMember vm = DAO.GetMemberByID(e.getChannelJoined().getGuild().getName(), e.getEntity().getIdLong());
			if (vm == null) {

				vm = DAO.addMember(e.getChannelJoined().getGuild().getName(), e.getEntity().getEffectiveName(),
						e.getEntity().getIdLong());

			}

			vs.addMember(vm);

		}

	}

	public void onMessageReceived(MessageReceivedEvent event) {

		Message message = event.getMessage();
		String msg = message.getContentRaw();
		MessageChannel channel = event.getChannel();

		if (msg.charAt(0) == '!') {

			String[] messages = msg.split("\\s+");

			if (messages.length == 1) {
				switch (messages[0]) {

				case "!help":
					sendHelp(channel);
					break;

				case "!list-servers":
					sendServerList(channel);
					break;

				default:
					break;
				}
			}

			if (messages.length == 2) {

				if (messages[0].equals("!reboot")) {
					System.out.println("Rebooting");
				}
			}
		}
	}

}
