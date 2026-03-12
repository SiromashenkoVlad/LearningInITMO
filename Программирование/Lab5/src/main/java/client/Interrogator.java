package client;

import java.util.Scanner;

public class Interrogator {
    private final Scanner userScanner;
    private boolean fileMode = false;
    public Interrogator(Scanner userScanner) {
        this.userScanner = userScanner;
    }

    public Scanner getUserScanner() {
        return userScanner;
    }

    public boolean fileMode() {
        return fileMode;
    }

    public void setUserMode() {
        this.fileMode = false;
    }

    public void setFileMode() {
        this.fileMode = true;
    }
}