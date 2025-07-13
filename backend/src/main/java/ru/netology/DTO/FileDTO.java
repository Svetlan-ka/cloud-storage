package ru.netology.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FileDTO {
    @JsonProperty("fileName")
    public String name;
    @JsonProperty("fileSize")
    public Long size;
}
