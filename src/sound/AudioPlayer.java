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

    public static synchronized void play(AudioClip sfx, double volume) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                AudioInputStream stream = null;
                try {
                    stream = sfx.getAudioStream();
                    if (stream == null) {
                        System.out.println("ERROR >> Could not play sound effect. Stream is null.");
                        return;
                    }
                    try {
                        Clip clip = AudioSystem.getClip();
                        clip.open(stream);
                        setVolume(clip, volume);
                        clip.start();
                    } catch (LineUnavailableException | IOException ex) {
                        Logger.getLogger(AudioPlayer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (UnsupportedAudioFileException ex) {
                    Logger.getLogger(AudioPlayer.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(AudioPlayer.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        stream.close();
                    } catch (IOException ex) {
                        Logger.getLogger(AudioPlayer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
        thread.start();
    }

    public static synchronized void playMusic(AudioClip audioClip) {
        music = audioClip;
        thread = new Thread(audioPlayer);
        thread.start();
    }

    public static synchronized void stop() throws InterruptedException {
        if (thread != null) thread.join();
        if (clip != null) clip.stop();
    }

    public static synchronized void pause() {
        if (clip != null && clip.isRunning()) {
            musicTimePosition = clip.getMicrosecondPosition();
            clip.stop();
        }
    }

    public static synchronized void resume() {
        if (clip != null) {
            clip.setMicrosecondPosition(musicTimePosition);
            clip.start();
        }
    }

    private static void setVolume(Clip clip, double volume) {
        if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl gain = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = (float) (Math.log(volume) / Math.log(10) * 20);
            gain.setValue(dB);
        }
    }

    @Override
    public void run() {
        AudioInputStream stream = null;
        try {
            stream = music.getAudioStream();
            if (stream == null) {
                System.out.println("ERROR >> Could not play music. Stream is null.");
                return;
            }
            try {
                clip = AudioSystem.getClip();
                clip.open(stream);
                setVolume(clip, 1);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } catch (LineUnavailableException | IOException ex) {
                Logger.getLogger(AudioPlayer.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(AudioPlayer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AudioPlayer.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stream.close();
            } catch (IOException ex) {
                Logger.getLogger(AudioPlayer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
