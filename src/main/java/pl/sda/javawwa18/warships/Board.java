package pl.sda.javawwa18.warships;

import com.google.common.base.Preconditions;

import java.awt.Point;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class Board {
    private GameObject[][] board;
    private int x;
    private int y;

    public Board() {
        this(10, 10);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Board(int x, int y) {
        board = new GameObject[x][y];
        for (GameObject[] gameObjects : board) {
            Arrays.fill(gameObjects, GameObject.EMPTY_SPACE);
        }
        this.x = x;
        this.y = y;
    }

    private void rangeCheck(int x, int y) {
        Preconditions.checkArgument(x >= 0 && x <= this.x, String.format("x has to be in range (%d, %d)", 0, this.x));
        Preconditions.checkArgument(y >= 0 && y <= this.y, String.format("y has to be in range (%d, %d)", 0, this.y));
    }

    public void setObject(int x, int y, GameObject object) {
        rangeCheck(x, y);
        board[x][y] = object;
    }

    public GameObject getObject(int x, int y) {
        rangeCheck(x, y);
        return board[x][y];
    }

    public List<Point> getAvailablePointsWithinDistance(Point begin, int length) {
        int[] rowOffsets = {0, 0, 1, -1};
        int[] columnOffsets = {-1, 1, 0, 0};
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Point point = new Point(begin.x + length * rowOffsets[i], begin.y + length * columnOffsets[i]);
            if (isInRange(point) && isNotAdjacentToAShip(point)) {
                points.add(point);
            }
        }
        return points;
    }

    public boolean isNotAdjacentToAShip(Point point) {
        List<Point> pointList = getAdjacentPoints(point);
        for (Point p : pointList) {
            if (getObject(p.x, p.y).equals(GameObject.HIDDEN_SHIP)) {
                return false;
            }
        }
        return true;
    }

    public boolean isInRange(Point point) {
        return point.x >= 0 && point.x < this.x && point.y >= 0 && point.y < this.y;
    }

    public List<Point> getAdjacentPoints(Point point) {
        int[] rowOffsets = {-1, -1, -1, 0, 0, 0, 1, 1, 1};
        int[] columnOffsets = {-1, 0, 1, -1, 0, 1, -1, 0, 1};
        List<Point> adjacentPoints = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            Point adjacent = new Point(point.x + rowOffsets[i], point.y + columnOffsets[i]);
            if (isInRange(adjacent)) {
                adjacentPoints.add(adjacent);
            }
        }
        return adjacentPoints;
    }

    public boolean shipIsSunken(int x, int y) {
        if (getObject(x, y) != GameObject.HIT_SHIP) {
            return false;
        }
        Boolean[][] visited = new Boolean[this.x][this.y];
        for (Boolean[] booleans : visited) {
            Arrays.fill(booleans, false);
        }
        visited[x][y] = true;
        List<Point> availablePointsWithinDistance = getAvailablePointsWithinDistance(new Point(x, y), 1);
        clearEmptySpacesAndPointsAlreadyVisited(availablePointsWithinDistance, visited);
        Queue<Point> points = new LinkedList<>(availablePointsWithinDistance);
        while (!points.isEmpty()) {
            Point point = points.remove();
            if (getObject(point.x, point.y) == GameObject.HIDDEN_SHIP) {
                return false;
            }
            List<Point> adjacentPoints = getAvailablePointsWithinDistance(point, 1);
            clearEmptySpacesAndPointsAlreadyVisited(adjacentPoints, visited);
            points.addAll(adjacentPoints);
            visited[point.x][point.y] = true;
        }
        return true;
    }

    private void clearEmptySpacesAndPointsAlreadyVisited(List<Point> adjacentPoints, Boolean[][] visited) {
        Iterator<Point> iterator = adjacentPoints.iterator();
        while (iterator.hasNext()) {
            Point point = iterator.next();
            if (visited[point.x][point.y]) {
                iterator.remove();
            }
            GameObject object = getObject(point.x, point.y);
            if (object == GameObject.EMPTY_SPACE || object == GameObject.HIT_SPACE) {
                iterator.remove();
            }
        }
    }

    public String getPrintableBoard() {
        StringBuilder stringBuilder = new StringBuilder("  ");
        for (int i = 0; i < x; i++) {
            stringBuilder.append(i);
        }
        stringBuilder.append("\n");
        for (int i = 0; i < board.length; i++) {
            stringBuilder.append(i).append(" ");
            for (int j = 0; j < board[i].length; j++) {
                stringBuilder.append(board[j][i]);
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public void printBoard() {
        System.out.println(getPrintableBoard());
    }
}
