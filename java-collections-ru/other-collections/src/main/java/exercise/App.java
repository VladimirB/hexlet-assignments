package exercise;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// BEGIN
public class App {

    public static Map<String, String> genDiff(Map<String, Object> file1, Map<String, Object> file2) {
        Set<Map.Entry<String, Object>> entries1 = file1.entrySet();
        Set<Map.Entry<String, Object>> entries2 = file2.entrySet();

        var result = Stream.concat(entries1.stream(), entries2.stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> String.valueOf(entry.getValue()),
                        App::mergeValues,
                        TreeMap::new)
                );

        var allowedStates = List.of("added", "deleted", "unchanged", "changed");
        for (var entry : result.entrySet()) {
            if (!allowedStates.contains(entry.getValue())) {
                var key = entry.getKey();
                var merged = mergeValues(file1.get(key), file2.get(key));
                result.put(key, merged);
            }
        }

        return result;
    }

    private static String mergeValues(Object v1, Object v2) {
        if (Objects.nonNull(v1) && Objects.nonNull(v2)) {
            return v1.equals(v2) ? "unchanged" : "changed";
        }

        if (Objects.isNull(v1) && Objects.nonNull(v2)) {
            return "added";
        }

        if (Objects.nonNull(v1)) {
            return "deleted";
        }

        return "unknown";
    }
}
//END
