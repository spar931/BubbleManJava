package Item;

import javax.imageio.ImageIO;
import java.io.IOException;

public class ITEM_bubble extends SuperItem{

    public ITEM_bubble() {
        name = "Bubble";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/Items/bomb.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
