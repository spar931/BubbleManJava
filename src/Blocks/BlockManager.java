package Blocks;

import Main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class BlockManager {
    GamePanel gamePanel;
    public Block[] block;
    public int[][] mapBlockNum;

    public BlockManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        block = new Block[10];
        mapBlockNum = new int[gamePanel.maxScreenCol][gamePanel.maxScreenRow];
        getBlockImage();
        loadMap("/Maps/mapdata.txt");
    }

    public void getBlockImage() {
        try {
            block[0] = new Block();
            block[0].image = ImageIO.read(getClass().getResourceAsStream("/Blocks/lego.png"));
            block[0].collision = true;
            block[0].canBeDestroyed = true;

            block[1] = new Block();
            block[1].image = ImageIO.read(getClass().getResourceAsStream("/Blocks/water2.png"));
            block[1].collision = true;

            block[2] = new Block();
            block[2].image = ImageIO.read(getClass().getResourceAsStream("/Blocks/legofloor.jpeg"));

            block[3] = new Block();
            block[3].image = ImageIO.read(getClass().getResourceAsStream("/Blocks/legoOrange.png"));
            block[3].collision = true;
            block[3].canBeDestroyed = true;

            block[4] = new Block();
            block[4].image = ImageIO.read(getClass().getResourceAsStream("/Blocks/hut.png"));
            block[4].collision = true;

            block[5] = new Block();
            block[5].image = ImageIO.read(getClass().getResourceAsStream("/Blocks/tree.png"));
            block[5].collision = true;

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String filename) { // read and store map data into 2d array
        try {
            InputStream inputS = getClass().getResourceAsStream(filename);
            BufferedReader bufferedR = new BufferedReader(new InputStreamReader(inputS));
            int row = 0;
            int col = 0;
            while(col < gamePanel.maxScreenCol && row < gamePanel.maxScreenRow) {
                String line = bufferedR.readLine();
                while (col < gamePanel.maxScreenCol) {
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapBlockNum[col][row] = num;
                    col++;
                }
                if (col == gamePanel.maxScreenCol) {
                    col = 0;
                    row++;
                }
            }
            bufferedR.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2d) {
        int x = 0;
        int y = 0;
        int blockNum;
        for (int row = 0; row < gamePanel.maxScreenRow; row++) {
            for (int col = 0; col < gamePanel.maxScreenCol; col++) {
                blockNum = mapBlockNum[col][row];
                g2d.drawImage(block[blockNum].image, x, y, gamePanel.tileSize, gamePanel.tileSize, null);
                x += gamePanel.screenWidth / gamePanel.maxScreenCol;
            }
            y += gamePanel.screenHeight / gamePanel.maxScreenRow;
            x = 0;
        }
    }
}
