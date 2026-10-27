package pl.cezarysanecki.demo.app.support;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(
        classes = {
                TestConfig.class
        }
)
class SupportServiceTest {

    @Autowired
    SupportService supportService;

    @Test
    void exampleTest() {
        String name = supportService.getName();

        assertEquals("Dominik", name);
    }

}

@Import(SupportConfig.class)
@Configuration
class TestConfig {

    @Bean
    ExternalClient externalClient() {
        return () -> "{\"name\":\"Dominik\"}";
    }

}