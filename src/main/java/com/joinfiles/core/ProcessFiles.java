package com.joinfiles.core;

import com.joinfiles.config.JoinFilesProperties;
import org.apache.commons.configuration2.Configuration;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

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

        try (BufferedWriter writer = getWriter()) {
            System.out.println("\nArquivos mesclados:");
            int fileLineCount = 0;

            for (Path path : getFilesPath()) {
                try (BufferedReader reader = getFileReaderForPath(path)) {
                    String currentLine;

                    while ((currentLine = reader.readLine()) != null) {
                        fileLineCount++;
                        if (isFirstFile && fileLineCount == 1) {
                            writer.write(properties.getString("file.header") + "\n");
                            currentLine = reader.readLine();
                            fileLineCount++;
                        }
                        if (!isFirstFile && fileLineCount == 1) {
                            //writer.write("");
                            currentLine = reader.readLine();
                        }

                        writer.write(currentLine + "\n");
                    }

                    isFirstFile = false;
                    totalFileLines += fileLineCount;
                    System.out.println(String.format("Copiadas %d linhas do arquivo: %s ", fileLineCount, path));
                    fileLineCount = 0;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("\nArquivo foi gerado com sucesso em: " + properties.getString("directory.joined.url"));
            System.out.println("Total de linhas no arquivo: " + totalFileLines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isFirstFile(List<Path> filesPath, Path path) {
        return filesPath.indexOf(path) == 0;
    }

    private DirectoryStream<Path> getFilesPath() throws IOException {
        Path csvs = Paths.get(properties.getString("directory.read.url"));
        DirectoryStream.Filter<Path> filter = entry -> !Files.isDirectory(entry);
        DirectoryStream<Path> filesPath = Files.newDirectoryStream(csvs, filter);

        return filesPath;
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
