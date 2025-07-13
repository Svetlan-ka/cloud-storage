package ru.netology.mapper;

import org.springframework.stereotype.Component;
import ru.netology.DTO.FileDTO;
import ru.netology.entity.File;

@Component
public class MapperFile {

    public FileDTO mapFileToFileDto(File file) {
        return new FileDTO(file.getFileName(), file.getSize());
    }
}
