package client;

import common.Intefaces.OnesStringArgumentable;
import common.Intefaces.Personable;
import common.Mainpart.Person;
import common.Model.Location;
import common.requests.Request;
import server.comands.Command;

import java.util.Map;
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