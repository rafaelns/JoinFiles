package br.com.rns.joinfiles.core;

import br.com.rns.joinfiles.config.JoinFilesProperties;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ProcessFiles {

    private Configuration properties;
    private static boolean isFirstFile = true;
    private static int totalFileLines = 0;

    private void init() throws ConfigurationException, URISyntaxException {
        JoinFilesProperties joinFilesProperties = new JoinFilesProperties();
        properties = joinFilesProperties.getAppProps();
    }

    public void process() {

        try {
            init();
            generateFileFromDirectoriesPaths(listFilesOnDirectory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generateFileFromDirectoriesPaths(DirectoryStream<Path> directoriesPaths) throws IOException {
        BufferedWriter writer = getWriter();
        System.out.println("\nArquivos mesclados:");

        for (Path path : directoriesPaths) {

            if (!Files.isDirectory(path)) {
                System.out.println(path);
                BufferedReader reader = getFileReaderForPath(path);
                int headerLineNumber = 0;
                String currentLine;

                while ((currentLine = reader.readLine()) != null) {
                    headerLineNumber++;

                    if (!isFirstFile && headerLineNumber == 1) {
                        continue;
                    }

                    totalFileLines++;
                    writer.write(currentLine + "\n");
                    isFirstFile = false;
                }
                reader.close();
            }
        }

        writer.close();
        System.out.println("\nTotal de linhas no arquivo: " + totalFileLines);
    }

    private DirectoryStream<Path> listFilesOnDirectory() throws IOException {
        Path csvs = Paths.get(properties.getString("directory.read.url"));
        return Files.newDirectoryStream(csvs);
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
