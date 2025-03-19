package pl.cezarysanecki.demo.app.extra;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.cezarysanecki.demo.app.extra.infrastracture.ExternalApi;

@Component
@RequiredArgsConstructor
public class ExtraService {

    private final ExternalApi externalApi;
    private final ExtraRepository extraRepository;

    public void storeSth(int id) {
        String sth = externalApi.getSth(id);
        extraRepository.save(id, sth);
    }

}
