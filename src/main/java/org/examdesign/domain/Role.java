package org.examdesign.domain;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity(name="role")
public class Role {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	/*
	//@OneToOne
	@OneToMany(mappedBy="role", cascade=CascadeType.ALL)
	private Collection<User> users;
	*/
	private String role;
	
	public Role() {}
	
	public Role(Integer id)
	{
		this.id = id.longValue();
	}
	
	public Role(Long id)
	{
		this.id = id;
	}
	
	public Role(String role) {
		this.role = role;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	/*
	public Collection<User> getUsers() {
		return users;
	}
	public void setUser(Collection<User> users) {
		this.users = users;
	}
	*/
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
}
