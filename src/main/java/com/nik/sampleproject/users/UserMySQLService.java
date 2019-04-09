package com.nik.sampleproject.users;

/*import org.springframework.stereotype.Service;

import com.nik.sampleproject.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Service*/
public class UserMySQLService {
	/*@Autowired
	public UserMySQLRepository userRepo;

	public ResponseEntity<Iterable<User>> getAll() {
		//return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
        	return ResponseEntity.ok(userRepo.findAll());
        } catch (Exception e) {
        	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
	}

    public User getSingle(int userID) {
        return userRepo.findById(userID).orElseThrow(() -> new ResourceNotFoundException("User", "id", userID));
    }

    public User create(User newUser) {
		return userRepo.save(newUser);
    }

    public User update(int userID, User newUserDetails) {
        User user = getSingle(userID);
        user.setName(newUserDetails.getName());
        user.setEmail(newUserDetails.getEmail());
        return userRepo.save(user);
    }

    public ResponseEntity<?> delete(int userID) {
        userRepo.deleteById(userID);
        return ResponseEntity.ok().build();
    }*/
}