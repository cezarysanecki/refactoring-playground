package pl.cezarysanecki.demo.app.extra;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExtraService {

    private final ExtraRepository extraRepository;

    public void storeSth(int id, String sth) {
        extraRepository.save(id, sth);
    }

}
