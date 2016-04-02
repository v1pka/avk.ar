package ru.avk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.avk.domain.User;
import ru.avk.repository.UserRepository;

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
    public List<User> findByLastNameStartsWithIgnoreCase(String lastName) {
        return userRepository.findByLastNameStartsWithIgnoreCase(lastName);
    }

    @Override
    public List<User> findByLoginStartsWithIgnoreCase(String login) {
        return userRepository.findByLoginStartsWithIgnoreCase(login);
    }
}
