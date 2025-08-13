package ru.netology.service;

import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.DTO.FileDTO;
import ru.netology.entity.File;
import ru.netology.entity.Role;
import ru.netology.entity.User;
import ru.netology.exception.FileNotFoundException;
import ru.netology.mapper.MapperFile;
import ru.netology.repository.FileRepository;
import ru.netology.repository.UserRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FileServiceTests {
    @Mock
    FileRepository fileRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    MapperFile mapperFile;
    @InjectMocks
    FileService fileService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private void setUpSecurityContext(String username) {
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);

        // Set up the behavior of the authentication object
        when(authentication.getName()).thenReturn(username);
        // Set up the behavior of the SecurityContext object
        when(securityContext.getAuthentication()).thenReturn(authentication);
        // Set the SecurityContext to return the mocked SecurityContext object
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void testUploadFile() throws IOException {
        setUpSecurityContext("login");
        when(userRepository.findUserByLogin(anyString()))
                .thenReturn(Optional.of(new User(1L, "login", "password", Role.USER, new ArrayList<>())));
        MultipartFile file = new MockMultipartFile("filename","filename".getBytes());

        HttpStatus result = fileService.uploadFile("filename", file);

        assertEquals(HttpStatus.OK, result);
    }

    @Test
    void testDeleteFile() throws FileNotFoundException {
        setUpSecurityContext("login");
        File file = new File();
        when(fileRepository.findFileByFileNameAndUserLogin("fileName", "login")).thenReturn(Optional.of(file));
        HttpStatus result = fileService.deleteFile("fileName");

        assertEquals(HttpStatus.OK, result);
    }

    @Test
    void testEditFileName() throws FileNotFoundException {
        setUpSecurityContext("login");
        File file = new File();
        file.setFileName("fileName");
        when(fileRepository.findFileByFileNameAndUserLogin("fileName", "login"))
                .thenReturn(Optional.of(file));

        HttpStatus result = fileService.editFileName("fileName", "newFileName");

        assertEquals(HttpStatus.OK, result);
    }

    @Test
    void testDownloadFile() throws FileNotFoundException {
        setUpSecurityContext("login");
        File file = new File();
        file.setFileName("fileName");
        byte[] fileBytes = "File for test".getBytes();
        file.setFileByte(fileBytes);
        when(fileRepository.findFileByFileNameAndUserLogin("fileName", "login"))
                .thenReturn(Optional.of(file));

        byte[] result = fileService.downloadFile("fileName");

        assertArrayEquals(fileBytes, result);
    }

    @Test
    void testGetAllFiles() {
        MapperFile noMockMapperFile = new MapperFile();
        setUpSecurityContext("login");
        File file = new File(1L, "fileName", new byte[]{(byte) 0}, "txt", 1L,
                new User());
        FileDTO fileDTO = noMockMapperFile.mapFileToFileDto(file);
        List<FileDTO> files = List.of(fileDTO);

        when(fileRepository.findAllFilesByUserLogin(anyString()))
                .thenReturn(List.of(file));
        when(mapperFile.mapFileToFileDto(file)).thenReturn(fileDTO);

        List<FileDTO> result = fileService.getAllFiles(1);

        assertEquals(files, result);
    }

    @Test
    void testFindFileByFileNameAndLogin() throws FileNotFoundException {
        setUpSecurityContext("login");

        File file = new File();
        file.setFileName("fileName");

        when(fileRepository.findFileByFileNameAndUserLogin("fileName", "login"))
                .thenReturn(Optional.of(file));

        File result = fileService.findFileByFileNameAndLogin("fileName", "login");
        assertEquals("fileName", result.getFileName());
    }

}
