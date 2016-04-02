package ru.avk.service;

import ru.avk.domain.Role;
import ru.avk.domain.User;

import java.util.List;

/**
 * Created by ipopkov on 02/04/16.
 */
public interface RoleService extends CrudService<Role, Long> {
    Role findByAuthority(String roleName);
}
