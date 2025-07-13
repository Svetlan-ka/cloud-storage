package ru.netology.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.hibernate.annotations.Formula;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.netology.DTO.UserDTO;
import ru.netology.entity.Token;
import ru.netology.entity.Role;
import ru.netology.entity.User;
import ru.netology.exception.BadCredentialsException;
import ru.netology.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Log4j2
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public Token register(UserDTO request) {
        var user = User.builder()
                .login(request.getLogin())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        log.info("User {} registered", user.getLogin());
        return Token.builder()
                .token(jwtToken)
                .build();
    }

    public Token authenticate(UserDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getLogin(),
                        request.getPassword()
                )
        );
        var user = repository.findUserByLogin(request.getLogin());
        if (user.isEmpty()) {
            log.error("User not found");
            throw new BadCredentialsException("User not found");
        }
        var jwtToken = jwtService.generateToken(user.get());
        log.info("User {} authenticated", user.get().getLogin());
        return Token.builder()
                .token(jwtToken)
                .build();
    }

    @Formula("(select u.id from user u where u.login = login)")
    public void logout(String jwtToken) {
        final String BEARER_PREFIX = "Bearer ";

        if (jwtToken.startsWith(BEARER_PREFIX)) {
            final String token = jwtToken.substring(BEARER_PREFIX.length());
            jwtService.addTokenInBlackList(token);
            log.info("User {} logout", jwtService.extractUserLogin(token));
        }
    }

}

