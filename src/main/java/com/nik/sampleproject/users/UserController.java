package com.nik.sampleproject.users;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

//import com.fasterxml.jackson.core.JsonParseException;
//import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class UserController {
	//@Autowired
	//private UserMySQLService userService;
	
	@Autowired
	private UserMongoService userService;
	
	// Used to convert JSON strings to User objects. jsonUserString shows the expected format.
	ObjectMapper mapper = new ObjectMapper();
	//String userJSONSQL = "{\"id\":1, \"name\":\"Test\", \"email\":\"test@clairvoyantsoft.com\"}";
	//String userJSONMongo = "{\"id\":"5c6dbd8494df8171a08cbf6a", \"name\":\"Test\", \"email\":\"test@clairvoyantsoft.com\"}";
	
	@GetMapping("/users")
	public ResponseEntity<Iterable<UserAndHobby>> getAll() {
        return userService.getAll();
	}
	
	@GetMapping("/users/{id}")
    public User getSingle(@PathVariable(value = "id") String userID) {
        return userService.getSingle(userID);
    }
     
	@PostMapping("/users")
    public UserAndHobby create(@Valid @RequestBody String newUserJSONStr) {
		User newUser = jsonToUser(newUserJSONStr);
        return userService.create(newUser);
    }

    @PutMapping("/users/{id}")
    public User update(@PathVariable(value = "id") String userID, @RequestBody String newUserJSONStr) {
    	System.out.println("newUserJSONStr" + newUserJSONStr);
    	User newUserDetails = jsonToUser(newUserJSONStr);
        return userService.update(userID, newUserDetails);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") String userID) {
        return userService.delete(userID);
    }
    
    private User jsonToUser(String userJSONStr) {
    	User newUser = new User();
		try {
	    	newUser = mapper.readValue(userJSONStr, User.class);
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
		return newUser;
    }
}