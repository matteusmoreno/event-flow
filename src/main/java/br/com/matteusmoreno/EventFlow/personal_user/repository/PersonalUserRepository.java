package br.com.matteusmoreno.EventFlow.personal_user.repository;

import br.com.matteusmoreno.EventFlow.personal_user.entity.PersonalUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PersonalUserRepository extends JpaRepository<PersonalUser, UUID> {
}
