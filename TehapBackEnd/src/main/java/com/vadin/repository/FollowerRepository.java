package com.vadin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vadin.entity.Followers;
import com.vadin.entity.Users;

@Repository
public interface FollowerRepository extends JpaRepository<Followers, Long> {

	@Query(value = "SELECT COUNT(from_user_fk) FROM followers WHERE to_user_fk = ?1", nativeQuery = true)
	long userFollowersCount(long userId);
	
	@Query(value = "SELECT COUNT(to_user_fk) FROM followers WHERE from_user_fk = ?1", nativeQuery = true)
	long userFollowingCount(long userId);
	
	@Query(value = "SELECT COUNT(to_user_fk) FROM followers WHERE from_user_fk = ?1 AND to_user_fk = ?2", nativeQuery = true)
	int theUserFollow(Users loggedUser, Users followingUserE);
	
	@Query(value = "DELETE FROM followers WHERE from_user_fk= ?1 AND to_user_fk = ?2 RETURNING id",nativeQuery = true)
	int deleteBytoIdandfromId(Users loggedUser, Users followingUserE);

}
