package Item;

import javax.imageio.ImageIO;
import java.io.IOException;

public class ITEM_boots extends SuperItem {
    public ITEM_boots() {
        name = "Boots";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/Items/boots.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
