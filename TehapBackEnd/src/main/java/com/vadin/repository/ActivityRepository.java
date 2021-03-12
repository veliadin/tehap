package com.vadin.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vadin.entity.Activity;
import com.vadin.entity.Users;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long>, JpaSpecificationExecutor<Activity> {

	Page<Activity> findByUser(Users user, Pageable page);
//INSERT INTO activity_attended_users(attended_activities_id,attended_users_id) VALUES(?1, ?2) WHERE NOT EXISTS (SELECT * FROM activity_attended_users WHERE attended_activities_id=?1 AND attended_users_id = ?2) LIMIT 1
//insert into activity_attended_users(attended_activities_id,attended_users_id) Select (?1,?2) from activity_attended_users Where not exists(select * from activity_attended_users where attended_activities_id=?1 AND attended_users_id = ?2
	@Query(value = "INSERT INTO activity_attended_users (attended_activities_id, attended_users_id) SELECT ?1, ?2 WHERE NOT EXISTS (SELECT attended_activities_id FROM activity_attended_users WHERE attended_activities_id = ?1 AND attended_users_id=?2);", nativeQuery = true)
	void saveAttendUser(long activityId, long userId);
//WHERE id ANY =(SELECT attended_activities_id FROM activity_attended_users WHERE attended_users_id ?1
	@Query(value = "SELECT * FROM activity as a INNER JOIN activity_attended_users as aau ON a.id = aau.attended_activities_id AND aau.attended_users_id = ?1 ",nativeQuery = true)
	List<Activity> findActivitiesByUserAttendedId(long userID);
	
}

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