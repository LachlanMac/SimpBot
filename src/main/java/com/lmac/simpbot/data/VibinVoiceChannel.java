package com.lmac.simpbot.data;

import java.util.ArrayList;
import java.util.List;

import com.lmac.simpbot.data.VibinMember.GenderType;
import com.lmac.simpbot.debug.Debug;

//This class represents a voice channel in discord.

public class VibinVoiceChannel {

	// class vars
	private String name; // Effective name of the channel per discord
	private long id; // Unique Snowflake ID of the channel
	private List<VibinMember> connectedMembers; // List of current members who are connected

	private float simpLeaveScore = 0f;
	private float simpJoinScore = 0f;

	private final float SIMP_JOIN_SCORE_MAX = 5.0f;
	private final float SIMP_LEAVE_SCORE_MAX = 5.0f;

	// We need two simp numbers: one that is activated when a girl joins a channel,
	// the other that is activated when a girl leaves a channel

	// Constructor
	public VibinVoiceChannel(String name, long id) {
		this.name = name;
		this.id = id;
		connectedMembers = new ArrayList<VibinMember>();
	}

	// Adds a member to the channel
	public void addMember(VibinMember vm) {

		for (int i = 0; i < connectedMembers.size(); i++) {
			// check to make sure this user isn't already connected? I don't know if this is
			// needed
			if (vm.getId() == connectedMembers.get(i).getId())
				return;
		}

		if (vm.getGender() == GenderType.FEMALE) {
			// a girl left, so we better start the score counter. Those who leave quickest
			// will get the highest simp score

		} else if (vm.getGender() == GenderType.MALE) {
			// a male left, so we better see if a female also left recently

		}

		Debug.GetInstance().console("Adding Member " + vm.getName() + " to channnel " + name);

		connectedMembers.add(vm);
	}

	// remove member frm channel
	public void removeMember(VibinMember vm) {

		for (int i = 0; i < connectedMembers.size(); i++) {

			if (vm.getId() == connectedMembers.get(i).getId()) {
				connectedMembers.remove(vm);
				Debug.GetInstance().console("Removing Member " + vm.getName() + " to channnel " + name);
			}

		}

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

	public List<VibinMember> getConnectedMembers() {
		return connectedMembers;
	}

	public void setConnectedMembers(List<VibinMember> connectedMembers) {
		this.connectedMembers = connectedMembers;
	}

}
