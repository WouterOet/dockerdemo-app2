package app;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.jms.connection.CachingConnectionFactory;

import javax.annotation.PostConstruct;
import javax.jms.ConnectionFactory;

@Configuration
public class Configurator {
    private static final Logger LOGGER = LoggerFactory.getLogger(Configurator.class);

    @PostConstruct
    public void init() {
        LOGGER.info("Initialized Configurator");
    }

    @Profile("dev")
    @Configuration
    @PropertySources({
            //De properties file famed-integration.properties is geplaatst in het common component omdat deze ook door de clients worden gebruikt
            @PropertySource(name = "appProperties", value = "classpath:/config/default.properties"),
            @PropertySource(name = "appProperties", value = "file://${user.home}/quintor/app2.properties",
                    ignoreResourceNotFound = true)
    })
    public static class DevProperties {
        @PostConstruct
        public void init() {
            LOGGER.info("Initialized devProperties");
        }
    }

    @Profile("docker")
    @Configuration
    @PropertySource(name = "appProperties", value = "file:/etc/quintor-docker/app2.properties")
    public static class DockerProperties {
        @PostConstruct
        public void init() {
            LOGGER.info("Initialized dockerProperties");
        }
    }

    @Bean()
    public BerichtOntvanger berichtOntvanger() {
        return new BerichtOntvanger();
    }

}
