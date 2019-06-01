package pl.sda.javawwa18.warships;

import java.awt.*;
import java.util.List;
import java.util.Scanner;

public class WarshipGame {

    private static Scanner scanner;
    private static boolean gameIsWon = false;
    private static Player winningPlayer;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        System.out.println("=== Elo ===");
        Board p1Board = new Board();
        Board p2Board = new Board();

        int[] p1availableShips = {0, 4, 3, 2, 1};
        int[] p2availableShips = {0, 4, 3, 2, 1};

        System.out.println("Podaj imię gracza 1:");
        String p1Name = scanner.nextLine();

        System.out.println("Podaj imię gracza 2:");
        String p2Name = scanner.nextLine();

        Player p1 = new Player(p1Board, p2Board, p1availableShips, p1Name);
        Player p2 = new Player(p2Board, p1Board, p2availableShips, p2Name);

        placeShips(p1);
        placeShips(p2);

        GameObject.HIDDEN_SHIP.setSymbol(" ");

        while (!gameIsWon) {
            move(p1);
            move(p2);
        }
        System.out.println(winningPlayer.getName() + " wygrał!");
    }

    private static void placeShips(Player player) {
        while (player.hasAvailableShips()) {
            player.getPlayerBoard().printBoard();
            int size = getShipSize(player);
            Point startPoint = getShipStartPoint(player, size);
            Point endPoint = getShipEndPoint(player, startPoint, size);
            player.placeShip(startPoint, endPoint);
        }
    }

    private static void move(Player player) {
        while (true) {
            player.getPlayerBoard().printBoard();
            player.getOpponentBoard().printBoard();
            System.out.println("Podaj współrzędne pola gdzie chcesz strzelić");
            Point playerHit;
            do {
                playerHit = getPointFromUser();
            } while (!player.getPlayerBoard().isInRange(playerHit));
            boolean hit = player.hit(playerHit);
            if (!hit) {
                System.out.println("Nie trafiłeś");
                break;
            }
            if (player.getOpponentBoard().shipIsSunken(playerHit)) {
                System.out.println("Trafiony i zatopiony");
                if (player.hasWon()) {
                    gameIsWon = true;
                    winningPlayer = player;
                    return;
                }
            } else {
                System.out.println("Trafiony");
            }
        }
    }

    private static int getShipSize(Player player) {
        System.out.println(String.format("%s, jaki statek chcesz postawić? Podaj liczbę odpowiadającą długości statku",
                                         player.getName()));
        int[] availableShips = player.getAvailableShips();
        for (int i = 0; i < availableShips.length; i++) {
            int availableShip = availableShips[i];
            if (availableShip > 0) {
                System.out.println(String.format("%d-masztowiec (pozostało: %d)", i, availableShip));
            }
        }

        int playerShipSizeChoice;
        do {
            playerShipSizeChoice = scanner.nextInt();
        } while (!isValidShipSizeChoice(playerShipSizeChoice, availableShips));
        return playerShipSizeChoice;
    }

    private static Point getShipStartPoint(Player player, int size) {
        System.out.println(String.format("%s, podaj współrzędne początku statku:", player.getName()));
        Point playerShipStartPointChoice;
        do {
            playerShipStartPointChoice = getPointFromUser();
        } while (!isValidShipStartPointChoice(playerShipStartPointChoice, player.getPlayerBoard(), size));
        return playerShipStartPointChoice;
    }

    private static Point getShipEndPoint(Player player, Point startPoint, int size) {
        if (size == 1) {
            return startPoint;
        }
        List<Point> pointsWithinDistance =
                player.getPlayerBoard().getAvailablePointsWithinDistance(startPoint, size - 1);
        if (pointsWithinDistance.size() == 1) {
            return pointsWithinDistance.get(0);
        }
        System.out.println(String.format("%s, wybierz współrzędne końca statku:", player.getName()));
        for (int i = 0; i < pointsWithinDistance.size(); i++) {
            Point point = pointsWithinDistance.get(i);
            System.out.println(String.format("%d. (%d, %d)", i, point.x, point.y));
        }
        int playerChoice;
        do {
            playerChoice = scanner.nextInt();
        } while (playerChoice < 0 || playerChoice >= pointsWithinDistance.size());
        return pointsWithinDistance.get(playerChoice);
    }

    private static Point getPointFromUser() {
        System.out.print("X: ");
        int x = scanner.nextInt();
        System.out.print("Y: ");
        int y = scanner.nextInt();
        return new Point(x, y);
    }

    private static boolean isValidShipSizeChoice(int playerShipSizeChoice, int[] availableShips) {
        return playerShipSizeChoice > 0 && playerShipSizeChoice < availableShips.length &&
               availableShips[playerShipSizeChoice] > 0;
    }

    private static boolean isValidShipStartPointChoice(Point playerShipStartPointChoice,
                                                       Board playerBoard, int size) {
        if (!playerBoard.isInRange(playerShipStartPointChoice)) {
            return false;
        }
        List<Point> pointsAdjacentToStartPoint = playerBoard.getAdjacentPoints(playerShipStartPointChoice);
        for (Point adjacentPoint : pointsAdjacentToStartPoint) {
            if (playerBoard.getObject(adjacentPoint.x, adjacentPoint.y) == GameObject.HIDDEN_SHIP) {
                return false;
            }
        }
        List<Point> pointsWithinDistance =
                playerBoard.getAvailablePointsWithinDistance(playerShipStartPointChoice, size - 1);
        for (Point point : pointsWithinDistance) {
            if (playerBoard.isNotAdjacentToAShip(point)) {
                return true;
            }
        }
        return false;
    }
}
