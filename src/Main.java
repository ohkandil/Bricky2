import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) throws Exception {
        JFrame obj = new JFrame();  // Create a new window
        GamePlay gamePlay = new GamePlay(); // Create a new GamePlay object
        obj.setBounds(10, 10, 700, 600);    // (x, y, width, height)
        obj.setTitle("Bricky2"); // Title of the game
        obj.setResizable(false);    // Disable resizing
        obj.setVisible(true);   // Make the window visible
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close the window when the close button is clicked
        obj.add(gamePlay);  // Add the gamePlay object to the window
    }
}