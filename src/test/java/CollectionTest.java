import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


public class CollectionTest {

    @Test
    public void java9CollectionTest() {

        // java 8 unmodifiableSet
        Set<String> beforeSet = Collections.unmodifiableSet(new HashSet<>(Arrays.asList("a", "b", "c")));

        List<String> list = List.of("one", "two", "three");
        Set<String> set = Set.of("one", "two", "three");
        Map<String, String> map = Map.of("foo", "one", "bar", "two");

        assertThrows(
                UnsupportedOperationException.class,
                () -> list.add("four"),
                "Expected list.add() to throw"
        );
    }

    @Test
    public void iterateTest() {
        // before - big problem
        /*
        IntStream
                .iterate(1, i -> i * 2)
                .filter(i -> i < 20)
                .forEach(System.out::println);
        */

        // after
        IntStream
                .iterate(1, i -> i < 20, i -> i * 2)
                .forEach(System.out::println);
    }

    @Test
    public void whileTest() {
        long count = Stream.of(1, 2, 3, 4, 5, 6).takeWhile(i -> i <= 3).count();
        long count2 = Stream.of(1, 2, 3, 4, 5, 6).dropWhile(i -> i <= 2).count();

        assertAll("takeWhile, dropWhile test",
                () -> assertEquals(3, count),
                () -> assertEquals(4, count2));
    }

    @Test
    public void ofNullableTest() {
        HashMap<Integer, String> mapNumber = new HashMap<>() {{
            put(1, "one");
            put(2, "two");
            put(3, "three");
            put(null, null);
        }};
        List<Integer> numbers = Arrays.asList(1, 2, 3, null);

        List<String> stringNumbers = numbers.stream()
                .flatMap(s -> Stream.ofNullable(mapNumber.get(s)))
                .collect(Collectors.toList());
        System.out.println(stringNumbers);
    }

}
