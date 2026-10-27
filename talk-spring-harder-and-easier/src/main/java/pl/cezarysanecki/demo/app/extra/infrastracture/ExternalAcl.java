package pl.cezarysanecki.demo.app.extra.infrastracture;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.cezarysanecki.demo.app.extra.ExtraService;

import java.util.Random;

@Component
@RequiredArgsConstructor
public class ExternalAcl {

    private final ExternalApi externalApi;
    private final ExtraService extraService;

    public void call() {
        int id = new Random().nextInt();
        String sth = externalApi.getSth(id);
        extraService.storeSth(id, sth);
    }

}
