package com.nik.sampleproject.users;

import com.nik.sampleproject.hobbies.Hobby;

public class UserAndHobby {
	private String id;
	private String name;
    private String email;
    private String hobbyID;
    private String hobbyName;
    private String hobbyCat;
    private boolean hobbyOutdoor;
    
    public UserAndHobby(User u, Hobby h) {
    	init(u, h);
    }
    
    public UserAndHobby(User u) {
    	init(u, null);
    }
    
    private void init(User u, Hobby h) {
    	id = u.getId();
    	name = u.getName();
    	email = u.getEmail();
    	
    	if (h != null) {
    		hobbyID = h.getId();
    		hobbyName = h.getName();
    		hobbyCat = h.getCategory();
    		hobbyOutdoor = h.isOutdoor();
    	}
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHobbyID() {
		return hobbyID;
	}

	public void setHobbyID(String hobbyID) {
		this.hobbyID = hobbyID;
	}

	public String getHobbyName() {
		return hobbyName;
	}

	public void setHobbyName(String hobbyName) {
		this.hobbyName = hobbyName;
	}

	public String getHobbyCat() {
		return hobbyCat;
	}

	public void setHobbyCat(String hobbyCat) {
		this.hobbyCat = hobbyCat;
	}

	public boolean isHobbyOutdoor() {
		return hobbyOutdoor;
	}

	public void setHobbyOutdoor(boolean hobbyOutdoor) {
		this.hobbyOutdoor = hobbyOutdoor;
	}
}
