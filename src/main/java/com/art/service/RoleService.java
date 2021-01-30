package com.art.service;

import com.art.entity.Role;
import com.art.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private RoleRepository roleRepository;

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findRoleById(Long id) {
        return roleRepository.findRoleById(id);
    }

    public void update(Role role) {
        roleRepository.save(role);
    }
}
