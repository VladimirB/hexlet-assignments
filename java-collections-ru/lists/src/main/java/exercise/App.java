package exercise;

import java.util.ArrayList;

// BEGIN
public class App {

    public static boolean scrabble(String letters, String target) {
        if (letters.length() < target.length()) {
            return false;
        }

        var list = new ArrayList<Character>();
        for (char ch : letters.toCharArray()) {
            list.add(Character.toLowerCase(ch));
        }

        for (char ch : target.toCharArray()) {
            int index = list.indexOf(Character.toLowerCase(ch));
            if (index == -1) {
                return false;
            }

            list.remove(index);
        }

        return true;
    }

    private static boolean contains(String source, char letter) {
        for (char ch : source.toCharArray()) {
            if (Character.toLowerCase(ch) == Character.toLowerCase(letter)) {
                return true;
            }
        }
        return false;
    }
}
//END
