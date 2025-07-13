package ru.netology.mapper;


import org.springframework.stereotype.Component;
import ru.netology.DTO.UserDTO;
import ru.netology.entity.User;

@Component
public class MapperUser {
    public UserDTO mapUserToUserDto(User user) {
        return new UserDTO(user.getLogin(), user.getPassword());
    }
}
