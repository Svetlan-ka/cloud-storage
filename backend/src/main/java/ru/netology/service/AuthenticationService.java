package ru.netology.service;

import org.hibernate.annotations.Formula;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.netology.DTO.UserDTO;
import ru.netology.exception.BadCredentialsException;
import ru.netology.exception.UserNotFoundException;
import ru.netology.repository.UserRepository;
import ru.netology.entity.Token;

@Service
public class AuthenticationService {
    private final UserRepository repository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository repository, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }


    public Token authenticate(UserDTO userDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDTO.getLogin(),
                        userDTO.getPassword()
                )
        );
        var user = repository.findUserByLogin(userDTO.getLogin());
        if (user.isPresent()) {
            var jwtToken = jwtService.generateToken(user.get());
            return Token.builder()
                    .token(jwtToken)
                    .build();
        }
        throw new UserNotFoundException("User not found");
    }

    @Formula("(select u.id from user u where u.login = login)")
    public void logout(String jwtToken) {
        final String BEARER_PREFIX = "Bearer ";

        if (jwtToken.startsWith(BEARER_PREFIX)) {
            final String token = jwtToken.substring(BEARER_PREFIX.length());
            jwtService.addTokenInBlackList(token);
        }
        throw new BadCredentialsException("Invalid token");
    }

    /*
    public Token register(@NonNull UserDTO request) {
        var user = mapperUser.mapUserDtoToUser(request);
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        Token token = Token.builder()
                .token(jwtToken)
                .build();
        return token;
    }
     */

}

