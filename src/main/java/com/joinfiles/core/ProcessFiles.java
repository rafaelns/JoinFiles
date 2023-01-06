package com.joinfiles.core;

import com.joinfiles.config.JoinFilesProperties;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ProcessFiles {

    private  JoinFilesProperties joinFilesProperties;
    private static boolean isFirstFile = true;
    private static int totalFileLines = 0;

    public ProcessFiles (){
        init();
    }

    private void init() {
        try {
            joinFilesProperties = new JoinFilesProperties();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generateFile() {

        try (BufferedWriter writer = getWriter()) {
            System.out.println("\nArquivos mesclados:");
            int fileLineCount = 0;

            for (Path path : getFilesPath()) {
                try (BufferedReader reader = getFileReaderForPath(path)) {
                    String currentLine;

                    while ((currentLine = reader.readLine()) != null) {
                        fileLineCount++;
                        if (isFirstFile && fileLineCount == 1) {
                            writer.write(joinFilesProperties.getPropertyByName("file.header") + "\n");
                            currentLine = reader.readLine();
                            fileLineCount++;
                        }
                        if (!isFirstFile && fileLineCount == 1) {
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

            System.out.println("\nArquivo foi gerado com sucesso em: " + joinFilesProperties.getPropertyByName("directory.joined.url"));
            System.out.println("Total de linhas no arquivo: " + totalFileLines);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public DirectoryStream<Path> getFilesPath() throws IOException, ConfigurationException {
        Path csvs = Paths.get(joinFilesProperties.getPropertyByName("directory.read.url"));
        DirectoryStream.Filter<Path> filter = entry -> !Files.isDirectory(entry);
        DirectoryStream<Path> filesPath = Files.newDirectoryStream(csvs, filter);

        return filesPath;
    }

    public BufferedWriter getWriter() throws ConfigurationException, IOException {
        Path result = Paths.get(joinFilesProperties.getPropertyByName("directory.joined.url"));
        FileWriter fileToWrite = new FileWriter(result.toFile());
        return new BufferedWriter(fileToWrite);

    }

    public BufferedReader getFileReaderForPath(Path path) throws FileNotFoundException {
        FileReader fileToRead = new FileReader(path.toFile());
        return new BufferedReader(fileToRead);
    }


    public JoinFilesProperties getJoinFilesProperties() {
        return this.joinFilesProperties;
    }
}
