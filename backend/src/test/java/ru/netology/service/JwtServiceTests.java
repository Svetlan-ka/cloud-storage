package ru.netology.service;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.netology.entity.Role;
import ru.netology.entity.User;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class JwtServiceTests {

    @Test
    public void testGenerateToken_tokenNotEmpty() {
        JwtService jwtService = new JwtService();
        User user = new User(1L, "testUser1", "testPassword1", Role.USER, null);

        String result = jwtService.generateToken(user);

        assertNotNull(result);
    }
}
