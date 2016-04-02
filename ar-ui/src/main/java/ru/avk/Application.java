package ru.avk;

import com.google.common.eventbus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.avk.domain.Role;
import ru.avk.domain.User;
import ru.avk.repository.RoleRepository;
import ru.avk.service.RoleService;
import ru.avk.service.UserService;

@Import(BaseDataConfig.class)
@Configuration
@EnableAutoConfiguration
public class Application {

    @Bean
    public EventBus eventBus (){
        return new EventBus("main");
    }


    private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}



	@Bean
	InitializingBean initialize(UserService userService, RoleService roleService){
		return () -> {
            Role adminRole;
            Role userRole;
            if (roleService.findAll().size() == 0) {
                adminRole = new Role("ROLE_ADMIN");
                roleService.save(adminRole);
                userRole = new Role("ROLE_USER");
                roleService.save(userRole);
            } else {
                adminRole = roleService.findByAuthority("ADMIN");
                userRole = roleService.findByAuthority("USER");
            }
            if (userService.findAll().size() == 0) {
                userService.save(createUser("admin", "admin", "Administrator", adminRole, userRole));
                userService.save(createUser("user", "user", "John Doe", userRole));
            }
		};
	}


    private User createUser(String username, String password, String fullname, Role ... roles) {
        User user = new User();
        user.setUsername(username);
        user.setUnencryptedPassword(password);
        user.setFullName(fullname);
        for (Role role : roles) {
            user.addAuthority(role);
        }
        return user;
    }
}
