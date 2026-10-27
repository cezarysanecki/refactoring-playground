package pl.cezarysanecki.demo.app.extra;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExtraServiceTest {

    ExtraRepository extraRepository = new ExtraRepository.ImMemoryExtraRepository();

    ExtraService extraService = new ExtraConfig()
            .extraService(
                    extraRepository
            );

    @Test
    void test() {
        extraService.storeSth(12, "sth");

        String result = extraRepository.findBy(12);
        assertEquals("sth", result);
    }

}