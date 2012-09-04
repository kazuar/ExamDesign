package org.examdesign.service;

import java.util.List;

import org.examdesign.domain.Role;
import org.examdesign.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class RoleService {

	@Autowired
	private RoleRepository repository;
	
	public Boolean create(Role role) {
		Role saved = repository.save(role);
		if (saved == null) 
			return false;
		
		return true;
	}
	
	public Boolean update(Role role) {
		Role existingRole = repository.findOne(role.getId());

		if (existingRole == null) 
			return false;
		
		existingRole.setRole(role.getRole());
		
		Role saved = repository.save(existingRole);
		if (saved == null) 
			return false;
		
		return true;
	}
	
	public Boolean delete(Role role) {
		Role existingRole = repository.findOne(role.getId());
		if (existingRole == null) 
			return false;
		
		repository.delete(existingRole);
		Role deletedRole = repository.findOne(role.getId());
		if (deletedRole != null) 
			return false;
		
		return true;
	}
	
	public String list() {
		List<Role> roles = repository.findAll();
		String rolesList = "";
		
		for (Role role : roles) {
			rolesList += "" + role.getId() + ":" + role.getRole();
			rolesList += (roles.indexOf(role) < (roles.size() - 1) ? ";" : ""); 
		}
		
		return rolesList;
	}
}
