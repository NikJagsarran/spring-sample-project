package com.nik.sampleproject.users;

import static com.nik.sampleproject.akka.SpringExtension.SPRING_EXTENSION_PROVIDER;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.nik.sampleproject.hobbies.Hobby;
import com.nik.sampleproject.hobbies.HobbyService;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.pattern.Patterns;
import akka.util.Timeout;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.FiniteDuration;

@Service
public class UserMongoService {
	@Autowired
	public UserMongoRepository userMongoRepo;
	
	@Autowired
	public HobbyService hobbyService;
	
	@Autowired
	ActorSystem system;
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	String kafkaTopic = "sample-project";
	
	public ResponseEntity<Iterable<UserAndHobby>> getAll() {
		akkaSendMessage("Begin fetching all users and hobbies");
		Iterator<Hobby> hobbies = null;
		try {
			hobbies = hobbyService.findAll().getBody().iterator();
			akkaSendMessage("Hobbies successfully fetched");
		} catch (Exception e) {
			akkaSendMessage("Error getting hobbies");
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
		
		Iterator<User> users = null;
		try {
			users = userMongoRepo.findAll().iterator();
			akkaSendMessage("Users successfully fetched");
		} catch (Exception e) {
			akkaSendMessage("Error getting users");
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
		
		Map<String, Hobby> hobbyMap = genHobbyMap(hobbies);
		List<UserAndHobby> toReturn = new ArrayList<UserAndHobby>();
		
		while (users.hasNext()) {
    		User currUser = users.next();
    		String currHobbyID = currUser.getHobbyID();
    		
    		if (currHobbyID == null) {
    			// ID of empty "None" hobby that was manually created.
    			currHobbyID = "000000000000000000000000";
    		}
    		Hobby currHobby = hobbyMap.get(currHobbyID);
    		toReturn.add(new UserAndHobby(currUser, currHobby));
    	}
		akkaSendMessage("Finished fetching users and hobbies");
		return ResponseEntity.ok((Iterable<UserAndHobby>) toReturn);
	}
	
	private Map<String, Hobby> genHobbyMap(Iterator<Hobby> hobbies) {
		Map<String, Hobby> toReturn = new HashMap<String, Hobby>();
		while (hobbies.hasNext()) {
			Hobby curr = hobbies.next();
			toReturn.put(curr.getId(), curr);
		}
		return toReturn;
	}
	
    public User getSingle(String userID) {
        //return userMongoRepo.findById(userID);//.orElseThrow(() -> new ResourceNotFoundException("User", "id", userID));
        Optional<User> res = userMongoRepo.findById(userID);
        if (!res.isPresent()) {
        	akkaSendMessage("Error fetching user with ID: " + userID);
        }
        akkaSendMessage("Successfully fetched user with ID: " + userID);
        return res.get();
    }
     
    public UserAndHobby create(User newUser) {
    	userMongoRepo.save(newUser);
    	UserAndHobby toReturn = new UserAndHobby(newUser, hobbyService.getSingle(newUser.getHobbyID()));
    	akkaSendMessage("Saving new user: " + newUser.toString());
		return toReturn;
    }

    public User update(String userID, User newUserDetails) {
        User user = getSingle(userID);
        akkaSendMessage("Updating user with the ID: " + userID);
        user.updateDetails(newUserDetails);
        return userMongoRepo.save(user);
    }

    public ResponseEntity<?> delete(String userID) {
        userMongoRepo.deleteById(userID);
        akkaSendMessage("Deleting user with the ID: " + userID);
        return ResponseEntity.ok().build();
    }
	
	@KafkaListener(topics = "sample-project", groupId = "main-group")
	public void kafkaListen(String msg) {
		
		System.out.println("Received Message in main-group: " + msg);
	}
	
	// Akka below.
	private void akkaSendMessage(String msg) {
		ActorRef kafka = system.actorOf(SPRING_EXTENSION_PROVIDER.get(system).props("kafkaActor"),
				"kafka" + System.nanoTime());

		FiniteDuration duration = FiniteDuration.create(5, TimeUnit.SECONDS);
		Timeout timeout = Timeout.durationToTimeout(duration);

		Future<Object> kafkaActorResult = Patterns.ask(kafka, msg, timeout);
		try {
			//System.out.println(Await.result(kafkaActorResult, duration));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}