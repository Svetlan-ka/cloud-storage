package ru.netology.exception;


import java.io.IOException;

public class FileNotFoundException extends IOException {

    public FileNotFoundException(String msg) {
        super(msg);
    }


}
