package pl.cezarysanecki.demo.app.support.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import pl.cezarysanecki.demo.app.support.ExternalClient;

@Primary
@Component
public class ClientAdapter implements ExternalClient {

    private final FakeClient fakeClient;
    private final VerySlowClient verySlowClient;
    private final boolean useFake;

    public ClientAdapter(
            FakeClient fakeClient,
            VerySlowClient verySlowClient,
            @Value("${fake:false}") boolean useFake
    ) {
        this.fakeClient = fakeClient;
        this.verySlowClient = verySlowClient;
        this.useFake = useFake;
    }

    @Override
    public String json() {
        if (useFake) {
            return fakeClient.json();
        }
        return verySlowClient.json();
    }

}
