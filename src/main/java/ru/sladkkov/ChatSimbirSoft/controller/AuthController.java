package ru.sladkkov.ChatSimbirSoft.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sladkkov.ChatSimbirSoft.domain.Users;
import ru.sladkkov.ChatSimbirSoft.dto.request.AuthenticationRequestDto;
import ru.sladkkov.ChatSimbirSoft.exception.NotAuthenticationException;
import ru.sladkkov.ChatSimbirSoft.repository.UserRepo;
import ru.sladkkov.ChatSimbirSoft.security.JwtTokenProvider;
import ru.sladkkov.ChatSimbirSoft.service.AuthService;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/chat")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepo userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    private final AuthService authService;

    public AuthController(AuthenticationManager authenticationManager, UserRepo userRepository, JwtTokenProvider jwtTokenProvider, AuthService authService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequestDto request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword()));
            Users user = userRepository.findByUserLogin(request.getLogin()).orElseThrow(() -> new UsernameNotFoundException("???????????????????????? ???? ??????????????"));
            String token = jwtTokenProvider.createToken(request.getLogin(), user.getRole().name());
            Map<Object, Object> response = new HashMap<>();
            response.put("login", request.getLogin());
            response.put("token", token);
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>("???????????????? ????????????, ?????????????????? ???????????????????????????? ???????????????????????????????? ??????", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity logout(HttpServletRequest request, HttpServletResponse response) throws LoginException, NotAuthenticationException {
        authService.logout(request,response);
        return ResponseEntity.ok("???? ?????????????? ?????????? ???? ????????????????");
    }
}