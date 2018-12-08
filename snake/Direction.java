/**
 * 贪吃蛇前进的方向
 */
public enum Direction {
    UP(0),
    RIGHT(1),
    DOWN(2),
    LEFT(3);

    // 成员变量
    private final int directionCode;

    // 成员方法
    public int directionCode() {
        return directionCode;
    }

    public boolean compatibleWith(Direction newDirection) {
        switch (newDirection) {
            case UP:
                // 向上移动
                return directionCode != DOWN.directionCode;
            case RIGHT:
                // 向右移动
                return directionCode != LEFT.directionCode;
            case DOWN:
                // 向下移动
                return directionCode != UP.directionCode;
            case LEFT:
                // 向左移动
                return directionCode != RIGHT.directionCode;
            default:
                return false;
        }
    }

    // 构造函数
    Direction(int directionCode) {
        this.directionCode = directionCode;
    }
}
