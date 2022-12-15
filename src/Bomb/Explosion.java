package Bomb;

import Blocks.Block;
import Entity.Player;
import Blocks.BlockManager;
import Entity.Entity;
import Item.SuperItem;
import Main.Assets;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Explosion extends SuperItem {
    private int explosionTime;
    public boolean explosionFinish = false;
    public Explosion(int x, int y) {
        name = "Explosion";
        this.x = x;
        this.y = y;
        explosionTime = 0;
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/Items/explosion.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void countdown() {
        this.explosionTime++;
        if (explosionTime > 20) {
            explosionFinish = true;
            explosionTime = 0;
        }
    }
    
    public void createVerticalExplosion(BubbleBomb bomb, BlockManager blockM, Assets assetSetter, Player player) {
        int checkTileY = bomb.y;
        // top explosion
        for (int q = 0; q < player.numPotions; q++) {
            checkTileY -= gamePanel.tileSize;
            int blockNum = blockM.mapBlockNum[this.x / gamePanel.tileSize][checkTileY / gamePanel.tileSize];
            if (blockNum == 2) {
                this.y -= gamePanel.tileSize;
            } else if (blockM.block[blockNum].canBeDestroyed) {
                this.y -= gamePanel.tileSize;
                blockM.mapBlockNum[this.x / gamePanel.tileSize][checkTileY / gamePanel.tileSize] = 2;
                assetSetter.setItem(this.x, checkTileY);
                break;
            } else {
                break;
            }
        }

        checkTileY = bomb.y;
        this.height = gamePanel.tileSize + Math.abs(this.y - bomb.y);
        // bottom explosion
        for (int q = 0; q < player.numPotions; q++) {
            checkTileY += gamePanel.tileSize;
            int blockNum = blockM.mapBlockNum[this.x / gamePanel.tileSize][checkTileY / gamePanel.tileSize];
            if (blockNum == 2) {
                this.height += gamePanel.tileSize;
            } else if (blockM.block[blockNum].canBeDestroyed) {
                this.height += gamePanel.tileSize;
                blockM.mapBlockNum[this.x / gamePanel.tileSize][checkTileY / gamePanel.tileSize] = 2;
                assetSetter.setItem(this.x, checkTileY);
                break;
            } else {
                break;
            }
        }
    }

    public void createHorizontalExplosion(BubbleBomb bomb, BlockManager blockM, Assets assetSetter, Player player) {
        int checkTileX = bomb.x;
        // left explosion
        for (int q = 0; q < player.numPotions; q++) {
            checkTileX -= gamePanel.tileSize;
            int blockNum = blockM.mapBlockNum[checkTileX / gamePanel.tileSize][this.y / gamePanel.tileSize];
            if (blockNum == 2) {
                this.x -= gamePanel.tileSize;
            } else if (blockM.block[blockNum].canBeDestroyed) {
                this.x -= gamePanel.tileSize;
                blockM.mapBlockNum[checkTileX / gamePanel.tileSize][this.y / gamePanel.tileSize] = 2;
                assetSetter.setItem(checkTileX, this.y);
                break;
            } else {
                break;
            }
        }

        checkTileX = bomb.x;
        this.width = gamePanel.tileSize + Math.abs(this.x - bomb.x);
        // right explosion
        for (int q = 0; q < player.numPotions; q++) {
            checkTileX += gamePanel.tileSize;
            int blockNum = blockM.mapBlockNum[checkTileX / gamePanel.tileSize][this.y / gamePanel.tileSize];
            if (blockNum == 2) {
                this.width += gamePanel.tileSize;
            } else if (blockM.block[blockNum].canBeDestroyed) {
                this.width += gamePanel.tileSize;
                blockM.mapBlockNum[checkTileX / gamePanel.tileSize][this.y / gamePanel.tileSize] = 2;
                assetSetter.setItem(checkTileX, this.y);
                break;
            } else {
                break;
            }
        }
    }
}
