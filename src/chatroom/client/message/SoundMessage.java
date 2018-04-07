package chatroom.client.message;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

/**
 * @author rcharpen
 */
public class SoundMessage extends Message {

    private File sound;

    public SoundMessage(File file, String nick) {
        super(file.toString(), nick);
        //convert file to sound
        sound = file;
    }

    public SoundMessage(File file, String nick, boolean priv) throws IOException {
        super(file.toString(), nick, priv);
        //convert file to sound
        sound = file;
    }

    public AudioInputStream getSound() throws IOException, UnsupportedAudioFileException {
        return AudioSystem.getAudioInputStream(sound.getAbsoluteFile());
    }

}
