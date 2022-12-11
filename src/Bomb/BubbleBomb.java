package Bomb;

import Item.SuperItem;

import javax.imageio.ImageIO;
import java.io.IOException;

public class BubbleBomb extends SuperItem {
    private int timeToExplode;
    public boolean exploded;
    
    public BubbleBomb() {
        timeToExplode = 0;
        exploded = false;
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/Items/bomb.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void countdown() {
        this.timeToExplode++;
        if (timeToExplode > 120) {
            exploded = true;;
            timeToExplode = 0;
        }
    }
}
