package es.Google.service;

import java.util.List;
import java.util.Optional;

import es.Google.dao.GoogleRepository;
import es.Google.model.User;

public class GoogleService {
private GoogleRepository googleRepository;
	
	public enum GoogleServiceResult {
		SUCCESS,
		FAIL;
	}

	public GoogleService(GoogleRepository googleRepository) {
		this.googleRepository = googleRepository;
	}

	public User getUserById(Long id) {
		Optional<User> result = googleRepository.findById(id);
		
		return result.isEmpty() ? null : result.get();
	}

	public User getUserByEmail(String email) {
		Optional<User> result = googleRepository.findByEmail(email);
		
		return result.isEmpty() ? null : result.get();
	}

	public List<User> getAllUsers() {
		return googleRepository.findAll();
	}

	public GoogleServiceResult createUser(User user) {
		Optional<User> result = googleRepository.findByEmail(user.getEmail());
		
		if (result.isEmpty()) {
			User savedUser = googleRepository.save(user);
			
			if (savedUser != null) {
				return GoogleServiceResult.SUCCESS;
			}
		}
		
		return GoogleServiceResult.FAIL;
	}

	public GoogleServiceResult updateUser(User user, Long id) {
		Optional<User> result = googleRepository.findById(user.getId());
		
		if (!result.isEmpty()) {
			User updatedUser = result.get();
			
			updatedUser.setPassword(user.getPassword());
			updatedUser.setEmail(user.getEmail());

			googleRepository.save(updatedUser);
			
			if (!googleRepository.findById(id).isEmpty()) {
				return GoogleServiceResult.SUCCESS;
			}
		}
		
		return GoogleServiceResult.FAIL;
	}

	public GoogleServiceResult deleteUser(Long id) {
		Optional<User> result = googleRepository.findById(id);
		
		if (!result.isEmpty()) {
			googleRepository.delete(result.get());

			if (googleRepository.findById(id).isEmpty()) {
				return GoogleServiceResult.SUCCESS;
			}
		}
		
		return GoogleServiceResult.FAIL;
	}

	public GoogleServiceResult deleteAllUsers() {
		GoogleServiceResult result = GoogleServiceResult.SUCCESS;
		
		for (User u : googleRepository.findAll()) {
			googleRepository.deleteById(u.getId());

			if (!googleRepository.findById(u.getId()).isEmpty()) {
				result = GoogleServiceResult.FAIL;
			}
		}

		return result;
	}
}
