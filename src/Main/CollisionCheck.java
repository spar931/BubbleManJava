package Main;

import Bomb.BubbleBomb;
import Entity.Entity;
import Item.SuperItem;

import java.util.ArrayList;

public class CollisionCheck {
    GamePanel gamePanel;

    public CollisionCheck(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void checkBlock(Entity entity) {
        int entityLeftX = entity.solidArea.x + entity.x;
        int entityRightX = entity.solidArea.x + entity.solidArea.width + entity.x;
        int entityTopY = entity.solidArea.y + entity.y;
        int entityBotY = entity.solidArea.y + entity.solidArea.height + entity.y;

        int entityLeftCol = entityLeftX / gamePanel.tileSize;
        int entityRightCol = entityRightX / gamePanel.tileSize;
        int entityTopRow = entityTopY / gamePanel.tileSize;
        int entityBotRow = entityBotY / gamePanel.tileSize;

        int blockNum1, blockNum2; // 2 points of the rectangle that need to be checked when character moves

        switch (entity.direction) {
            case "up":
                entityTopRow = (entityTopY - entity.speed) / gamePanel.tileSize; // predict where player will be after moving upwards
                blockNum1 = gamePanel.blockM.mapBlockNum[entityLeftCol][entityTopRow];
                blockNum2 = gamePanel.blockM.mapBlockNum[entityRightCol][entityTopRow];
                if (gamePanel.blockM.block[blockNum1].collision || gamePanel.blockM.block[blockNum2].collision) { // if block is solid
                    entity.collisionOn = true;
                }
                break;
            case "down":
                entityBotRow = (entityBotY + entity.speed) / gamePanel.tileSize;
                blockNum1 = gamePanel.blockM.mapBlockNum[entityLeftCol][entityBotRow];
                blockNum2 = gamePanel.blockM.mapBlockNum[entityRightCol][entityBotRow];
                if (gamePanel.blockM.block[blockNum1].collision || gamePanel.blockM.block[blockNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftX - entity.speed) / gamePanel.tileSize;
                blockNum1 = gamePanel.blockM.mapBlockNum[entityLeftCol][entityTopRow];
                blockNum2 = gamePanel.blockM.mapBlockNum[entityLeftCol][entityBotRow];
                if (gamePanel.blockM.block[blockNum1].collision || gamePanel.blockM.block[blockNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightX + entity.speed) / gamePanel.tileSize;
                blockNum1 = gamePanel.blockM.mapBlockNum[entityRightCol][entityTopRow];
                blockNum2 = gamePanel.blockM.mapBlockNum[entityRightCol][entityBotRow];
                if (gamePanel.blockM.block[blockNum1].collision || gamePanel.blockM.block[blockNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
        }
    }

    public int checkItem(Entity entity, boolean player, ArrayList<SuperItem> items) {
        int index = 9999;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i) != null) {
                // entity's solid area position
                entity.solidArea.x = entity.x + entity.solidArea.x;
                entity.solidArea.y = entity.y + entity.solidArea.y;
                // item's solid area position
                items.get(i).solidArea.x = items.get(i).x + items.get(i).solidArea.x;
                items.get(i).solidArea.y = items.get(i).y + items.get(i).solidArea.y;

                switch (entity.direction) {
                    case "up":
                        if (items.get(i) instanceof BubbleBomb) {
                            entity.solidArea.y -= gamePanel.tileSize;
                        } else {
                            entity.solidArea.y -= entity.speed;
                        }
                        if (entity.solidArea.intersects(items.get(i).solidArea)) { // check if the 2 rectangles are touching
                            if (items.get(i).collision)
                                entity.collisionOn = true;
                            if (player) // interaction should only happen when a player (not another entity) collides with item
                                index = i;
                        }
                        break;
                    case "down":
                        if (items.get(i) instanceof BubbleBomb) {
                            entity.solidArea.y += gamePanel.tileSize;
                        } else {
                            entity.solidArea.y += entity.speed;
                        }
                        if (entity.solidArea.intersects(items.get(i).solidArea)) {
                            if (items.get(i).collision)
                                entity.collisionOn = true;
                            if (player)
                                index = i;
                        }
                        break;
                    case "left":
                        if (items.get(i) instanceof BubbleBomb) {
                            entity.solidArea.x -= gamePanel.tileSize;
                        } else {
                            entity.solidArea.x -= entity.speed;
                        }
                        if (entity.solidArea.intersects(items.get(i).solidArea)) {
                            if (items.get(i).collision)
                                entity.collisionOn = true;
                            if (player)
                                index = i;
                        }
                        break;
                    case "right":
                        if (items.get(i) instanceof BubbleBomb) {
                            entity.solidArea.x += gamePanel.tileSize;
                        } else {
                            entity.solidArea.x += entity.speed;
                        }
                        if (entity.solidArea.intersects(items.get(i).solidArea)) {
                            if (items.get(i).collision)
                                entity.collisionOn = true;
                            if (player) // interaction should only happen when a player collides with item
                                index = i;
                        }
                        break;
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                items.get(i).solidArea.x = items.get(i).solidAreaDefaultX;
                items.get(i).solidArea.y = items.get(i).solidAreaDefaultY;
            }
        }
        return index;
    }
}



