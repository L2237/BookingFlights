package services;

import java.io.BufferedWriter;
import java.io.IOException;

public class FileWriterService {
    private final BufferedWriter bufferedWriter;

    public FileWriterService() {
        this.bufferedWriter = FileService.createWriter();
    }

    public void write(String str) {
        try {
            bufferedWriter.write(str);
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void flush() {
        try {
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}