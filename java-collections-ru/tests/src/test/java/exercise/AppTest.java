package exercise;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class AppTest {

    @Test
    void testTake() {
        // BEGIN
        var list = List.of(1, 2, 3, 4);
        var expected = List.of(1, 2);
        var actual = App.take(list, 2);
        Assertions.assertThat(actual)
                .containsExactlyElementsOf(expected);


        // END
    }
}
