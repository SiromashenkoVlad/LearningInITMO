package server.Connection;

import server.managers.Communicator;

import java.nio.ByteBuffer;

public class Attach {
    private Communicator communicator;
    private ByteBuffer bf;
    private int length;
    private boolean firstTime = true;

    public Attach(int size){
        bf = ByteBuffer.allocate(size + 4);
    }

    public void setCommunicator(Communicator communicator) {
        this.communicator = communicator;
    }

    public void setFirstTime() {
        this.firstTime = false;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public ByteBuffer getBf() {
        return bf;
    }

    public Communicator getCommunicator() {
        return communicator;
    }

    public int getLength() {
        return length;
    }

    public boolean isFirstTime() {
        return firstTime;
    }
}
