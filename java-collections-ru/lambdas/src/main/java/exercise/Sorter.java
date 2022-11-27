package exercise;

import java.util.Comparator;
import java.util.Map;
import java.util.List;
import java.time.LocalDate;
import java.util.Objects;
import java.util.stream.Collectors;

// BEGIN
public class Sorter {

    public static List<String> takeOldestMans(List<Map<String, String>> items) {
        return items.stream()
                .filter(it -> it.get("gender").equals("male"))
                .sorted(Comparator.comparing(man -> man.get("birthday")))
                .map(it -> it.get("name"))
                .toList();
    }
}
// END
