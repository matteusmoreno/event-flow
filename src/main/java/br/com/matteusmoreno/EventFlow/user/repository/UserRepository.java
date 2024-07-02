package br.com.matteusmoreno.EventFlow.user.repository;

import br.com.matteusmoreno.EventFlow.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    User findByEmail(String email);

    boolean existsByEmail(String email);
}
