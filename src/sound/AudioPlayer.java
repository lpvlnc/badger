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
public class AudioPlayer {
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
    
    public static synchronized void loop(AudioClip sfx, double volume){
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
                        clip.loop(Clip.LOOP_CONTINUOUSLY);
                        
                    } catch (LineUnavailableException ex) {
                        Logger.getLogger(AudioPlayer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (UnsupportedAudioFileException | IOException ex) {
                    Logger.getLogger(AudioPlayer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }; thread.start();
    }
    
    private static void setVolume(Clip clip, double volume){
        FloatControl gain = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
        float dB = (float)(Math.log(volume) / Math.log(10) * 20);
        gain.setValue(dB);
    }
}
