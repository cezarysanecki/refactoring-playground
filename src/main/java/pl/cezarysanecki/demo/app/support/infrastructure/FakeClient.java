package pl.cezarysanecki.demo.app.support.infrastructure;

import org.springframework.stereotype.Component;
import pl.cezarysanecki.demo.app.support.ExternalClient;

@Component
public class FakeClient implements ExternalClient {

    @Override
    public String json() {
        return "{\"name\":\"Cezary\"}";
    }

}
