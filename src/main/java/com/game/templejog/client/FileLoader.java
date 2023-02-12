package com.game.templejog.client;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.game.templejog.Game;
import com.game.templejog.Temple;

import java.io.*;
import java.sql.Date;
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

    public static String printAllSavedGames(){
        File[] SAVED_GAMES_LIST = new File("src/main/resources/JSON/SAVED").listFiles();
        StringBuilder savedGamesString = new StringBuilder();
        if ((SAVED_GAMES_LIST != null )&&( SAVED_GAMES_LIST.length>0)) {
            for (File session : SAVED_GAMES_LIST) {
                String[] sessionName = session.getName().split("@");
                savedGamesString.append(String.format("[%d] - session , saved on: %s ",Integer.parseInt(sessionName[0].split("-")[1]), sessionName[1].split("\\.")[0] ));
            }
        }
        return savedGamesString.toString();
    }
    public static Integer savedGamesCount(){
        File[] SAVED_GAMES_LIST = new File("src/main/resources/JSON/SAVED").listFiles();
        int count = 1;
        if ((SAVED_GAMES_LIST != null )&&( SAVED_GAMES_LIST.length>0)) {
            for (File session : SAVED_GAMES_LIST) { count+=1; }
        }
        return count;
    }
    public static String saveGame(Game game, String saveGameName){
        String PATH = "src/main/resources/JSON/SAVED/";
        File savedGamed;
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(SerializationFeature.INDENT_OUTPUT,true);
            savedGamed = new File(String.format("%s%s.json",PATH,saveGameName));
            objectMapper.writeValue(savedGamed,game);
        } catch (IOException e) { throw new RuntimeException(e); }
        return String.format("Game Saved as %s",saveGameName);
    }
    public static String getSavedGamePath(String targetSavedGame){
        File[] SAVED_GAMES_LIST = new File("src/main/resources/JSON/SAVED").listFiles();
        StringBuilder savedGamePath = new StringBuilder();
        if ((SAVED_GAMES_LIST != null )&&( SAVED_GAMES_LIST.length>0)) {
            for (File session : SAVED_GAMES_LIST) {
                if(session.getName().contains("session-"+targetSavedGame)) {
                    savedGamePath.append(session.getPath());
                }
            }
        }
        return savedGamePath.toString();
    }
    public static String getGameFilePath(String playerInput){
        if(playerInput.equals("y")) return "JSON/gameFiles.json";;
        String absolutePath = getSavedGamePath(playerInput);
        int idx = absolutePath.indexOf("resources/") + "resources/".length();
        String relPath = absolutePath.substring(idx);
        return relPath;
    }

}