package client.auth;

import client.Interrogator;
import client.console.Console;
import client.network.RequestSender;
import client.readArguments.ValueFactory;
import common.requests.Request;
import common.requests.Responce;
import common.userData.CredentialsProvider;


public class Authorization {
    public static boolean auth(Console console, Interrogator interrogator,
                               RequestSender sender, CredentialsProvider cp) {
        while (true) {
            console.println("Выберите действие: log / reg / exit");
            String type = (String) ValueFactory.getReader(String.class)
                    .read(console, interrogator);
            switch (type) {
                case "log"  -> { if (AuthService.login(console, interrogator, sender, cp))    return true; }
                case "reg"  -> { if (AuthService.register(console, interrogator, sender, cp)) return true; }
                case "exit" -> { return false; }
                default     -> console.println("Неизвестная команда: " + type);
            }
        }
    }

    public static boolean reauth(RequestSender sender, CredentialsProvider cp) {
        try {
            Responce response = sender.send( new Request("log", null, cp));
            return response.isSuccess();
        } catch (Exception e) {
            return false;
        }
    }
}