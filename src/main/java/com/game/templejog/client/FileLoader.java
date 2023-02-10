package com.game.templejog.client;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.game.templejog.Game;
import com.game.templejog.Temple;

import java.io.*;
import java.time.LocalDateTime;

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

    public static String getSavedGames(){
        // File Name Convention: fileName_Date.json
        // Get list of available files
        // Print To User
        String date = LocalDateTime.now().toString();
        return String.format("[1] %s",date)+ String.format("\n[2] %s",date);
    }

    public static File saveGame(Game game){
        // check that no more than 1 saved games exist in JSON dir
        File savedGamed;
        try{
            savedGamed = new File("savedGame");
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(savedGamed,game);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return savedGamed;
    }

    public static void savedGame(){
        // file
        // Game Class
    }

}