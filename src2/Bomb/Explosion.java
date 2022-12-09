package Bomb;

import Item.SuperItem;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Explosion extends SuperItem {
    private int explosionTime;
    public boolean explosionFinish = false;
    public Explosion() {
        name = "Explosion";
        explosionTime = 0;
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/Items/explosion.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void Countdown() {
        this.explosionTime++;
        if (explosionTime > 20) {
            explosionFinish = true;
            explosionTime = 0;
        }
    }
}
