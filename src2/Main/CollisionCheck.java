package Main;

import Entity.Entity;

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

    public int checkItem(Entity entity, boolean player) {
        int index = 9999;
        for (int i = 0; i < gamePanel.item.size(); i++) {
            if (gamePanel.item.get(i) != null) {
                // entity's solid area position
                entity.solidArea.x = entity.x + entity.solidArea.x;
                entity.solidArea.y = entity.y + entity.solidArea.y;
                // item's solid area position
                gamePanel.item.get(i).solidArea.x = gamePanel.item.get(i).x + gamePanel.item.get(i).solidArea.x;
                gamePanel.item.get(i).solidArea.y = gamePanel.item.get(i).y + gamePanel.item.get(i).solidArea.y;

                switch (entity.direction) {
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        if (entity.solidArea.intersects(gamePanel.item.get(i).solidArea)) { // check if the 2 rectangles are touching
                            if (gamePanel.item.get(i).collision)
                                entity.collisionOn = true;
                            if (player) // interaction should only happen when a player (not another entity) collides with item
                                index = i;
                        }
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        if (entity.solidArea.intersects(gamePanel.item.get(i).solidArea)) {
                            if (gamePanel.item.get(i).collision)
                                entity.collisionOn = true;
                            if (player)
                                index = i;
                        }
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        if (entity.solidArea.intersects(gamePanel.item.get(i).solidArea)) {
                            if (gamePanel.item.get(i).collision)
                                entity.collisionOn = true;
                            if (player)
                                index = i;
                        }
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        if (entity.solidArea.intersects(gamePanel.item.get(i).solidArea)) {
                            if (gamePanel.item.get(i).collision)
                                entity.collisionOn = true;
                            if (player) // interaction should only happen when a player collides with item
                                index = i;
                        }
                        break;
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gamePanel.item.get(i).solidArea.x = gamePanel.item.get(i).solidAreaDefaultX;
                gamePanel.item.get(i).solidArea.y = gamePanel.item.get(i).solidAreaDefaultY;
            }
        }
        return index;
    }

    public void checkBomb(Entity entity, boolean player) {
        for (int i = 0; i < gamePanel.player.bombs.size(); i++) {
            if (gamePanel.player.bombs.get(i) != null) {
                entity.solidArea.x = entity.x + entity.solidArea.x;
                entity.solidArea.y = entity.y + entity.solidArea.y;
                gamePanel.player.bombs.get(i).solidArea.x = gamePanel.player.bombs.get(i).x;
                gamePanel.player.bombs.get(i).solidArea.y = gamePanel.player.bombs.get(i).y;

                switch (entity.direction) {
                    case "up":
                        entity.solidArea.y -= gamePanel.tileSize;
                        if (entity.solidArea.intersects(gamePanel.player.bombs.get(i).solidArea)) { // check if the 2 rectangles are touching
                            if (gamePanel.player.bombs.get(i).collision) {
                                entity.collisionOn = true;
                            }
                        }
                        break;
                    case "down":
                        entity.solidArea.y += gamePanel.tileSize;
                        if (entity.solidArea.intersects(gamePanel.player.bombs.get(i).solidArea)) {
                            if (gamePanel.player.bombs.get(i).collision) {
                                entity.collisionOn = true;
                            }
                        }
                        break;
                    case "left":
                        entity.solidArea.x -= gamePanel.tileSize;
                        if (entity.solidArea.intersects(gamePanel.player.bombs.get(i).solidArea)) {
                            if (gamePanel.player.bombs.get(i).collision) {
                                entity.collisionOn = true;
                            }
                        }
                        break;
                    case "right":
                        entity.solidArea.x += gamePanel.tileSize;
                        if (entity.solidArea.intersects(gamePanel.player.bombs.get(i).solidArea)) {
                            if (gamePanel.player.bombs.get(i).collision) {
                                entity.collisionOn = true;
                            }
                        }
                        break;
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gamePanel.player.bombs.get(i).solidArea.x = gamePanel.player.bombs.get(i).solidAreaDefaultX;
                gamePanel.player.bombs.get(i).solidArea.y = gamePanel.player.bombs.get(i).solidAreaDefaultY;
            }
        }
    }
}



