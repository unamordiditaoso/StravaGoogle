package es.Google.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import es.Google.model.User;

public interface GoogleRepository extends JpaRepository<User, Long>{
	Optional<User> findByEmail(String email);
}
