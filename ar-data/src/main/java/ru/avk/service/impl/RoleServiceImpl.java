package ru.avk.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.avk.domain.Role;
import ru.avk.repository.RoleRepository;
import ru.avk.service.RoleService;
import ru.avk.service.impl.CrudServiceImpl;

/**
 * Created by ipopkov on 02/04/16.
 */
@Service
public class RoleServiceImpl extends CrudServiceImpl<Role, Long> implements RoleService {

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository){
        this.entityRepository = roleRepository;
        this.roleRepository = roleRepository;
    }

    private RoleRepository roleRepository;

    @Override
    public Role findByAuthority(String roleName) {
        return roleRepository.findByAuthority(roleName);
    }
}
