package pl.cezarysanecki.demo.app.extra;

import org.junit.jupiter.api.Test;
import pl.cezarysanecki.demo.app.extra.infrastracture.ExternalApi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ExtraServiceTest {

    ExternalApi externalApi = mock(ExternalApi.class);
    ExtraRepository extraRepository = new ExtraRepository.ImMemoryExtraRepository();

    ExtraService extraService = new ExtraConfig()
            .extraService(
                    externalApi,
                    extraRepository
            );

    @Test
    void test() {
        when(externalApi.getSth(12)).thenReturn("sth");

        extraService.storeSth(12);

        String result = extraRepository.findBy(12);
        assertEquals("sth", result);
    }

}