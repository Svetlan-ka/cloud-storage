package ru.netology.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.DTO.FileDTO;
import ru.netology.exception.FileNotFoundException;
import ru.netology.service.FileService;

import java.io.IOException;
import java.util.List;

@Log4j2
@RestController
@AllArgsConstructor
public class FileController {
    private final FileService service;

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @PostMapping(path = "/file", consumes = "multipart/form-data") //работает
    public HttpStatus uploadFile(@RequestParam("fileName") String fileName,
                                 MultipartFile file) throws IOException {
        return service.uploadFile(fileName, file);

    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @DeleteMapping("/file") //работает
    public HttpStatus deleteFile(@RequestParam("fileName") String fileName) throws FileNotFoundException {
        return service.deleteFile(fileName);
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @PutMapping("/file") //работает
    public HttpStatus editFileName(@RequestParam("fileName") String fileName,
                               @RequestParam("newFileName") String newFileName) throws FileNotFoundException {
        return service.editFileName(fileName, newFileName);
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping("/file") //работает
    public byte[]  downloadFile(@RequestParam("fileName") String fileName) throws FileNotFoundException {
        return service.downloadFile(fileName);
    }


  //  @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping("/list") //работает
    public List<FileDTO> getAllFiles(@RequestParam("limit") int limit) {
        return service.getAllFiles(limit);
    }
}
