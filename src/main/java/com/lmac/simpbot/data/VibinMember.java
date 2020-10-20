package com.lmac.simpbot.data;

public class VibinMember {

	private String name;
	private long id;
	private float simpRating;
	private GenderType gender;

	public enum GenderType {
		MALE, FEMALE, NB
	}

	public VibinMember(String name, long id, float simpRating, int genderCode) {
		this.name = name;
		this.id = id;
		this.simpRating = simpRating;
		this.gender = parseGenderCode(genderCode);
	}

	public GenderType parseGenderCode(int code) {

		switch (code) {

		case 0:
			return GenderType.NB;
		case 1:
			return GenderType.MALE;
		case 2:
			return GenderType.FEMALE;
		default:
			return GenderType.NB;
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

	public float getSimpRating() {
		return simpRating;
	}

	public void setSimpRating(float simpRating) {
		this.simpRating = simpRating;
	}

	public GenderType getGender() {
		return gender;
	}

	public void setGender(GenderType gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {

		return "ID:" + id + "  " + name + "\nSimp Rating: " + simpRating;

	}
}
