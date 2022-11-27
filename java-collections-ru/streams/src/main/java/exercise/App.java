package exercise;

import java.util.List;
import java.util.Arrays;

// BEGIN
public class App {

    private static final List<String> FREE_DOMAINS = List.of(
            "gmail.com",
            "yandex.ru",
            "hotmail.com"
    );

    public static long getCountOfFreeEmails(List<String> emails) {
        return emails.stream()
                .filter(email -> isFreeEmail(email))
                .count();
    }

    private static boolean isFreeEmail(String email) {
        String domain = email.split("@")[1];
        return FREE_DOMAINS.contains(domain);
    }
}
// END
