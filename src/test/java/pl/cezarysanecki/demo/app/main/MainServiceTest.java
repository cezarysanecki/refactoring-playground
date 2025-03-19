package pl.cezarysanecki.demo.app.main;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import pl.cezarysanecki.demo.app._1.ServiceOne;
import pl.cezarysanecki.demo.app._2.ServiceTwo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = TestConfig.class)
class MainServiceTest {

    @Autowired
    MainService mainService;

    @MockitoBean
    ServiceOne serviceOne;
    @MockitoBean
    ServiceTwo serviceTwo;

    @Test
    public void exampleTest() {
        when(serviceOne.doSth()).thenReturn("ServiceOne");
        when(serviceTwo.doSth()).thenReturn("ServiceTwo");

        String result = mainService.doSth();

        assertEquals("ServiceOne ServiceTwo", result);
    }

}

@Configuration
@ComponentScan("pl.cezarysanecki.demo.app.main")
class TestConfig {

}