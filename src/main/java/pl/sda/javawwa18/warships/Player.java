package pl.sda.javawwa18.warships;

import java.awt.*;
import java.util.Arrays;

public class Player {

    private Board playerBoard;
    private Board opponentBoard;
    private int[] availableShips;
    private String name;

    public Player(Board playerBoard, Board opponentBoard, int[] availableShips, String name) {
        this.playerBoard = playerBoard;
        this.opponentBoard = opponentBoard;
        this.availableShips = availableShips;
        this.name = name;
    }

    public int[] getAvailableShips() {
        return availableShips;
    }

    public Board getPlayerBoard() {
        return playerBoard;
    }

    public Board getOpponentBoard() {
        return opponentBoard;
    }

    public String getName() {
        return name;
    }

    public boolean hit(Point point) {
        return hit(point.x, point.y);
    }

    public boolean hit(int x, int y) {
        GameObject object = opponentBoard.getObject(x, y);
        if (object.equals(GameObject.HIDDEN_SHIP)) {
            opponentBoard.setObject(x, y, GameObject.HIT_SHIP);
            return true;
        }
        if (object.equals(GameObject.EMPTY_SPACE)) {
            opponentBoard.setObject(x, y, GameObject.HIT_SPACE);
        }
        return false;
    }

    public boolean hasWon() {
        for (int i = 0; i < opponentBoard.getX(); i++) {
            for (int j = 0; j < opponentBoard.getY(); j++) {
                if (opponentBoard.getObject(i, j).equals(GameObject.HIDDEN_SHIP)) {
                    return false;
                }
            }
        }
        return true;
    }

    public void placeShip(Point start, Point end) {
        int distance = (int) (start.distance(end) + 1);
        if (availableShips[distance] <= 0) {
            throw new IllegalArgumentException("Nie ma takiego statku");
        }
        availableShips[distance]--;
        Point tmp = start;
        while (!tmp.equals(end)) {
            playerBoard.setObject(tmp.x, tmp.y, GameObject.HIDDEN_SHIP);
            tmp = PointUtils.next(tmp, end);
        }
        playerBoard.setObject(tmp.x, tmp.y, GameObject.HIDDEN_SHIP);
    }

    public boolean hasAvailableShips() {
        return Arrays.stream(availableShips).sum() > 0;
    }
}
