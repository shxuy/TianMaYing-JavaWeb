import java.util.LinkedList;

public class Snake {
    private LinkedList<Node> body = new LinkedList<>();

    public Node eat(Node food) {
        // 如果food与头部相邻，则将food这个Node加入到body中，返回food
        // 否则不做任何操作，返回null
        if (isNeighbor(body.getFirst(), food)) {
            // 相邻情况下的处理
            body.addFirst(food);
            return food;
        } else {
            return null;
        }
    }

    private boolean isNeighbor(Node a, Node b) {
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY()) == 1;
    }

    public Node move(Direction direction) {
        // 根据方向更新贪吃蛇的body
        // 返回移动之前的尾部Node
        Node head = this.getHead();
        switch (direction) {
            case UP:
                // 向上移动
                body.addFirst(new Node(head.getX(), head.getY() - 1));
                break;
            case RIGHT:
                // 向右移动
                body.addFirst(new Node(head.getX() + 1, head.getY()));
                break;
            case DOWN:
                // 向下移动
                body.addFirst(new Node(head.getX(), head.getY() + 1));
                break;
            case LEFT:
                // 向左移动
                body.addFirst(new Node(head.getX() - 1, head.getY()));
                break;
        }
        Node tail = body.get(body.size() - 1);
        body.remove(body.size() - 1);
        return tail;
    }

    public Node getHead() {
        return body.getFirst();
    }

    public Node addTail(Node area) {
        this.body.addLast(area);
        return area;
    }

    public LinkedList<Node> getBody() {
        return body;
    }
}
