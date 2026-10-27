package pl.cezarysanecki.demo.app.extra;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExtraConfig {

    @Bean
    ExtraService extraService(
            ExtraRepository extraRepository
    ) {
        return new ExtraService(
                extraRepository
        );
    }

}
