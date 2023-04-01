package ro.gascalupapuc.auth.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.gascalupapuc.auth.model.user.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByEmail(String email);
}
