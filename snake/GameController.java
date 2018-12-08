import java.util.Queue;
import java.util.ArrayDeque;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class GameController implements Runnable, KeyListener {
    private Grid grid;
    private GameView gameView;

    private boolean running;
    private boolean notObserved;
    private Queue<Direction> keyEvents = new ArrayDeque<>();

    @Override
    public void keyPressed(KeyEvent e) {
        // 这里处理按键
        int keyCode = e.getKeyCode();

        switch (keyCode) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                keyEvents.add(Direction.UP);
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                keyEvents.add(Direction.DOWN);
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                keyEvents.add(Direction.LEFT);
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                keyEvents.add(Direction.RIGHT);
                break;
        }

        // repaint the canvas
        gameView.draw();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // 用不到本方法
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // 用不到本方法
    }

    @Override
    public void run() {
        while (running) {
            if (notObserved) {
                try {
                    Thread.sleep(Settings.DEFAULT_OBSERVE_INTERVAL);
                } catch (InterruptedException e) {
                    break;
                }
                notObserved = false;
            }
            // 进入游戏下一步
            if (!keyEvents.isEmpty())
                grid.changeDirection(keyEvents.remove());
            running = grid.nextRound();
            // 如果结束，则退出游戏
            // 如果继续，则绘制新的游戏页面
            if (running) {
                gameView.draw();
            } else {
                gameView.showGameOverMessage();
                System.exit(0);
            }
            try {
                Thread.sleep(Settings.DEFAULT_MOVE_INTERVAL);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public GameController(Grid grid, GameView gameView) {
        this.grid = grid;
        this.gameView = gameView;
        this.running = true;
        this.notObserved = true;
    }
}
