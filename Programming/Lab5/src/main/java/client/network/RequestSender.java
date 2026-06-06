package client.network;

import common.requests.Request;
import common.requests.Responce;

import java.io.IOException;

public interface RequestSender {
    Responce send(Request request) throws IOException, ClassNotFoundException;
}