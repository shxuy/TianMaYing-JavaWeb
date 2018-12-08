import java.awt.Graphics;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JOptionPane;

public class GameView {
    private final Grid grid;
    private JPanel canvas;

    public void init() {
        canvas = new JPanel() {
            @Override
            public void paintComponent(Graphics graphics) {
                drawGridBackground(graphics);
                drawSnake(graphics, grid.getSnake());
                drawFood(graphics, grid.getFood());
            }
        };
    }

    public void draw() {
        canvas.repaint();
    }

    public JPanel getCanvas() {
        return canvas;
    }

    public void drawSnake(Graphics graphics, Snake snake) {
        for (Node current: snake.getBody())
            drawSquare(graphics, current, Color.WHITE);
    }

    public void drawFood(Graphics graphics, Node squareArea) {
        drawCircle(graphics, squareArea, Color.BLUE);
    }

    public void drawGridBackground(Graphics graphics) {
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, Settings.DEFAULT_NODE_SIZE * Settings.DEFAULT_GRID_WIDTH,
                Settings.DEFAULT_NODE_SIZE * Settings.DEFAULT_GRID_HEIGHT);
    }

    private void drawSquare(Graphics graphics, Node squareArea, Color color) {
        graphics.setColor(color);
        int size = Settings.DEFAULT_NODE_SIZE;
        graphics.fillRect(squareArea.getX() * size, squareArea.getY() * size, size - 1, size - 1);
    }

    private void drawCircle(Graphics graphics, Node squareArea, Color color) {
        graphics.setColor(color);
        int size = Settings.DEFAULT_NODE_SIZE;
        graphics.fillOval(squareArea.getX() * size, squareArea.getY() * size, size, size);
    }

    public void showGameOverMessage() {
        JOptionPane.showMessageDialog(null, "游戏结束", "游戏结束", JOptionPane.INFORMATION_MESSAGE);
    }

    public GameView(Grid grid) {
        this.grid = grid;
    }
}
