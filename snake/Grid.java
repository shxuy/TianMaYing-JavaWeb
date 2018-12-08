import java.util.LinkedList;
import java.util.Random;

public class Grid {
    public final boolean status[][];
    private final int width;
    private final int height;

    private Snake snake;
    private Node food;

    // 初始方向默认设置为向左
    private Direction snakeDirection = Direction.LEFT;

    public Snake getSnake() {
        return snake;
    }

    public Node getFood() {
        return food;
    }

    public Direction getSnakeDirection() {
        return snakeDirection;
    }

    private Snake initSnake() {
        snake = new Snake();

        // 设置Snake的Body
        // 贪吃蛇的长度为棋盘宽度的三分之一
        // 贪吃蛇为水平放置，即包含的所有Node的Y坐标相同，Y坐标为棋盘垂直中间位置（即height / 2），
        // 最左边的X为棋盘水平中间位置（即width / 2）
        // 更新棋盘覆盖状态
        for (int x = width / 2; x <= width * 5 / 6; x++) {
            snake.addTail(new Node(x, height / 2));
            status[x][height / 2] = true;
        }

        return snake;
    }

    public Node createFood() {
        int x, y = 0;
        // 使用Random设置x和y
        Random random = new Random();
        // 生成的X坐标和Y坐标必须在有效的范围之内，不能超过棋盘大小
        // 食物的位置不能和贪吃蛇的位置重叠
        int position = random.nextInt(width * height - snake.getBody().size());
        outer:
        for (x = 0; x < width; x++) {
            for (y = 0; y < height; y++) {
                if (position == 0)
                    break outer;
                if (!status[x][y])
                    position--;
            }
        }
        food = new Node(x, y);
        return food;
    }

    public boolean nextRound() {
        // 按当前方向移动贪吃蛇
        Node oldTail = snake.move(snakeDirection);
        Node head = snake.getHead();

        if (headIsValid(oldTail)) { // 头部的位置是否有效
            if (food.getX() == head.getX() && food.getY() == head.getY()) { // 头部原来是食物
                // 把原来move操作时删除的尾部添加回来
                snake.addTail(oldTail);
                // 创建一个新的食物
                createFood();
            } else {
                status[oldTail.getX()][oldTail.getY()] = false;
            }
            // 更新棋盘状态并返回游戏是否结束的标志
            status[head.getX()][head.getY()] = true;
            return true;
        }
        return false;
    }

    private boolean headIsValid(Node oldTail) {
        Node head = snake.getHead();
        LinkedList<Node> body = snake.getBody();
        // 头部位置无效有两种情况：
        // 1. 碰到边界
        if (head.getX() < 0 || head.getX() >= width || head.getY() < 0 || head.getY() >= height)
            return false;
        // 2. 碰到自己
        if (oldTail.getX() == head.getX() && oldTail.getY() == head.getY())
            return false;
        for (int i = 1; i < body.size(); i++) {
            Node current = body.get(i);
            if (current.getX() == head.getX() && current.getY() == head.getY())
                return false;
        }
        return true;
    }

    public void changeDirection(Direction newDirection) {
        if (snakeDirection.compatibleWith(newDirection))
            snakeDirection = newDirection;
    }

    public Grid(int width, int height) {
        this.width = width;
        this.height = height;
        status = new boolean[width][height];

        initSnake();
        createFood();
    }
}
