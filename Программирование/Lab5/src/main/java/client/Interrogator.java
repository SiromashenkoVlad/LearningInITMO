package client;

import java.util.Scanner;

public class Interrogator {
    private Scanner userScanner;
    private boolean fileMode = false;

    public Scanner getUserScanner() {
        return userScanner;
    }

    public void setUserScanner(Scanner userScanner) {
        this.userScanner = userScanner;
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