package exercise;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Map.Entry;

// BEGIN
public class App {

    public static List<Map<String, String>> findWhere(List<Map<String, String>> items, Map<String, String> query) {
        List<Map<String, String>> result = new ArrayList<>();
        for (var item : items) {
//            book = Map.of("title", "Cymbeline", "author", "Shakespeare", "year", "1611")
//            query = Map.of("author", "Shakespeare", "year", "1611")
            boolean found = true;
            for (var queryEntry : query.entrySet()) {
                if (!item.get(queryEntry.getKey()).equals(queryEntry.getValue())) {
                    found = false;
                }
            }
            if (found) {
                result.add(item);
            }
        }
        return result;
    }
}
//END
