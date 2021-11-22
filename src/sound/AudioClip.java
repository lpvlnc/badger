/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sound;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author Leonardo
 */
public class AudioClip {
    private File file;
    
    public AudioClip(String path){
        file = new File(path);
        if(!file.exists()){
            System.out.println("ERROR >> AudioClip Not Found!");
        }
    }
    
    public AudioInputStream getAudioStream() throws UnsupportedAudioFileException, IOException{
        return AudioSystem.getAudioInputStream(file);
    }
}
