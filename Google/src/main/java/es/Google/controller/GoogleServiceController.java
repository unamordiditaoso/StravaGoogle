package es.Google.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import es.Google.model.User;
import es.Google.service.GoogleService;

public class GoogleServiceController {
	private static final Logger log= LoggerFactory.getLogger(GoogleServiceController.class);	

    private GoogleService googleService;
    
    public GoogleServiceController(GoogleService googleService) {
        this.googleService = googleService;
    }
    
    @GetMapping("/user/all")
    public List<User> getUsers() {
    	log.warn("Getting all users ...");
        return googleService.getAllUsers();
    }

    @GetMapping("/user/details/{id}")
    public User getUser(@PathVariable Long id) {
    	log.warn(String.format("Getting user by id {%d} ...", id));
        return googleService.getUserById(id);
    }
    
    @GetMapping("/user/email/{email}")
    public User getUserByEmail(@PathVariable String email) {
    	log.warn(String.format("Getting user by email {%s} ...", email));
        return googleService.getUserByEmail(email);
    }
             
    @PostMapping("/user/create")
    public ResponseEntity<Object> createUser(@RequestBody User user) {
    	log.warn("Creating a new User ...");
    	
       	switch (googleService.createUser(user)) {
       		case FAIL:
       			return ResponseEntity.unprocessableEntity().body(String.format("User with email {%s} creation fails.", user.getEmail()));
    	    default:
      	    	return ResponseEntity.ok(String.format("A new User (%s) has been created.", user.getEmail()));
    	}
    }
    
    @PutMapping("/user/update/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable Long id, @RequestBody User user) {
    	log.warn(String.format("Updating user with id {%d} ...", id));
    	
    	switch (googleService.updateUser(user, id)) {
   			case FAIL:
   				return ResponseEntity.unprocessableEntity().body(String.format("User with email {%s} cannot be updated", user.getEmail()));
   			default:
   				return ResponseEntity.ok(String.format("User with email {%s} has been updated.", user.getEmail()));
    	}
    }
   
    @DeleteMapping("user/delete/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
    	log.warn(String.format("Deleting user with id {%d} ...", id));
    	
    	switch (googleService.deleteUser(id)) {    	
			case FAIL:
   				return ResponseEntity.unprocessableEntity().body(String.format("User with id {%s} cannot be deleted", id.toString()));
   			default:
   				return ResponseEntity.ok(String.format("User with email {%s} has been deleted.", id.toString()));
    	}
    }
    
    @DeleteMapping("/user/delete/all")
    public ResponseEntity<Object> deleteUsers() {
    	log.warn("Deleting ALL users ...");
    	
    	switch (googleService.deleteAllUsers()) {
			case FAIL:
   				return ResponseEntity.unprocessableEntity().body("Deletion of all users fails");
   			default:
   				return ResponseEntity.ok("All users has been deleted.");
    	}   
    }
}
