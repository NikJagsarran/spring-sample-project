package com.nik.sampleproject.hobbies;

//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

//@Entity // This tells Hibernate to make a table out of this class
//@Table(name = "user")
public class Hobby {
    //@Id
    private String id;
    
    @NotEmpty(message = "Name is mandatory")
    private String name;
    
    @NotEmpty(message = "Category is mandatory")
    private String category;
    
    @NotEmpty(message = "Outdoor is mandatory")
    private boolean outdoor;
    
    public Hobby() {
    	name = "None";
    	category = "None";
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public boolean isOutdoor() {
		return outdoor;
	}

	public void setOutdoor(boolean outdoor) {
		this.outdoor = outdoor;
	}
}