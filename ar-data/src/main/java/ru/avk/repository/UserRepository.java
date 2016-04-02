package ru.avk.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import ru.avk.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

	List<User> findByFullNameStartsWithIgnoreCase(String fullName);

	User findByUsernameStartsWithIgnoreCase(String login);
}
