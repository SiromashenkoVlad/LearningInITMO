package client;


import client.connection.RetryPolicy;
import client.session.SessionInitializer;


public class Main {
    public static void main(String[] args){
        new EstablishConnection(
                new SessionInitializer(), new RetryPolicy(7, 2)
        ).run();
    }
}
