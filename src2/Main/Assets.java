package Main;

import Bomb.BubbleBomb;
import Item.ITEM_boots;
import Item.ITEM_bubble;
import Item.ITEM_potion;

public class Assets {

    GamePanel gamePanel;

    public Assets(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setItem() {
        // gamePanel.item.add(new ITEM_potion());

        ITEM_boots boots = new ITEM_boots();
        boots.x = 96;
        boots.y = 96;
        gamePanel.item.add(boots);

        // gamePanel.item.add(new ITEM_bubble());

    }
}
