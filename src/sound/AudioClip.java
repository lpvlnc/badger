/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sound;

import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author Leonardo
 */
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
