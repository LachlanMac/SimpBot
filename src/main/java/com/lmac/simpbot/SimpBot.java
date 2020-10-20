package com.lmac.simpbot;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

import com.lmac.simpbot.dao.DAO;
import com.lmac.simpbot.data.VibinVoiceChannel;
import com.lmac.simpbot.debug.Debug;
import com.lmac.simpbot.data.VibinMember;
import com.lmac.simpbot.data.VibinServer;
import com.lmac.simpbot.listeners.SimpListener;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;

public class SimpBot {

	private JDA jda;
	private static SimpBot instance;
	public static final String TOKEN = "NzY1NjQ4MzgzNjE2MjIxMTk0.X4X3nw.3cJyojndESWm52yXxO5Ujr4fdSQ";
	private List<VibinServer> servers;
	private List<Long> whitelist;

	public static SimpBot GetInstance() {

		if (instance == null) {
			instance = new SimpBot();
		}

		return instance;

	}

	private SimpBot() {
		initJDA();
	}

	public static void main(String[] args) {
		new SimpBot();
	}

	public void initJDA() {

		Debug.GetInstance().console("Init JDA");

		servers = new ArrayList<VibinServer>();
		// temporary code that whitelists a server
		whitelist = new ArrayList<Long>();
		whitelist.add(765651618812264509L);

		try {
			jda = JDABuilder.createDefault(TOKEN).addEventListeners(new SimpListener()).build();
		} catch (LoginException e) {
			Debug.GetInstance().error(e.getMessage());
		}
	}

	// Adds a server to be managed
	public void AddServer(Guild g) {
		// create obj representation of a server
		VibinServer vs = new VibinServer(g.getName(), g.getIdLong());

		// check if db has been created and create if not
		if (!DAO.databaseExists(g.getName())) {
			DAO.createNewDatabase(g.getName());
		}

		// for each channel in the guild
		for (VoiceChannel c : g.getVoiceChannels()) {
			// create obj representation
			VibinVoiceChannel vc = new VibinVoiceChannel(c.getName(), c.getIdLong());
			// detect current members in the channel
			for (Member m : c.getMembers()) {
				// attempt to load a member from the database
				VibinMember vm = DAO.GetMemberByID(g.getName(), m.getIdLong());
				if (vm == null) {
					// if member doesn't exist, add them to the database
					vm = DAO.addMember(g.getName(), m.getEffectiveName(), m.getIdLong());
				}
				// add member to the voice channel
				vc.addMember(vm);
			}
			// add channel to the server
			vs.addChannel(vc);
		}
		// add server
		servers.add(vs);
	}

	// function to check if the current server has been whitelisted
	public boolean isWhiteListed(long id) {
		for (int i = 0; i < whitelist.size(); i++) {

			if (id == whitelist.get(i))
				return true;
		}
		return false;
	}

	// get JDA Instance
	public JDA getJDA() {
		return jda;
	}

	// gets an attached server by id
	public VibinServer GetServerByID(long id) {
		for (int i = 0; i < servers.size(); i++) {
			if (servers.get(i).getId() == id)
				return servers.get(i);
		}
		return null;
	}

}
