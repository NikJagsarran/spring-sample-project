package com.nik.sampleproject.users;

/*import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;*/
import javax.validation.constraints.NotEmpty;

/*@Entity // This tells Hibernate to make a table out of this class
@Table(name = "user")*/
public class User {
    //@Id
    private String id;
    
    @NotEmpty(message = "Name is mandatory")
    private String name;
    
    @NotEmpty(message = "Email is mandatory")
    private String email;
    
    private String hobbyID;
    
    public void updateDetails(User newDetails) {
    	this.name = newDetails.getName();
    	this.email = newDetails.getEmail();
    	this.hobbyID = newDetails.getHobbyID();
    }

    public String toString() {
    	return "{ID: " + id + ", Name: " + name + ", Email: " + email + "}";
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

	public void setHobby(String hobbyID) {
		this.hobbyID = hobbyID;
	}
}