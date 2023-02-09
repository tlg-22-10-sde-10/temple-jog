package com.game.templejog.client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.game.templejog.Temple;

import java.io.IOException;
import java.io.InputStream;

public class FileLoader {
    public static Temple jsonLoader(String path) throws IOException {
        Temple gameFiles;
        try(InputStream inputStream = Main.class.getClassLoader().getResourceAsStream(path)){
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
                gameFiles = mapper.readValue(inputStream,Temple.class);
            return gameFiles;
        }
    }
}
