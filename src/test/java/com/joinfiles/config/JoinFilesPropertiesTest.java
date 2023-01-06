package com.joinfiles.config;

import org.apache.commons.configuration2.ex.ConfigurationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.ClearEnvironmentVariable;
import org.junitpioneer.jupiter.SetEnvironmentVariable;


public class JoinFilesPropertiesTest {

    private JoinFilesProperties properties;
    private String DIRECTORY_FILES_READ_VALUE = "";
    private String DIRECTORY_FILES_JOINED_VALUE = "";
    private String FILE_HEADER_VALUE = "";

    @BeforeEach
    public void init() throws ConfigurationException {
        properties = new JoinFilesProperties();
    }

    @Test
    @SetEnvironmentVariable(key = "DIRECTORY_FILES_READ", value = "url_to_read")
    @SetEnvironmentVariable(key = "DIRECTORY_FILES_JOINED", value = "url_result_file")
    @SetEnvironmentVariable(key = "FILE_HEADER", value = "header_pattern")
    public void should_get_properties_values() throws ConfigurationException {
        DIRECTORY_FILES_READ_VALUE = properties.getPropertyByName("directory.read.url");
        DIRECTORY_FILES_JOINED_VALUE = properties.getPropertyByName("directory.joined.url");
        FILE_HEADER_VALUE = properties.getPropertyByName("file.header");

        Assertions.assertEquals("url_to_read", DIRECTORY_FILES_READ_VALUE);
        Assertions.assertEquals("url_result_file", DIRECTORY_FILES_JOINED_VALUE);
        Assertions.assertEquals("header_pattern", FILE_HEADER_VALUE);
    }

    @Test()
    @ClearEnvironmentVariable(key = "DIRECTORY_FILES_READ_VALUE")
    @ClearEnvironmentVariable(key = "DIRECTORY_FILES_JOINED")
    @ClearEnvironmentVariable(key = "FILE_HEADER")
    public void should_return_error_when_not_find_value() throws ConfigurationException {
        Assertions.assertThrows(ConfigurationException.class, () -> properties.getPropertyByName("directory.read.url"));
        Assertions.assertThrows(ConfigurationException.class, () -> properties.getPropertyByName("directory.joined.url"));
        Assertions.assertThrows(ConfigurationException.class, () -> properties.getPropertyByName("file.header"));
    }
}
