import java.awt.Container;
import java.awt.Dimension;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class SnakeApp implements Runnable {

    @Override
    public void run() {
        //创建游戏窗体
        JFrame window = new JFrame("贪吃蛇游戏");

        Container contentPane = window.getContentPane();

        // 基于Grid初始化gamaView
        Grid grid = new Grid(Settings.DEFAULT_GRID_WIDTH, Settings.DEFAULT_GRID_HEIGHT);
        GameView gameView = new GameView(grid);
        gameView.init();

        // 设置gameView中JPanel的大小
        gameView.getCanvas().setPreferredSize(new Dimension(
                Settings.DEFAULT_NODE_SIZE * Settings.DEFAULT_GRID_WIDTH,
                Settings.DEFAULT_NODE_SIZE * Settings.DEFAULT_GRID_HEIGHT));

        // 将gameView中JPanel加入到窗口中
        contentPane.add(gameView.getCanvas(), BorderLayout.CENTER);

        // 画出棋盘和贪吃蛇
        window.pack();
        window.setLocationRelativeTo(null); // 把窗口置于屏幕中央
        window.setResizable(false); // 设置窗口为大小不可变化
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 窗口关闭的行为
        window.setVisible(true); // 渲染和显示窗口

        GameController gameController = new GameController(grid, gameView);
        window.addKeyListener(gameController);

        new Thread(gameController).start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new SnakeApp());
    }
}
