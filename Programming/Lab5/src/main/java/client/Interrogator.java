package client;

import java.util.Scanner;

public class Interrogator {
    private final Scanner userScanner;
    public Interrogator(Scanner userScanner) {
        this.userScanner = userScanner;
    }

    public Scanner getUserScanner() {
        return userScanner;
    }
}