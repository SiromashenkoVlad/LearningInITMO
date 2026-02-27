package client;

import common.requests.Request;

import java.util.ArrayList;

public class MakeRequest {
    private ArrayList<Request> requests = new ArrayList<>();

    public void addRequest(Request request){
        requests.add(request);
    }


}
