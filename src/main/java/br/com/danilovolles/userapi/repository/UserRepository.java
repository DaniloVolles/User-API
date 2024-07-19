package br.com.danilovolles.userapi.repository;

import br.com.danilovolles.userapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByUsername(String username);
    List<User> findAllByOrderByRegistrationDateDesc();
}
