package com.lmac.simpbot;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

import com.lmac.simpbot.dao.DAO;
import com.lmac.simpbot.data.VibinChannel;
import com.lmac.simpbot.data.VibinMember;
import com.lmac.simpbot.data.VibinServer;
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

	public SimpBot() {
		initJDA();

	}

	public static void main(String[] args) {
		new SimpBot();

	}

	public void initJDA() {

		servers = new ArrayList<VibinServer>();
		whitelist = new ArrayList<Long>();

		whitelist.add(765651618812264509L);

		try {
			jda = JDABuilder.createDefault(TOKEN).addEventListeners(new SimpListener()).build();
		} catch (LoginException e) {
			System.out.println("Error: " + e.getMessage());
		}

	}

	public JDA getJDA() {
		return jda;
	}

	public VibinServer GetServerByID(long id) {

		for (int i = 0; i < servers.size(); i++) {

			if (servers.get(i).getId() == id)
				return servers.get(i);

		}

		return null;
	}

	public void AddServer(Guild g) {
		VibinServer vs = new VibinServer(g.getName(), g.getIdLong());

		if (!DAO.databaseExists(g.getName())) {
			DAO.createNewDatabase(g.getName());
		}

		List<VoiceChannel> channels = g.getVoiceChannels();
		for (VoiceChannel c : channels) {

			VibinChannel vc = new VibinChannel(c.getName(), c.getIdLong());

			for (Member m : c.getMembers()) {

				VibinMember vm = DAO.GetMemberByID(g.getName(), m.getIdLong());
				if (vm == null) {
					vm = DAO.addMember(g.getName(), m.getEffectiveName(), m.getIdLong());
				}

				System.out.println("ADDING " + vm);
				vc.addMember(vm);
			}

			vs.addChannel(vc);

		}
		servers.add(vs);
	}

	public boolean isWhiteListed(long id) {

		for (int i = 0; i < whitelist.size(); i++) {

			if (id == whitelist.get(i))
				return true;

		}

		return false;
	}

}
