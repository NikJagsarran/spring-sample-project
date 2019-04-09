package com.nik.sampleproject.hobbies;

import org.springframework.stereotype.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Service
public class HobbyService {
	@Autowired
	public HobbyRepository hobbyRepo;
	
	public ResponseEntity<Iterable<Hobby>> findAll() {
        try {
        	return ResponseEntity.ok(hobbyRepo.findAll());
        } catch (Exception e) {
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
	}
	
    public Hobby getSingle(String hobbyID) {
        //return hobbyRepo.findById(userID);//.orElseThrow(() -> new ResourceNotFoundException("User", "id", userID));
        Optional<Hobby> res = hobbyRepo.findById(hobbyID);
        if (!res.isPresent()) {
        	// Error handling if hobby wasn't found.
        }
        return res.get();
    }
     
    /*public Hobby create(Hobby newUser) {
		return hobbyRepo.save(newUser);
    }

    public Hobby update(String userID, Hobby newUserDetails) {
    	Hobby user = getSingle(userID);
        
        // TODO: Must be a better way to do this; setting all fields without the need to add each one.
        user.setName(newUserDetails.getName());
        user.setEmail(newUserDetails.getEmail());
        user.setHobby(newUserDetails.getHobby());
        return hobbyRepo.save(user);
    }

    public ResponseEntity<?> delete(String userID) {
        hobbyRepo.deleteById(userID);
        return ResponseEntity.ok().build();
    }*/
}