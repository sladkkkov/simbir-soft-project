package ru.sladkkov.ChatSimbirSoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sladkkov.ChatSimbirSoft.domain.Roles;

public interface RolesRepo extends JpaRepository<Roles, String> {
}
