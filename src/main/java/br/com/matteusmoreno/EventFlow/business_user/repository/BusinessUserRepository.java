package br.com.matteusmoreno.EventFlow.business_user.repository;

import br.com.matteusmoreno.EventFlow.business_user.entity.BusinessUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BusinessUserRepository extends JpaRepository<BusinessUser, UUID> {
}
