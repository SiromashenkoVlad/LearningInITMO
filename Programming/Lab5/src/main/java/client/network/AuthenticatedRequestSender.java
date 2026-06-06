package client.network;

import common.userData.CredentialsProvider;
import common.requests.Request;
import common.requests.Responce;

import java.io.IOException;

public class AuthenticatedRequestSender implements RequestSender {
    private final RequestSender wrapped;
    private final CredentialsProvider credentialsProvider;

    public AuthenticatedRequestSender(RequestSender wrapped,
                                      CredentialsProvider credentialsProvider) {
        this.wrapped = wrapped;
        this.credentialsProvider = credentialsProvider;
    }

    @Override
    public Responce send(Request request) throws IOException, ClassNotFoundException {
        if (credentialsProvider.getLogin() == null) {
            throw new IllegalStateException(
                    "Попытка отправить запрос без авторизации: " + request.getName()
            );
        }

        Request authenticatedRequest = new Request(
                request.getName(),
                request.getArgs(),
                credentialsProvider
        );
        return wrapped.send(authenticatedRequest);
    }
}
