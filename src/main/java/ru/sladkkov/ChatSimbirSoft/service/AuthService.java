package ru.sladkkov.ChatSimbirSoft.service;


import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import ru.sladkkov.ChatSimbirSoft.domain.Users;
import ru.sladkkov.ChatSimbirSoft.dto.request.AuthenticationRequestDto;
import ru.sladkkov.ChatSimbirSoft.exception.JwtAuthenticationException;
import ru.sladkkov.ChatSimbirSoft.repository.UserRepo;
import ru.sladkkov.ChatSimbirSoft.security.JwtTokenProvider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
@Log4j2
public class AuthService {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserRepo userRepo;

    public AuthService(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserRepo userRepo) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepo = userRepo;
    }

    public Map<Object, Object> authinficate(AuthenticationRequestDto authenticationRequestDto)throws JwtAuthenticationException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequestDto.getLogin(), authenticationRequestDto.getPassword()));
        Users user = userRepo.findByUserLogin(authenticationRequestDto.getLogin()).orElseThrow(() -> new UsernameNotFoundException("Пользователя не найдено"));
        String token = jwtTokenProvider.createToken(authenticationRequestDto.getLogin(), user.getRole().name());
        Map<Object, Object> response = new HashMap<>();
        response.put("login", authenticationRequestDto.getLogin());
        response.put("token", token);
        return  response;
    }
    public void logout(HttpServletRequest request, HttpServletResponse response) throws JwtAuthenticationException{
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }
}
