import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Predicate;

public class StreamTest {

    @Test
    public void StreamOperationCount() {

        AtomicInteger count1 = new AtomicInteger();
        AtomicInteger count2 = new AtomicInteger();
        final List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        Integer result = null;
        for (final Integer number : numbers) {
            if (number > 3 && number < 9) {
                final Integer newNumber = number * 2;
                if (newNumber > 10) {
                    result = newNumber;
                    break;
                }
            }
        }
        System.out.println("Imperative Result: " + result);

        System.out.println("Functional Result: " +
                numbers.stream()
                        .filter(number -> {
                            count1.getAndIncrement();
                            return number > 3;
                        })
                        .filter(number -> {
                            count1.getAndIncrement();
                            return number < 9;
                        })
                        .map(number -> {
                            count1.getAndIncrement();
                            return number * 2;
                        })
                        .filter(number -> {
                            count1.getAndIncrement();
                            return number > 10;
                        })
                        .findFirst()
        );
        System.out.println("Functional operation count1: " + count1);

        final List<Integer> greaterThan3 = filter(numbers, i -> {
            count2.getAndIncrement();
            return i > 3;
        });
        final List<Integer> lessThan9 = filter(greaterThan3, i -> {
            count2.getAndIncrement();
            return i < 9;
        });
        final List<Integer> doubled = map(lessThan9, i -> {
            count2.getAndIncrement();
            return i * 2;
        });
        final List<Integer> greaterThan10 = filter(doubled, i -> {
            count2.getAndIncrement();
            return i > 10;
        });
        System.out.println("My own method operation count2: " + count2);

    }

    private static <T> List<T> filter(List<T> list, Predicate<? super T> predicate) {
        final List<T> result = new ArrayList<>();
        for (final T t : list) {
            if (predicate.test(t)) {
                result.add(t);
            }
        }
        return result;
    }

    private static <T, R> List<R> map(List<T> list, Function<T, R> mapper) {
        final List<R> result = new ArrayList<>();
        for (final T t : list) {
            result.add(mapper.apply(t));
        }
        return result;
    }
}
