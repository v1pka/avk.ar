package ru.avk.ar.data.service

import org.springframework.beans.factory.annotation.Autowired
import ru.avk.ar.data.BaseSpecification
import ru.avk.domain.Role
import ru.avk.domain.User
import ru.avk.service.RoleService
import ru.avk.service.UserService

/**
 * Created by ipopkov on 02/04/16.
 */
class UserServiceTest extends BaseSpecification {


    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService


    User createUser(String username, String password, String fullname, Role ... roles) {
        def user = new User();
        user.setUsername(username);
        user.setUnencryptedPassword(password);
        user.setFullName(fullname);
        for (Role role : roles) {
            user.addAuthority(role);
        }
        return user;
    }


    def "Проверка получения пользователей"() {
        setup:
        def userName = "username"
        def role = roleService.save(new Role("ADMIN"))
        def user = createUser(userName, "pass", "VeryFulName", role)
        user.setFullName()
        userService.save(user)

        when:
        def usersByUserName = userService.findByUsernameStartsWithIgnoreCase(userName.toUpperCase())
        def userByPartOfFullName = userService.findByFullNameStartsWithIgnoreCase("very")

        then:
        usersByUserName != null
        userByPartOfFullName != null
    }

}
