package ru.netology.service;

import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.DTO.FileDTO;
import ru.netology.entity.File;
import ru.netology.entity.User;
import ru.netology.exception.FileNotFoundException;
import ru.netology.exception.InvalidDataException;
import ru.netology.mapper.MapperFile;
import ru.netology.repository.FileRepository;
import ru.netology.repository.UserRepository;


import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Data
@Service
public class FileService {
    private final FileRepository fileRepository;
    private final UserRepository userRepository;
    private final MapperFile mapperFile;


    public HttpStatus uploadFile(String fileName, MultipartFile file) throws IOException {

        String login = getUserLogin();
        Optional<User> optionalUser = userRepository.findUserByLogin(login);
        User user = optionalUser.get();

        if (fileRepository.findFileByFileNameAndUserLogin(fileName, login).isPresent())
            throw new InvalidDataException("File with this name already exists");

        File uploadFile = new File();
        uploadFile.setSize(file.getSize());
        uploadFile.setUser(user);
        uploadFile.setFileName(fileName);
        uploadFile.setType(file.getContentType());
        uploadFile.setFileByte(file.getBytes());

        fileRepository.saveAndFlush(uploadFile);
        log.info("File {} was uploaded", fileName);
        return HttpStatus.OK;
    }


    @Transactional
    public HttpStatus deleteFile(String fileName) throws FileNotFoundException {
        String login = getUserLogin();
        File file = findFileByFileNameAndLogin(fileName, login);

        if (file == null) {
            throw new FileNotFoundException("File not found");
        }
        fileRepository.delete(file);
        log.info("File {} was deleted", fileName);
        return HttpStatus.OK;
    }


    @Transactional
    public HttpStatus editFileName(String fileName, String newFileName) throws FileNotFoundException {
        String login = getUserLogin();
        File file = findFileByFileNameAndLogin(fileName, login);
        file.setFileName(newFileName);
        fileRepository.saveAndFlush(file);
        log.info("File {} was renamed to {}", fileName, newFileName);
        return HttpStatus.OK;
    }


    public byte[] downloadFile(String fileName) throws FileNotFoundException {
        String login = getUserLogin();
        File file = findFileByFileNameAndLogin(fileName, login);

        if (file == null) {
            throw new FileNotFoundException("File not found");
        }
        log.info("File {} was downloaded", fileName);
        return file.getFileByte();
    }


    public List<FileDTO> getAllFiles(int limit) {
        String login = getUserLogin();
        return fileRepository.findAllFilesByUserLogin(login)
                .stream().map(mapperFile::mapFileToFileDto)
                .limit(limit)
                .collect(Collectors.toList());
    }

    public String getUserLogin() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public File findFileByFileNameAndLogin(String fileName, String login) throws FileNotFoundException {
        Optional<File> optionalFile = fileRepository.findFileByFileNameAndUserLogin(fileName, login);

        if (optionalFile.isEmpty())
            throw new FileNotFoundException("File not found");

        return optionalFile.get();
    }
}
