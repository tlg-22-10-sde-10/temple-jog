package com.game.templejog.client;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.game.templejog.Game;
import com.game.templejog.Temple;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Array;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

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
        File[] SAVED_GAMES_LIST = new File("JSON/").listFiles();
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss");
        //DateTimeFormatter readableFormat = DateTimeFormatter.ofPattern("yyyy MMM dd 'at' HH:mm:ss");
        HashMap<Integer, String> mappedGames = new HashMap<>();
        if ((SAVED_GAMES_LIST != null )&&( SAVED_GAMES_LIST.length>0)) {
            int number = 0;
            for (File session : SAVED_GAMES_LIST) {
//                String[] name = session.getName().split("@");
//                //String number = name[0].split("-")[1];
//                String date = name[1].split("\\.")[0];
//                date = LocalDateTime.parse(date,formatter).format(readableFormat);
                mappedGames.put(++number,session.getName());
            }
        }
        Collection<String> values = mappedGames.values();
        StringBuilder outputNames = new StringBuilder();
        for (String value : values) {
            outputNames.append(value);
        }
        return outputNames.toString();
    }
    public static String savedGamesCount(){
        File[] SAVED_GAMES_LIST = new File("src/main/resources/JSON/SAVED").listFiles();
        int count = 1;
        if ((SAVED_GAMES_LIST != null )&&( SAVED_GAMES_LIST.length>0)) {
            for (File session : SAVED_GAMES_LIST) { count+=1; }
        }
        return String.valueOf(count);
    }
    public static String saveGame(Game game, String newSave){
//        String PATH = "src/main/resources/JSON/SAVED/";
//        File savedGamed;
//        try{
//            ObjectMapper objectMapper = new ObjectMapper();
//            objectMapper.configure(SerializationFeature.INDENT_OUTPUT,true);
//            savedGamed = new File(String.format("%s%s.json",PATH,newSave));
//            objectMapper.writeValue(savedGamed,game);
//        } catch (IOException e) { throw new RuntimeException(e); }

        try {
            InputStream jsonFile = Main.class.getClassLoader().getResourceAsStream("JSON/gameFiles.json");
            File jarFile = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath());
            JarFile jar = new JarFile(jarFile);
            JarEntry entry = jar.getJarEntry("JSON/gameFiles.json");
            File destFile = new File(entry.getName());

            //Files.copy(jsonFile,destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            try {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.configure(SerializationFeature.INDENT_OUTPUT,true);
                objectMapper.writeValue(destFile,game);

            } catch (IOException e) {
                System.out.println(e);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        return String.format("Game Saved as %s",newSave);
    }
    public static Boolean maxNumberOfSaves(){
        File[] SAVED_GAMES_LIST = new File("src/main/resources/JSON/SAVED").listFiles();
        return (SAVED_GAMES_LIST.length > 2);
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
        int idx = absolutePath.indexOf("JSON/") + "JSON/".length();
        String relPath = absolutePath.substring(idx);
        return relPath;
    }

}