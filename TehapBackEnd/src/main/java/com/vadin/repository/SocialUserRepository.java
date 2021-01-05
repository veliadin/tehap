package com.vadin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vadin.entity.SocialUsers;
import com.vadin.entity.Users;

@Repository
public interface SocialUserRepository extends JpaRepository<SocialUsers, Long> {

	
	
	@Query(value = "SELECT COUNT(*) FROM social_users WHERE provider_id = ?1", nativeQuery=true)
	int existsByProviderId(String providerId);
	
}
