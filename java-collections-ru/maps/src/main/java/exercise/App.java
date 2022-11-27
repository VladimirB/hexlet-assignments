package exercise;

import java.util.HashMap;
import java.util.Map;

// BEGIN
public class App {

    public static Map<String, Integer> getWordCount(String sentence) {
        Map<String, Integer> result = new HashMap<>();
        if (sentence.isEmpty()) {
            return result;
        }

        String[] words = sentence.split(" ");
        for (String word : words) {
            if (!result.containsKey(word)) {
                result.put(word, 0);
            }

            result.put(word, result.get(word) + 1);
        }
        return result;
    }

    public static String toString(Map<String, Integer> map) {
        if (map.isEmpty()) {
            return "{}";
        }

        var builder = new StringBuilder();
        builder.append("{");
        for (var entry : map.entrySet()) {
            String line = String.format("\n  %s: %d", entry.getKey(), entry.getValue());
            builder.append(line);
        }
        builder.append("\n}");
        return builder.toString();
    }
}
//END
