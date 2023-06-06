

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class GamePlay extends JPanel implements KeyListener, ActionListener {

    private boolean play = false;   // Game starts when play = true
    private int score = 0;      // Score
    private int totalbricks = 21;   // Total number of bricks
    private Timer Timer;    // Timer for the ball
    private int delay = 8;  // Delay for the ball
    private int playerX = 310;      // Player X starting position
    private int ballposX = 120;    // Ball X starting position
    private int ballposY = 350;   // Ball Y starting position
    private int ballXdir = -1;  // Ball X direction
    private int ballYdir = -2;  // Ball Y direction
    private MapGenerator map;   // MapGenerator object


    public GamePlay() {     // Constructor
        map = new MapGenerator(3, 7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        Timer = new Timer(delay, this);
        Timer.start();
    }

    public void paint(Graphics g) {    // Paint the objects
        g.setColor(Color.black);    // Background color
        g.fillRect(1, 1, 692, 592); // Background position

        map.draw((Graphics2D) g);   // Draw the map

        g.setColor(Color.yellow);   // Border color
        g.fillRect(0, 0, 3, 592);   // Left border
        g.fillRect(0, 0, 692, 3);   // Top border
        g.fillRect(691, 0, 3, 592); // Right border

        g.setColor(Color.white);    // Score color
        g.setFont(new Font("serif", Font.BOLD, 25));    // Score font
        g.drawString("" + score, 590, 30);  // Score position

        g.setColor(Color.yellow);   // Paddle color
        g.fillRect(playerX, 550, 100, 8);   // Paddle position

        //ball
        g.setColor(Color.GREEN);    // Ball color
        g.fillOval(ballposX, ballposY, 20, 20);   // Ball position

        if (ballposY > 570) {   // If the ball goes below the paddle
            play = false;   // Game over
            ballXdir = 0;   // Stop the ball
            ballYdir = 0;   // Stop the ball
            g.setColor(Color.red);  // Game over color
            g.setFont(new Font("serif", Font.BOLD, 30));    // Game over font
            g.drawString("    Game Over Score: " + score, 190, 300);    // Game over position

            g.setFont(new Font("serif", Font.BOLD, 30));    // Restart font
            g.drawString("   Press Enter to Restart", 190, 340);    // Restart position
        }
        if(totalbricks == 0){   // If all bricks are destroyed
            play = false;   // Game over
            ballYdir = -2;  // Stop the ball
            ballXdir = -1;  // Stop the ball
            g.setColor(Color.red);  // Game over color
            g.setFont(new Font("serif",Font.BOLD,30));   // Game over font
            g.drawString("    You Win!!, Your score is: "+score,190,300);   // You win position

            g.setFont(new Font("serif", Font.BOLD, 30));    // Restart font
            g.drawString("   Press Enter to Restart", 190, 340);    // Restart position


        }

        g.dispose();


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Timer.start();

        if (play) { // If the game is running
            if (new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {    // If the ball hits the paddle
                ballYdir = -ballYdir;   // Change the ball direction
            }

            A:
            for (int i = 0; i < map.map.length; i++) {  // Check if the ball hits the bricks
                for (int j = 0; j < map.map[0].length; j++) {
                    if (map.map[i][j] > 0) {    // If the brick is not destroyed
                        int brickX = j * map.brickWidth + 80;   // Brick X position
                        int brickY = i * map.brickHeight + 50;  // Brick Y position
                        int bricksWidth = map.brickWidth;
                        int bricksHeight = map.brickHeight;

                        Rectangle rect = new Rectangle(brickX, brickY, bricksWidth, bricksHeight);  // Brick rectangle
                        Rectangle ballrect = new Rectangle(ballposX, ballposY, 20, 20); // Ball rectangle
                        Rectangle brickrect = rect; // Brick rectangle

                        if (ballrect.intersects(brickrect)) {   // If the ball hits the brick
                            map.setBrickValue(0, i, j); // Destroy the brick
                            totalbricks--;  // Decrease the total number of bricks
                            score += 5; // Increase the score
                            if (ballposX + 19 <= brickrect.x || ballposX + 1 >= brickrect.x + bricksWidth) {    // If the ball hits the side of the brick
                                ballXdir = -ballXdir;   // Change the ball direction
                            } else {    // If the ball hits the top or bottom of the brick
                                ballYdir = -ballYdir;   // Change the ball direction
                            }
                            break A;
                        }
                    }


                }
            }


            ballposX += ballXdir;
            ballposY += ballYdir;
            if (ballposX < 0) { // If ball hits left map border
                ballXdir = -ballXdir;   // Change the ball direction
            }
            if (ballposY < 0) { // If ball hits top map border
                ballYdir = -ballYdir;   // Change the ball direction
            }
            if (ballposX > 670) {   // If ball hits right map border
                ballXdir = -ballXdir;   // Change the ball direction
            }
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {  // Not used

    }


    @Override
    public void keyReleased(KeyEvent e) {   // Not used

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {      //right arrow key pressed
            if (playerX >= 600) {   // If the paddle hits the right border
                playerX = 600;  // Stop the paddle
            } else {    // If the paddle doesn't hit the right border
                moveRight();    // Move the paddle right
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {   //left arrow key pressed
            if (playerX < 10) { // If the paddle hits the left border
                playerX = 10;   // Stop the paddle
            } else {    // If the paddle doesn't hit the left border
                moveLeft(); // Move the paddle left
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {  //enter key pressed
            if (!play) {    // If the game is not running
                ballposX = 120;
                ballposY = 350;
                ballXdir = -1;
                ballYdir = -2;
                score = 0;  // Reset the score
                playerX = 310;
                totalbricks = 21;
                map = new MapGenerator(3, 7);   // Reset the map

                repaint();
            }
        }


    }

    public void moveRight ()    // move right
    {
        play = true;
        playerX += 20;
    }
    public void moveLeft ()    //move left
    {
        play = true;
        playerX -= 20;
    }



}