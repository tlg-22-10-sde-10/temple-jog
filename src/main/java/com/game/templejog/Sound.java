package com.game.templejog;

import com.game.templejog.client.Main;
import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class Sound {
    private static Clip clip;

    /* Handles the background theme music */
    public static void themeSound(String file) {
        try {
            URL landingSound = Main.class.getClassLoader().getResource(file);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(landingSound);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            FloatControl gainControl =
                    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-10.0f); // Reduce volume by 10 decibels.
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException e) {
            System.out.println("Error: Unsupported audio file format.");
        } catch (IOException e) {
            System.out.println("Error: Could not read audio file.");
        } catch (LineUnavailableException e) {
            System.out.println("Error: Could not play audio clip.");
        }
    }

    public static String turningSound(String noun, Game game){
        if(noun.isEmpty()){
            return InvalidNounInput.BAD_SOUND.getWarning();
        } else if(noun.equalsIgnoreCase("on")){
            game.setPlaySound(true);
            Sound.themeSound(game.getCurrentRoom().getSound());
        } else if(noun.equalsIgnoreCase("off")){
            Sound.stopSound();
            game.setPlaySound(false);
        }
        return "Turning sound " + noun;
    }

    public static void gameSound(Scanner scanner, Game game) {
        System.out.println(UserInput.TURN_MUSIC.getUserPrompt());
        String musicOn = scanner.nextLine();
        if (musicOn.equalsIgnoreCase("y")){
            game.setPlaySound(true);
            Sound.themeSound("sounds/background_music.wav");
        } else {
            game.setPlaySound(false);
        }
    }

    public static void stopSound(){
        if(clip != null) {
            clip.stop();
        }
    }

    public static void ending(String filePath) {
        try {
            stopSound();
            URL sound = Main.class.getClassLoader().getResource(filePath);
            AudioInputStream audiostream = AudioSystem.getAudioInputStream(sound);
            clip = AudioSystem.getClip();
            clip.open(audiostream);
            FloatControl gainControl =
                    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-10.0f); // Reduce volume by 10 decibels.
            clip.loop(0);
        } catch (UnsupportedAudioFileException e) {
            System.out.println("Error: Unsupported audio file format.");
        } catch (IOException e) {
            System.out.println("Error: Could not read audio file.");
        } catch (LineUnavailableException e) {
            System.out.println("Error: Could not play audio clip.");
        }
    }
}
