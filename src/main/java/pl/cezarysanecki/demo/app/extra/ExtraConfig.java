package pl.cezarysanecki.demo.app.extra;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.cezarysanecki.demo.app.extra.infrastracture.ExternalApi;

@Configuration
public class ExtraConfig {

    @Bean
    ExtraService extraService(
            ExternalApi externalApi,
            ExtraRepository extraRepository
    ) {
        return new ExtraService(
                externalApi,
                extraRepository
        );
    }

}
