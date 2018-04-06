package chatroom.client.message;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author rcharpen
 */
public class SoundMessage extends Message{
    
    private AudioInputStream sound;
    
    public SoundMessage(File file, String nick) throws UnsupportedAudioFileException, IOException {
        super(file.toString(), nick);
        //convert file to sound
        AudioInputStream sound = AudioSystem.getAudioInputStream(file.getAbsoluteFile());
    }
    
    public AudioInputStream getSound(){
        return this.sound;
    }
    
}
