package Main;

import Item.ITEM_boots;
import Item.ITEM_bomb;
import Item.ITEM_potion;
import Item.SuperItem;

import java.util.Random;

public class Assets {

    GamePanel gamePanel;

    Random rand;
    public Assets(GamePanel gamePanel) {

        this.gamePanel = gamePanel;
        this.rand = new Random();
    }

    public void setItem(int x, int y) {
        int randomNumber = rand.nextInt(10);
        SuperItem item = null;
        if (randomNumber == 0) {
            item = new ITEM_bomb();
        } else if (randomNumber == 1) {
            item = new ITEM_boots();
        } else if (randomNumber == 2) {
            item = new ITEM_potion();
        }
        if (item != null) {
            item.x = x;
            item.y = y;
            gamePanel.item.add(item);
        }
    }
}
