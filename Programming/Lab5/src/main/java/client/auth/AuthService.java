package client.auth;


import client.Interrogator;
import client.console.Console;
import client.network.RequestSender;
import common.exceptions.DisconnectFromServer;
import common.requests.Request;
import common.requests.Responce;
import common.userData.CredentialsProvider;

import java.io.IOException;

public class AuthService {
    private static final int MAX_ATTEMPTS = 3;

    public static boolean login(Console console, Interrogator interrogator, RequestSender sender,
                         CredentialsProvider cp) {
        for (int attempt = 1; attempt <= MAX_ATTEMPTS; attempt++) {
            String[] creds = CredentialReader.readCredentials(console, interrogator);
            cp.setCredentials(creds[0], creds[1]);

            Request req = new Request("log", null, cp);
            try {
                Responce r = sender.send(req);
                System.out.println(r.getAnswer());
                if (r.isSuccess()) return true;

                if (attempt < MAX_ATTEMPTS && !CredentialReader.askRetry(console, interrogator)) return false;

            } catch (IOException | ClassNotFoundException e) {
                throw new DisconnectFromServer(e.getMessage(), req);
            }
        }
        System.out.println("Превышено число попыток");
        return false;
    }

    public static boolean register(Console console, Interrogator interrogator, RequestSender sender,
                            CredentialsProvider cp) {
        for (int attempt = 1; attempt <= MAX_ATTEMPTS; attempt++) {
            String[] creds = CredentialReader.readCredentials(console, interrogator);
            cp.setCredentials(creds[0], creds[1]);

            Request req = new Request("reg", null, cp);
            try {
                Responce r = sender.send(req);
                System.out.println(r.getAnswer());
                if (r.isSuccess()) return true;

                if (attempt < MAX_ATTEMPTS && !CredentialReader.askRetry(console, interrogator)) return false;

            } catch (IOException | ClassNotFoundException e) {
                throw new DisconnectFromServer(e.getMessage(), req);
            }
        }
        System.out.println("Превышено число попыток");
        return false;
    }
}