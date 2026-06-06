package server.сonnection;

import server.managers.Communicator;

import java.nio.ByteBuffer;

public class Attach {
    private Communicator communicator;
    private ByteBuffer bf;
    private int length;
    private boolean haveLength = false;
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

    public boolean isHaveLength() {
        return haveLength;
    }

    public void setSizeBf(int size) {
        haveLength = true;
        length = size;
        if (bf.capacity() >= size) return;


        ByteBuffer tmp = ByteBuffer.allocate(size * 2);
        bf.flip();
        tmp.put(bf);
        bf = tmp;
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

    public void close(){
        length = 0;
        haveLength = false;
    }
}
