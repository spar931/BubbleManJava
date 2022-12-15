package Item;

import javax.imageio.ImageIO;
import java.io.IOException;

public class ITEM_bomb extends SuperItem{

    public ITEM_bomb() {
        name = "Bubble";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/Items/bombIncrease.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
