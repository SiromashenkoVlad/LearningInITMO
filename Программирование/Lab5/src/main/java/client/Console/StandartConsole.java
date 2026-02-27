package client.Console;


public class StandartConsole implements Console {
    private static final String PS1 = "$ ";
    private static final String PS2 = "> ";

    public void print(Object obj) {
        System.out.print(obj);
    }

    public void println(Object obj) {
        System.out.println(obj);
    }

    public void printError(Object obj) {
        System.out.println("ошибка: " + obj);
    }

    public void printTable(Object elementLeft, Object elementRight) {
        System.out.printf(" %-35s%-1s%n", elementLeft, elementRight);
    }

    public void ps1() {
        print(PS1);
    }

    public void ps2() {
        print(PS2);
    }

    public String getPS1() {
        return PS1;
    }

    public String getPS2() {
        return PS2;
    }
}
