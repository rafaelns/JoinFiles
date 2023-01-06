package com.joinfiles.core;

import com.joinfiles.config.JoinFilesProperties;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.SetEnvironmentVariable;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ProcessFilesTest {

    private ProcessFiles processFiles;
    private JoinFilesProperties joinFilesProperties;

    @BeforeEach
    public void init() throws ConfigurationException {
        processFiles = new ProcessFiles();
        this.joinFilesProperties = processFiles.getJoinFilesProperties();
    }

    @Test
    @SetEnvironmentVariable(key = "DIRECTORY_FILES_READ", value = "src/test/resources/files/input")
    @SetEnvironmentVariable(key = "DIRECTORY_FILES_JOINED", value = "src/test/resources/files/output/teste.csv")
    @SetEnvironmentVariable(key = "FILE_HEADER", value = "id,first_name,last_name,email,gender,ip_address")
    public void should_generate_file_successfully() throws ConfigurationException, IOException {
        final String header = "id,first_name,last_name,email,gender,ip_address";

        processFiles.generateFile();

        List<Path> paths = getFilesPath(processFiles.getFilesPath());

        BufferedReader reader = processFiles.getFileReaderForPath(
                Paths.get(this.joinFilesProperties.getPropertyByName("directory.joined.url"))
        );

        List<String> fileLines = reader.lines().map(Objects::toString).collect(Collectors.toList());

        Assertions.assertEquals(2, paths.size());
        Assertions.assertEquals(5, fileLines.size());
        Assertions.assertEquals(1, fileLines.stream().filter(s -> s.equals(header)).count());
        Assertions.assertEquals(header, fileLines.get(0));
        Assertions.assertEquals("2,Cristabel,Grayshon,cgrayshon1@mapquest.com,Female,128.32.161.245", fileLines.get(2));

    }

    private List<Path> getFilesPath(DirectoryStream<Path> filesPath) throws IOException, ConfigurationException {
       List<Path> paths = StreamSupport.stream(filesPath.spliterator(), false)
                .collect(Collectors.toList());
        return paths;
    }




}
