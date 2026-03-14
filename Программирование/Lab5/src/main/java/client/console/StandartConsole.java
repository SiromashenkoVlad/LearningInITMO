package client.console;


public class StandartConsole implements Console {
    private static final String PS1 = "$ ";

    public void print(Object obj) {
        System.out.print(obj);
    }

    public void println(Object obj) {
        System.out.println(obj);
    }

    public void ps1() {
        print(PS1);
    }
}
