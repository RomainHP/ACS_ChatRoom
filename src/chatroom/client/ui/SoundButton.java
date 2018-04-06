package chatroom.client.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    private Clip clip;

    private boolean play = false;

    public SoundButton(AudioInputStream sound) throws LineUnavailableException, IOException {
        super("Play");
        clip = AudioSystem.getClip();
        clip.open(sound);
        this.addActionListener(actionEvent -> playSound());
    }

    private synchronized void playSound() {
        if (!play){
            this.setText("Stop");
            this.play = true;
            clip.start();
        }else{
            this.setText("Play");
            this.play = false;
            clip.stop();
        }
    }
}
