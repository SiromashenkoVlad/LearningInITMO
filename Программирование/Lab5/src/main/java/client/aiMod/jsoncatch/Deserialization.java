package client.aiMod.jsoncatch;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class Deserialization {
    ObjectMapper mapper = new ObjectMapper();

    public <T> T deserialization(String json, Class<T> clazz) throws IOException, JsonParseException {
        return mapper.readValue(json, clazz);
    }
}
