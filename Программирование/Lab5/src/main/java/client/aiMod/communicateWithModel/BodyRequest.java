package client.aiMod.communicateWithModel;

import java.util.List;

public class BodyRequest {
    private final String model;
    private final String instruction;
    private final List<MyMessage> messages;
    private final boolean stream;

    public BodyRequest(String model, String instruction, List<MyMessage> messageFromModels){
        this.model = model;
        this.instruction = instruction;
        this.messages = messageFromModels;
        this.stream = false;
    }

    public boolean isStream() {
        return stream;
    }

    public List<MyMessage> getMessages() {
        return messages;
    }

    public String getInstruction() {
        return instruction;
    }

    public String getModel() {
        return model;
    }
}

