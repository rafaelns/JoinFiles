package com.joinfiles.config;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;


public class JoinFilesProperties {

    private Configuration appProps = null;
    public JoinFilesProperties() throws ConfigurationException {
        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
                new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class);

        builder.configure(params.properties().setFileName("application.properties"));

        Configuration config = builder.getConfiguration();
        this.appProps = config;
    }

    public Configuration getAppProps() {
        return appProps;
    }
}
