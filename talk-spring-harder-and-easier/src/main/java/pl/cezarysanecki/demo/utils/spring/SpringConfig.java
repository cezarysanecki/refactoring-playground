package pl.cezarysanecki.demo.utils.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

}
