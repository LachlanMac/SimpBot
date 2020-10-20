package com.lmac.simpbot.data;

import java.util.ArrayList;
import java.util.List;

public class VibinChannel {

	private String name;
	private long id;
	private List<VibinMember> connectedMembers;

	public VibinChannel(String name, long id) {
		this.name = name;
		this.id = id;
		connectedMembers = new ArrayList<VibinMember>();
	}

	public void addMember(VibinMember vm) {

		for (int i = 0; i < connectedMembers.size(); i++) {

			if (vm.getId() == connectedMembers.get(i).getId())
				return;

		}

		System.out.println("Adding Member");

		connectedMembers.add(vm);
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

	public void removeMember(VibinMember vm) {

		for (int i = 0; i < connectedMembers.size(); i++) {

			if (vm.getId() == connectedMembers.get(i).getId()) {
				connectedMembers.remove(vm);
				System.out.println("Removing member");
			}

		}

	}

}
