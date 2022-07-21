package services;

import constants.FileNames;

import java.io.*;

public class FileService {
    public static BufferedWriter createWriter() {
        File outputFile = createOutputFile();

        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFile));
            return bufferedWriter;
        } catch (IOException e) {
            e.printStackTrace();

            // transformam din checked exception in unchecked
            throw new RuntimeException("Nu s-a putut realiza legatura cu fisierul de output!");
        }
    }

    public static BufferedReader createReader() {
        File inputFile = createInputFile();

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile));
            return bufferedReader;
        } catch (FileNotFoundException e) {
            e.printStackTrace();

            // transformam din checked exception in unchecked
            throw new RuntimeException("Nu s-a putut realiza legatura cu fisierul de input!");
        }
    }

    private static File getFolder() {
        File resourcesFolder = new File(FileNames.RESOURCES_FOLDER);
        if (!resourcesFolder.exists()) {
            resourcesFolder.mkdir();
        }

        return resourcesFolder;
    }

    private static File createInputFile() {
        return createFile(FileNames.INPUT_FILE);
    }

    private static File createOutputFile() {
        return createFile(FileNames.OUTPUT_FILE);
    }

    private static File createFile(String fileName) {
        File resourcesFolder = getFolder();
        File file = new File(resourcesFolder, fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return file;
    }
}
