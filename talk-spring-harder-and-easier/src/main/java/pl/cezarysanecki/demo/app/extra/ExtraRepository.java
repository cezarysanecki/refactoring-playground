package pl.cezarysanecki.demo.app.extra;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public interface ExtraRepository {

    void save(int id, String data);

    String findBy(int id);

    @Component
    class ImMemoryExtraRepository implements ExtraRepository {

        private static final Map<Integer, String> DATABASE = new ConcurrentHashMap<>();

        @Override
        public void save(int id, String data) {
            DATABASE.put(id, data);
        }

        @Override
        public String findBy(int id) {
            String value = DATABASE.get(id);
            if (value == null) {
                throw new IllegalArgumentException("not found for id: " + id);
            }
            return value;
        }

    }

}
