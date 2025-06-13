package audio;

import javax.sound.sampled.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AudioPlayer implements Runnable {

    public static AudioPlayer audioPlayer = new AudioPlayer();
    public static Clip clip;
    public static AudioClip music;
    public static long musicTimePosition;
    public static Thread thread;

    public static synchronized void play(AudioClip sfx, double volume) {
        Thread thread = new Thread(() -> {
            try (AudioInputStream stream = sfx.getAudioStream()) {
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
            } catch (IOException | UnsupportedAudioFileException ex) {
                Logger.getLogger(AudioPlayer.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
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
        try (AudioInputStream stream = music.getAudioStream()) {
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
        } catch (IOException | UnsupportedAudioFileException ex) {
            Logger.getLogger(AudioPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}