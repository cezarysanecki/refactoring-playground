package pl.cezarysanecki.demo.app.main;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MainServiceTest {

    @Autowired
    MainService mainService;

    @Test
    public void exampleTest() {
        String result = mainService.doSth();

        assertEquals("ServiceOne ServiceTwo", result);
    }

}