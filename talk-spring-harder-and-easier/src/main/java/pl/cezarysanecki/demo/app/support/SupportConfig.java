package pl.cezarysanecki.demo.app.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SupportConfig {

    @Bean
    SupportService supportService(ExternalClient externalClient) {
        return new SupportService(externalClient, new ObjectMapper());
    }

}
