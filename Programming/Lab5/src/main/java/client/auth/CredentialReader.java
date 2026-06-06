package client.auth;

import client.Interrogator;
import client.console.Console;
import client.readArguments.ValueFactory;

public class CredentialReader {
    public static String[] readCredentials(Console console, Interrogator interrogator) {
        console.println("Введите логин:");
        String login = (String) ValueFactory.getReader(String.class).read(console, interrogator);
        console.println("Введите пароль:");
        String password = (String) ValueFactory.getReader(String.class).read(console, interrogator);
        return new String[]{login, password};
    }

    public static boolean askRetry(Console console, Interrogator interrogator) {
        String answer;
        do {
            console.println("Повторить? (да/нет)");
            answer = (String) ValueFactory.getReader(String.class).read(console, interrogator);
        } while (!answer.equals("да") && !answer.equals("нет"));
        return answer.equals("да");
    }
}