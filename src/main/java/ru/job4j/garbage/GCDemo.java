package ru.job4j.garbage;

import static com.carrotsearch.sizeof.RamUsageEstimator.sizeOf;

public class GCDemo {
    private static final long KB = 1000;
    private static final long MB = KB * KB;
    private static final Runtime ENVIRONMENT = Runtime.getRuntime();

    public static void info() {
        final long freeMemory = ENVIRONMENT.freeMemory();
        final long totalMemory = ENVIRONMENT.totalMemory();
        final long maxMemory = ENVIRONMENT.maxMemory();
        System.out.println("=== Environment state ===");
        System.out.printf("Free: %d%n", freeMemory / MB);
        System.out.printf("Total: %d%n", totalMemory / MB);
        System.out.printf("Max: %d%n", maxMemory / MB);
    }

    public static void main(String[] args) {
        info();
        User usr = new User();
        User usr2 = new User();
        System.out.println(sizeOf(usr));
        System.out.println(sizeOf(usr2));
        info();
        User usr3 = new User();
        User usr4 = new User();
        System.out.println(sizeOf(usr3));
        System.gc();
        info();
        System.gc();
        info();
    }
}
