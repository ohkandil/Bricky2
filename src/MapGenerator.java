import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class MapGenerator {
    public int map[][]; // 2D array for the map
    public int brickWidth;  // Width of the brick
    public int brickHeight; // Height of the brick
    // Constructor
    public MapGenerator(int row, int col) { // row and col are the number of bricks
        map = new int[row][col];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                map[i][j] = 1;
            }
        }
        brickWidth = 540/col;   // Width of the brick
        brickHeight = 150/row;  // Height of the brick
    }
    // Draw the bricks
    public void draw(Graphics2D g) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] > 0) {
                    g.setColor(Color.white);    // Brick color
                    g.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight); // Brick position

                    g.setStroke(new BasicStroke(3));    // Brick border
                    g.setColor(Color.black);    // Brick border color
                    g.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight); // Brick border position
                }
            }
        }
    }

    // Set the value of the brick
    public void setBrickValue(int value, int row, int col) {
        map[row][col] = value;
    }
}