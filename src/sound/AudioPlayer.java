/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sound;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author Leonardo
 */
public class AudioPlayer implements Runnable {
    
    public static AudioPlayer audioPlayer = new AudioPlayer();
    public static Clip clip;
    public static AudioClip music;
    public static long musicTimePosition;
    public static Thread thread;
    public static boolean isRunning = false;
    
    public static synchronized void play(AudioClip sfx, double volume){
 
        Thread thread = new Thread(){
            @Override
            public void run(){
                AudioInputStream stream;
                try {
                    
                    stream = sfx.getAudioStream();
                    Clip clip;
                    
                    try {
                        
                        clip = AudioSystem.getClip();
                        clip.open(stream);
                        setVolume(clip, volume);
                        clip.start();
                        
                    } catch (LineUnavailableException ex) {
                        Logger.getLogger(AudioPlayer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (UnsupportedAudioFileException | IOException ex) {
                    Logger.getLogger(AudioPlayer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }; thread.start();
    }
    
    public static synchronized void playMusic(AudioClip audioClip){
        music = audioClip;
        thread = new Thread(audioPlayer);
        thread.start();
    }
    
    public static synchronized void stop() throws InterruptedException{
        thread.join();
        clip.stop();
    }
    
    public static synchronized void pause(){
        musicTimePosition = clip.getMicrosecondPosition();
        clip.stop();
    }
    
    public static synchronized void resume(){
        clip.setMicrosecondPosition(musicTimePosition);
        clip.start();
    }
    
    private static void setVolume(Clip clip, double volume){
        FloatControl gain = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
        float dB = (float)(Math.log(volume) / Math.log(10) * 20);
        gain.setValue(dB);
    }

    @Override
    public void run() {
        AudioInputStream stream;
        try {
            stream = music.getAudioStream();

            try {
                clip = AudioSystem.getClip();
                clip.open(stream);
                setVolume(clip, 1);
                clip.loop(Clip.LOOP_CONTINUOUSLY);

            } catch (LineUnavailableException ex) {
                Logger.getLogger(AudioPlayer.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (UnsupportedAudioFileException | IOException ex) {
            Logger.getLogger(AudioPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
