package org.crisli.myauth.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.web.server.WebFilter;

@Configuration
@EnableMongoAuditing
public class AppConfig {

    // @Bean
    // public LoggingEventListener mappingEventsListener() {
    // return new LoggingEventListener();
    // }

    private static Logger LOG = LoggerFactory.getLogger(AppConfig.class);

    @Bean
    public WebFilter demoFilter() {
        return (exchange, chain) -> {
            LOG.info("some logging");
            return chain.filter(exchange);
        };
    }
}
