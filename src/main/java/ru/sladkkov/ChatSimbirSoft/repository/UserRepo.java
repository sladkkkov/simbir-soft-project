package ru.sladkkov.ChatSimbirSoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import ru.sladkkov.ChatSimbirSoft.domain.Users;

public interface UserRepo extends JpaRepository<Users, Long> {
    Users findByUserLogin(@Param("user_login") String login);
}
