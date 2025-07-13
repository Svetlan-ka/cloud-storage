package ru.netology.service;

import org.junit.jupiter.api.Test;

public class FileServiceTests {

    @Test
    void shouldDeleteFile() {
        FileService fileService = new FileService();
        fileService.deleteFile(1);
        assertEquals(1, fileService.getFiles().size());
}
