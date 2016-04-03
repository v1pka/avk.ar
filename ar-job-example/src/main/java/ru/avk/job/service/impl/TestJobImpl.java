package ru.avk.job.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.avk.base.job.service.ScheduledTask;
import ru.avk.domain.User;
import ru.avk.service.UserService;
import ru.avk.service.impl.Application;

import java.util.UUID;

/**
 * Created by ipopkov on 03/04/16.
 */
@Service
public class TestJobImpl implements ScheduledTask {

    private static final Logger log = LoggerFactory.getLogger(TestJobImpl.class);

    @Autowired
    private UserService userService;

    @Override
    public void run() {
        User user = new User();
        user.setFullName(UUID.randomUUID().toString());
        user.setUsername(UUID.randomUUID().toString());
        userService.save(user);
        log.info("NEW SIZE OF USERS = " + userService.findAll().size());
    }
}
