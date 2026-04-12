package org.example;

import java.util.List;

public class BodyRequest {
    private final String model;
    private final String instruction;
    private final List<Message> messages;
    private final boolean stream;

    public BodyRequest(String model, String instruction, List<Message> messages){
        this.model = model;
        this.instruction = instruction;
        this.messages = messages;
        this.stream = false;
    }

    public boolean isStream() {
        return stream;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public String getInstruction() {
        return instruction;
    }

    public String getModel() {
        return model;
    }
}
