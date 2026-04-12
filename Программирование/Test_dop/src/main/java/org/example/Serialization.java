package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Serialization {
    ObjectMapper mapper = new ObjectMapper();

    public String serialize(Object o) throws JsonProcessingException {
        return mapper.writeValueAsString(o);
    }
}
