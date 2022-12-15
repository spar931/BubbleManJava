package Main;

import Blocks.BlockManager;
import Bomb.BubbleBomb;
import Bomb.Explosion;
import Entity.Player;
import Item.SuperItem;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable {
    //Screen settings
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3;
    public final int tileSize = originalTileSize * scale; // 48x48 tile
    public final int maxScreenCol = 16; // 16 tiles horizontally
    public final int maxScreenRow = 12; // 12 tiles vertically
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels
    KeyHandler key = new KeyHandler();
    Thread gameThread;

    public CollisionCheck collisionC = new CollisionCheck(this);

    public Player player = new Player(this, key);
    double fps = 60.00;

    public BlockManager blockM = new BlockManager(this);
    public ArrayList<SuperItem> item = new ArrayList<SuperItem>();
    public ArrayList<Explosion> explosions = new ArrayList<Explosion>();
    public ArrayList<BubbleBomb> bombs = new ArrayList<BubbleBomb>();
    public Assets assetSetter = new Assets(this);

    Sound music = new Sound();
    Sound se = new Sound();

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(key);
        this.setFocusable(true);
    }

    public void gameSetUp() {
        playMusic(0);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void run() {
        double drawInterval = 1000000000 / fps;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0; //display FPS

        while (gameThread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                requestFocusInWindow();
                repaint();
                delta--; // reset when delta = drawInterval
                drawCount++;
            }
            if (timer >= 1000000000) {
                System.out.println("FPS:" + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update(){
        player.update();

        for (int i = 0; i < bombs.size(); i++) {
            this.bombs.get(i).countdown();
            if (bombs.get(i).exploded) {
                player.restoreBomb();

                Explosion horizontal = new Explosion(bombs.get(i).x, bombs.get(i).y);
                Explosion vertical = new Explosion(bombs.get(i).x, bombs.get(i).y);
                horizontal.createHorizontalExplosion(bombs.get(i), blockM, assetSetter, player);
                vertical.createVerticalExplosion(bombs.get(i), blockM, assetSetter, player);
                explosions.add(horizontal);
                explosions.add(vertical);

                bombs.remove(bombs.get(i));
            }
        }

        for (int i = 0; i < explosions.size(); i++) {
            if (explosions.get(i).explosionFinish) {
                explosions.remove(explosions.get(i));
            } else {
                explosions.get(i).countdown();
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        blockM.draw(g2d);

        for (int i = 0; i < item.size(); i++) {
            if (item.get(i) != null) {
                item.get(i).draw(this, g2d);
            }
        }

        player.draw(g2d);

        for (int i = 0; i < this.bombs.size(); i++) {
            this.bombs.get(i).draw(this, g2d);
        }

        for (int i = 0; i < explosions.size(); i++) {
            explosions.get(i).draw(this, g2d);
        }

        g2d.dispose();
    }


    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    public void playSE(int i) {
        se.setFile(i);
        se.play();
    }
}
