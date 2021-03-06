package ru.avk.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.avk.domain.User;
import ru.avk.repository.UserRepository;
import ru.avk.service.UserService;
import ru.avk.service.impl.CrudServiceImpl;

import java.util.List;

/**
 * Created by ipopkov on 02/04/16.
 */
@Service
public class UserServiceImpl extends CrudServiceImpl<User, Long> implements UserService {

    @Autowired
    public UserServiceImpl(UserRepository userRepository){
        this.entityRepository = userRepository;
        this.userRepository = userRepository;
    }

    private UserRepository userRepository;

    @Override
    public List<User> findByFullNameStartsWithIgnoreCase(String fullName) {
        return userRepository.findByFullNameStartsWithIgnoreCase(fullName);
    }

    @Override
    public User findByUsernameStartsWithIgnoreCase(String login) {
        return userRepository.findByUsernameStartsWithIgnoreCase(login);
    }
}
