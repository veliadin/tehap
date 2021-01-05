package com.vadin.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.vadin.entity.Activity;
import com.vadin.entity.Users;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long>, JpaSpecificationExecutor<Activity> {

	Page<Activity> findByUser(Users user, Pageable page);

	/*
	 * Page<Activity> findByIdLessThan(long id, Pageable page);
	 * 
	 * Page<Activity> findByIdLessThanAndUser(long id, Users user, Pageable page);
	 * 
	 * long countByIdGreaterThan(long id);
	 * 
	 * long countByIdGreaterThanAndUser(long id, Users user);
	 * 
	 * List<Activity> findByIdGreaterThan(long id, Sort sort);
	 * 
	 * List<Activity> findByIdGreaterThanAndUser(long id, Users user, Sort sort);
	 */
}
