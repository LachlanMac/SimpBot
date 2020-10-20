package com.lmac.simpbot.data;

import java.util.ArrayList;
import java.util.List;

import net.dv8tion.jda.api.JDA;

public class VibinServer {

	private String name;
	private long id;
	private List<VibinVoiceChannel> channels;

	public VibinServer(String name, long id) {
		this.name = name;
		this.id = id;
		channels = new ArrayList<VibinVoiceChannel>();
	}

	public void sync(JDA jda) {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void addChannel(VibinVoiceChannel channel) {
		channels.add(channel);
	}

	public List<VibinVoiceChannel> getChannels() {
		return channels;
	}

	public VibinVoiceChannel getChannelByID(long id) {

		for (int i = 0; i < channels.size(); i++) {
			if (channels.get(i).getId() == id) {
				return channels.get(i);
			}
		}
		return null;
	}

}
