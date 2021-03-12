package com.vadin.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Followers {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@ManyToOne
	@JoinColumn(name = "from_user_fk")
	private Users from;

	@ManyToOne
	@JoinColumn(name = "to_user_fk")
	private Users to;

	public Followers() {
		super();
	}

	public Followers(long id, Users from, Users to) {
		super();
		this.id = id;
		this.from = from;
		this.to = to;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Users getFrom() {
		return from;
	}

	public void setFrom(Users from) {
		this.from = from;
	}

	public Users getTo() {
		return to;
	}

	public void setTo(Users to) {
		this.to = to;
	}

}
