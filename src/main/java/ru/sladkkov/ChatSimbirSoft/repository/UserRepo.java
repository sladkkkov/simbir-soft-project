package ru.sladkkov.ChatSimbirSoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sladkkov.ChatSimbirSoft.domain.Users;

import java.util.Optional;

public interface UserRepo extends JpaRepository<Users, Long> {
   Optional<Users> findByUserLogin(String name);
}
