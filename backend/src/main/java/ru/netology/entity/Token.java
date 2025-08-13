package ru.netology.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder

public class Token {
    @JsonProperty("auth-token")
    private String token;

    public Token(String token) {
        this.token = token;
    }
}
