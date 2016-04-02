package ru.avk.service;

import ru.avk.domain.User;

import java.util.List;

/**
 * Created by ipopkov on 02/04/16.
 */
public interface UserService extends CrudService<User, Long> {
    List<User> findByFullNameStartsWithIgnoreCase(String fullName);
    User findByUsernameStartsWithIgnoreCase(String login);
}
