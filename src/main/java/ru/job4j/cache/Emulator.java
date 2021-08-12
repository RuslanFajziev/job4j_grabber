package ru.job4j.cache;

import java.util.Scanner;

public class Emulator {
    public static void printConsole(String txt) {
        System.out.println("-------------------------------------------------");
        System.out.println(txt);
        System.out.println("-------------------------------------------------");
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        printConsole("Enter the path to the file directory");
        String txt = input.nextLine();
        DirFileCache dirFileCache = new DirFileCache(txt);
        printConsole("Enter a file name to read or EXIT to shut down");
        txt = input.nextLine();
        while (!txt.equals("exit")) {
            printConsole(dirFileCache.load(txt));
            printConsole("Enter a file name to read or EXIT to shut down");
            txt = input.nextLine();
        }
    }
}
