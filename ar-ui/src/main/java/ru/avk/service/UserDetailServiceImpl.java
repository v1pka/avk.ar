package ru.avk.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.avk.domain.User;

import java.util.Objects;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private Logger LOG = LoggerFactory.getLogger(UserDetailServiceImpl.class);

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Objects.requireNonNull(username);

        User user = userService.findByUsernameStartsWithIgnoreCase(username);

        if (user == null) {
            throw new UsernameNotFoundException("User " + username + " is unknown");
        }

        LOG.debug("Loaded user {}", user);

        return user;
    }
}
