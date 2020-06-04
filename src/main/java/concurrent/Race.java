package concurrent;

import lombok.Getter;

import java.util.*;
import java.util.concurrent.CountDownLatch;

public class Race {
    private Random rand = new Random();

    @Getter
    private int distance = rand.nextInt(250);

    private List<String> horses = new ArrayList<>();

    public Race(String... names) {
        this.horses.addAll(Arrays.asList(names));
    }

    public void run() throws InterruptedException {
        System.out.println("And the horses are stepping up to the gate...");
        final CountDownLatch start = new CountDownLatch(1);
        final CountDownLatch finish = new CountDownLatch(horses.size());
        final List<String> places = Collections.synchronizedList(new ArrayList<>());

        for (final String h : horses) {
            new Thread(() -> {
                try {
                    System.out.println(h + " stepping up to the gate");
                    start.await();

                    int traveled = 0;
                    while (traveled < distance) {
                        Thread.sleep(rand.nextInt(3) * 1000);

                        traveled += rand.nextInt(15);
                        System.out.println(h + " advanced to " + traveled + "!");
                    }
                    finish.countDown();
                    System.out.println(h + " crossed the finish!");
                    places.add(h);
                } catch (InterruptedException intEx) {
                    System.out.println();
                }
            }).start();
        }
        System.out.println("And... they're off!");
        start.countDown();

        finish.await();
        System.out.println("And we have our winners!");
        System.out.println(places.get(0) + " took the gold...");
        System.out.println(places.get(1) + " got the silver...");
        System.out.println("and " + places.get(2) + " took home the bronze.");
    }
}