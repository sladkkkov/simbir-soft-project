package ru.sladkkov.ChatSimbirSoft.service;

import org.springframework.http.ResponseEntity;
import ru.sladkkov.ChatSimbirSoft.domain.Users;

import java.util.List;

public interface UserServiceInterface {

    List<Users> getAll();

    Users findByUserLogin(String name);

    ResponseEntity<Users> getById(Long id);

    void delete(Long id);
}
