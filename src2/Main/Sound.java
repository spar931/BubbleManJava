package Main;
// must be WAV and 16 bit (32 doesn't work)
// can create sounds in beepbox
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class Sound {
    Clip clip;
    URL[] soundURL = new URL[30];

    public Sound() {
    }

    public void setFile(int i) {

    }

    public void play() {
        // clip.start();
    }

    public void loop() {
        // clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }
}
