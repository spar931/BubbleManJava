package Item;

import Main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperItem {
    public GamePanel gamePanel = new GamePanel();
    public BufferedImage image;
    public String name;
    public boolean collision = true;
    public int x, y;
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;
    public int width = gamePanel.tileSize, height = gamePanel.tileSize;
    public Rectangle solidArea = new Rectangle(solidAreaDefaultX, solidAreaDefaultY, gamePanel.tileSize, gamePanel.tileSize);

    public void draw(GamePanel gamePanel, Graphics2D g2d) {
        g2d.drawImage(image, x, y, width, height, null);
    }
}
