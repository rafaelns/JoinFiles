package com.joinfiles.core;

import com.joinfiles.config.JoinFilesProperties;
import org.apache.commons.configuration2.Configuration;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ProcessFiles {

    private Configuration properties;
    private static boolean isFirstFile = true;
    private static int totalFileLines = 0;

    private void init() {
        try {
            JoinFilesProperties joinFilesProperties = new JoinFilesProperties();
            properties = joinFilesProperties.getAppProps();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void process() {
        init();
        generateFileFromDirectoriesPaths();
    }

    private void generateFileFromDirectoriesPaths() {

        try(BufferedWriter writer = getWriter()) {
            System.out.println("\nArquivos mesclados:");

            listFilesOnDirectory().forEach(path -> {
                System.out.println(path);

                try(BufferedReader reader = getFileReaderForPath(path)) {
                    int headerLineNumber = 0;
                    String currentLine;
                    while ((currentLine = reader.readLine()) != null) {
                        headerLineNumber++;
                        if (!isFirstFile && headerLineNumber == 1) { continue; }
                        totalFileLines++;
                        writer.write(currentLine + "\n");
                        isFirstFile = false;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            System.out.println("\nTotal de linhas no arquivo: " + totalFileLines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private DirectoryStream<Path> listFilesOnDirectory() throws IOException {
        Path csvs = Paths.get(properties.getString("directory.read.url"));
        DirectoryStream.Filter<Path> filter = entry -> !Files.isDirectory(entry);
        return Files.newDirectoryStream(csvs, filter);
    }

    private BufferedWriter getWriter() throws IOException {
        Path result = Paths.get(properties.getString("directory.joined.url"));
        FileWriter fileToWrite = new FileWriter(result.toFile());
        return new BufferedWriter(fileToWrite);
    }

    private BufferedReader getFileReaderForPath(Path path) throws FileNotFoundException {
        FileReader fileToRead = new FileReader(path.toFile());
        return new BufferedReader(fileToRead);
    }
}
