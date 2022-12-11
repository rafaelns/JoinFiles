package br.com.rns.joinfiles.config;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.net.URISyntaxException;

public class JoinFilesProperties {

    private Configuration appProps = null;
    public JoinFilesProperties() throws ConfigurationException, URISyntaxException {
        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
                new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class);

        builder.configure(params.properties().setThrowExceptionOnMissing(false)
                .setFileName("application.properties"));

        Configuration config = builder.getConfiguration();
        this.appProps = config;
    }

    public Configuration getAppProps() {
        return appProps;
    }
}
