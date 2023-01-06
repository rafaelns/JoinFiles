package com.joinfiles.config;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;


public class JoinFilesProperties {

    private Configuration appProps = null;

    private static String WRONG_ENV_VALUE = "env:";

    public JoinFilesProperties() throws ConfigurationException {
        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
                new FileBasedConfigurationBuilder(PropertiesConfiguration.class);

        builder.configure(params.properties().setFileName("application.properties"));

        Configuration config = builder.getConfiguration();
        this.appProps = config;
    }

    public String getPropertyByName(String name) throws ConfigurationException {
        String prop = appProps.getString(name);
        if(prop.contains(WRONG_ENV_VALUE)) {
            throw new ConfigurationException("Erro ao encontrar a property.");
        } else {
            return prop;
        }
    }
}
