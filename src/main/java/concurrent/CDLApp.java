package concurrent;

import java.io.IOException;

public class CDLApp {
    public static void main(String[] args) throws InterruptedException, IOException {
        System.out.println("Prepping...");

        Race r = new Race(
                "Beverly Takes a Bath",
                "RockerHorse",
                "Phineas",
                "Ferb",
                "Tin Cup",
                "I'm Faster Than a Monkey",
                "Glue Factory Reject"
        );
        System.out.println("It's a race of " + r.getDistance() + " lengths");

        System.out.println("Press Enter to run the race....");
        System.in.read();

        r.run();
    }
}
