package org.example;



import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class DeserializationChatResponse {
    ObjectMapper mapper = new ObjectMapper();

    public ChatResponse deserialization(String json) throws IOException {
        return mapper.readValue(json, ChatResponse.class);
    }
}
