package client;

import client.auth.Authorization;
import client.connection.ConnectionManager;
import client.connection.RetryPolicy;
import client.console.Console;
import client.console.StandartConsole;
import client.network.AuthenticatedRequestSender;
import client.network.BaseRequestSender;
import client.network.RequestSender;
import client.runners.Runner;
import client.session.SessionInitializer;
import common.enums.WorkMode;
import common.exceptions.DisconnectFromServer;
import common.requests.Argument;
import common.requests.Request;
import common.requests.Responce;
import common.userData.CredentialsProvider;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Map;
import java.util.Scanner;

public class EstablishConnection{
    private final SessionInitializer sessionInitializer;
    private final RetryPolicy retryPolicy;
    private Request pendingRequest = null;
    private CredentialsProvider cp = new CredentialsProvider();

    public EstablishConnection(SessionInitializer sessionInitializer, RetryPolicy retryPolicy) {
        this.sessionInitializer = sessionInitializer;
        this.retryPolicy = retryPolicy;
    }

    public void run() {
        Console console = new StandartConsole();
        Interrogator interrogator = new Interrogator(new Scanner(System.in));

        for (int attempt = 0; ; attempt++) {
            int delay = retryPolicy.getDelay(attempt);
            if (delay == -1) break;

            try (ConnectionManager conn = new ConnectionManager(
                    InetAddress.getByName("localhost"), 18234)) {

                Map<String, Argument[]> usages = sessionInitializer.initialize(conn);

                RequestSender sender = new AuthenticatedRequestSender(
                        new BaseRequestSender(conn), cp);
                if (cp.getLogin() == null || !Authorization.reauth(sender, cp))
                   if (!Authorization.auth(console, interrogator, sender, cp)) return;
                Runner runner = new Runner(console, interrogator, sender, usages);

                replayPendingRequest(runner);
                runner.interactiveMode(WorkMode.Interactive);
                return;

            } catch (IOException e) {
                System.err.println("Сервер недоступен, жду " + delay + "с");
                retryPolicy.sleep(delay);
            } catch (ClassNotFoundException e) {
                System.err.println("Ошибка десериализации: " + e.getMessage());
                retryPolicy.sleep(delay);
            } catch (DisconnectFromServer e) {
                System.err.println("Переподключаюсь...");
                System.err.println(e.getMessage());
                System.err.println(e.getStackTrace());
                if (e.getLastRequest() != null) {
                    System.out.println("Последний запрос: " + e.getLastRequest().getName());
                    pendingRequest = e.getLastRequest();
                }
                else {
                    System.out.println("Последний запрос: " + pendingRequest.getName());
                }

                retryPolicy.sleep(delay);
            }
        }
        System.out.println("Не удалось подключиться");
    }

    private void replayPendingRequest(Runner runner)
            throws IOException, ClassNotFoundException {
        System.out.println("retry1");
        if (pendingRequest != null) {
            System.out.println("retry2");
            Responce r = runner.sendRequest(pendingRequest);
            if (r.isSuccess()) {
                runner.outAnswer(r);
                pendingRequest = null;
            }
        }
    }
}
//    public Request r = null;
//
//    public void run(String fileName){
//        for (int i = 2; i < 15; i += 2){
//            try(ConnectionManager connectionManager = new ConnectionManager(InetAddress.getByName("localhost"), 18234)) {
//                Map<String, Argument[]> usages = (Map<String, Argument[]>) connectionManager.gettingResponse();
//                System.out.println("Типо принял usage");
//                Runner runner = new Runner(new StandartConsole(), new Interrogator(new Scanner(System.in)),
//                        new BaseRequestSender(connectionManager), usages);
//                if (r != null){
//                    Responce rep = runner.sendRequest(r);
//                    runner.outAnswer(rep);
//                    r = null;
//                }
//
//                runner.interactiveMode(WorkMode.Interactive);
//                break;
//            } catch (IOException e){
//                System.err.println("Сервер временно недоступен, пробую подключиться");
//                System.err.println(e.getMessage());
//                try{
//                    Thread.sleep(i * 1000);
//                } catch (InterruptedException ex) {
//                    System.err.println(ex.getMessage());
//                }
//            } catch (ClassNotFoundException e) {
//                System.err.println("Не смог прочитать usages");
//            } catch (DisconnectFromServer e){
//                System.err.println("Пробую переподключиться");
//                r = e.getLastRequest();
//            }
//        }
//        System.out.println("Подключение закрыто");
//    }

