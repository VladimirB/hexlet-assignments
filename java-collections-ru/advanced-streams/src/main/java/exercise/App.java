package exercise;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// BEGIN
public class App {

    private static final String FORWARDED_OPTION = "X_FORWARDED_";

    public static String getForwardedVariables(String content) {
        List<String> lines = Stream.of(content.split("\n"))
                .filter(it -> it.startsWith("environment"))
                .map(it -> it.substring(it.indexOf('"') + 1, it.lastIndexOf('"')))
                .toList();

        String[] vars = String.join(",", lines)
                .split(",");

        List<String> forwardedVars =  Stream.of(vars)
                .filter(it -> it.startsWith("X_FORWARDED_"))
                .map(it -> it.replace(FORWARDED_OPTION, ""))
                .toList();

        return String.join(",", forwardedVars);
    }
}
//END
