package Item;

import javax.imageio.ImageIO;
import java.io.IOException;

public class ITEM_potion extends SuperItem {

    public ITEM_potion() {
        name = "Potion";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/Items/potion_red.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
