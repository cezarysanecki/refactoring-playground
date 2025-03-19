package pl.cezarysanecki.demo.app.extra.infrastracture;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.cezarysanecki.demo.app.extra.ExtraService;
import pl.cezarysanecki.demo.utils.helpers.Sth;

import java.util.Random;

@Component
@RequiredArgsConstructor
public class ExternalCaller {

    private final ExtraService extraService;

    public void call() {
        int id = new Random().nextInt();
        extraService.storeSth(id);
    }

}
