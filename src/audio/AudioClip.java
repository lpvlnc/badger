package audio;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URL;

public class AudioClip {
    private URL resource;

    public AudioClip(String path) {
        resource = getClass().getResource(path);
        if (resource == null) {
            System.out.println("ERROR >> AudioClip Not Found at: " + path);
        }
    }

    public AudioInputStream getAudioStream() throws UnsupportedAudioFileException, IOException {
        if (resource == null) return null;
        return AudioSystem.getAudioInputStream(resource);
    }
}
