package chatroom.client.ui;

import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.JButton;

/**
 *
 * @author rcharpen
 */
public class SoundButton extends JButton{
    
    private AudioInputStream sound;
    
    public SoundButton(AudioInputStream sound){
        super("Play");
        this.sound = sound;
    }
    
    public synchronized void playSound() {
        new Thread(() -> {
            try {
                Clip clip = AudioSystem.getClip();
                clip.open(SoundButton.this.sound);
                clip.start(); 
            } catch (LineUnavailableException | IOException e) {
                ExceptionPopup.showError(e);
            }
        }).start();
  }
}
