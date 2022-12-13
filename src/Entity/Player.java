package Entity;

import Bomb.BubbleBomb;
import Main.GamePanel;
import Main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity {
    GamePanel gamePanel;
    KeyHandler key;
    int numBoots = 0;
    int numBombs = 2;
    public int numPotions;


    public Player(GamePanel gamePanel, KeyHandler key) {
        this.gamePanel = gamePanel;
        this.key = key;

        solidArea = new Rectangle(); // creating small rectangle inside character to get in between walls smoothly
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 20;
        solidArea.height = 20;

        setDefaultValues();
        getPlayerImage();

    }

    public void setDefaultValues() {
        x = gamePanel.tileSize;
        y = gamePanel.tileSize;
        speed = 2;
        numPotions = 1;
        direction = "down";
    }

    public void getPlayerImage() { // using one image for now, to draw characters with legs later in different walking animations
        try {
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/angryemoji.png")));
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/angryemoji.png")));
            down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/angryemoji.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/angryemoji.png")));
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/angryemoji.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/angryemoji.png")));
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/angryemoji.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Player/angryemoji.png")));
            bomb = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Items/bomb.png")));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void update(){
        if (key.up || key.down || key.left || key.right) {
            if (key.up) {
                direction = "up";
            } else if (key.left) {
                direction = "left";
            } else if (key.down) {
                direction = "down";
            } else if (key.right) {
                direction = "right";
            }
            // check block collision
            collisionOn = false;
            gamePanel.collisionC.checkBlock(this);

            // check item collision
            int itemIndex = gamePanel.collisionC.checkItem(this, true);
            pickUpItem(itemIndex);

            // check bomb collision
            gamePanel.collisionC.checkBomb(this, true);

            if (!collisionOn) {
                switch (direction) {
                    case "up": y -= speed; break;
                    case "down": y += speed; break;
                    case "left": x -= speed; break;
                    case "right": x += speed; break;
                }
            }

            spriteCounter++;
            if (spriteCounter > 12) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                }
                else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }

        if (key.space && numBombs > 0) {
            placeBomb();
        }
    }

    public void placeBomb() {
        BubbleBomb bomb = new BubbleBomb();
        bomb.x = gamePanel.tileSize * (Math.round((float) x / gamePanel.tileSize)); // bomb should be placed in middle of tile closest to player
        bomb.y = gamePanel.tileSize * (Math.round((float) y / gamePanel.tileSize));
        for (int i = 0; i < gamePanel.bombs.size(); i++) {
            if (bomb.x == gamePanel.bombs.get(i).x && bomb.y == gamePanel.bombs.get(i).y) {
              return;
            }
        }
        gamePanel.bombs.add(bomb);
        numBombs--;
    }

    public void explode() {
        numBombs++;
    }

    public void pickUpItem(int index) {
        if (index != 9999) {
            String itemName = gamePanel.item.get(index).name;
            switch(itemName) { // to add bubbles, boots and other items. Will need an if statement for bubbles, as it should only work when bubbles are available
                case "Potion" :
                    gamePanel.playSE(2);
                    if (numPotions < 6) {
                        numPotions++;
                    }
                    gamePanel.item.remove(index); // make touched item disappear
                    System.out.println("Potions:" + numPotions);
                    break;
                case "Boots" :
                    gamePanel.playSE(3);
                    if (numBoots < 4) {
                        numBoots++;
                        speed += 1;
                    }
                    gamePanel.item.remove(index);
                    System.out.println("Boots:" + numBoots);
                    break;
                case "Bubble" :
                    if (numBombs < 6) {
                        numBombs++;
                    }
                    gamePanel.item.remove(index);
                    System.out.println("Bombs:" + numBombs);
                    break;
            }
        }
    }

    public void draw(Graphics2D g2d) { // draw player
        BufferedImage image = null;
        switch(direction) {
            case "up":
                if (spriteNum == 1) {
                    image = up1;
                }
                if (spriteNum == 2) {
                    image = up2;
                }
                break;
            case "down":
                if (spriteNum == 1) {
                    image = down1;
                }
                if (spriteNum == 2) {
                    image = down2;
                }
                break;
            case "left":
                if (spriteNum == 1) {
                    image = left1;
                }
                if (spriteNum == 2) {
                    image = left2;
                }
                break;
            case "right":
                if (spriteNum == 1) {
                    image = right1;
                }
                if (spriteNum == 2) {
                    image = right2;
                }
                break;
        }
    g2d.drawImage(image, x, y, gamePanel.tileSize - (gamePanel.tileSize / 3), gamePanel.tileSize - (gamePanel.tileSize / 3), null);
    }
}
