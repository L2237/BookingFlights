package services;

import java.io.BufferedReader;
import java.io.IOException;

public class FileReaderService {
    private final BufferedReader bufferedReader;

    public FileReaderService() {
        bufferedReader = FileService.createReader();
    }

    public String readLine() {
        try {
            return bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();

            throw new RuntimeException("Nu s-a putut face citirea!");
        }
    }
}
