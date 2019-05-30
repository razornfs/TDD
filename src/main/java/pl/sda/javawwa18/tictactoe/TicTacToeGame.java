package pl.sda.javawwa18.tictactoe;

import java.util.Scanner;

public class TicTacToeGame {

    public static void main(String[] args) {
        System.out.println("--- Tic Tac Toe Game ---");
        System.out.println("Wybierz liczbe graczy: [1] lub [2]");

        Scanner sc = new Scanner(System.in);
        int noOfPlayers = 0;
        do {
            noOfPlayers = sc.nextInt();
        }
        //regula De Moivre
        //NIE (lg = 1 V lg = 2) ---> NIE lg = 1 AND NIE lg = 2
        while (noOfPlayers != 1 && noOfPlayers != 2);

        if (noOfPlayers == 1) {
            singlePlayerGame(sc);
        } else {
            multiPlayerGame(sc);
        }
    }

    private static void multiPlayerGame(final Scanner sc) {
        Board.Sign startingSing = getStartingSign();
        System.out.println(String.format("Gre zaczyna gracz: %s", startingSing));
        Board board = new Board(startingSing);
        Player playerA = new Player(startingSing);
        Player playerB = new Player(startingSing.other());
        boolean isGameWon = false;
        Player currentPlayer = playerA;

        do {
            currentPlayer = getPlayerForSign(board.getCurrentSign(), playerA, playerB);
            board.placeSign(currentPlayer, getPlayerSquare(sc, currentPlayer, board));
            isGameWon = board.checkGameWon(currentPlayer);

            if (isGameWon) {
                board.printBoard();
                System.out.println(
                        String.format("Gratulacje, gre wygral gracz %s", currentPlayer.getPlayerSign().name()));
            }
        }
        while (!isGameWon);
    }

    private static void singlePlayerGame(final Scanner sc) {
        AIPlayer aiPlayer;
        Player player;
        Board board;
        if (Math.random() < 0.5) {
            aiPlayer = new AIPlayer(Board.Sign.X);
            player = new Player(Board.Sign.O);
        } else {
            aiPlayer = new AIPlayer(Board.Sign.O);
            player = new Player(Board.Sign.X);
        }
        Player currentPlayer;
        if (Math.random() < 0.5) {
            board = new Board(aiPlayer.getPlayerSign());
            currentPlayer = aiPlayer;
            System.out.println("Grę zaczyna komputer");
        } else {
            board = new Board(player.getPlayerSign());
            currentPlayer = player;
            System.out.println("Zaczynasz grę");
        }
        System.out.println("Grasz jako " + player.getPlayerSign());

        boolean isGameWon = false;

        while (true) {
            currentPlayer = getPlayerForSign(board.getCurrentSign(), aiPlayer, player);
            board.placeSign(currentPlayer, getPlayerSquare(sc, currentPlayer, board));
            isGameWon = board.checkGameWon(currentPlayer);

            if (isGameWon) {
                board.printBoard();
                if (currentPlayer instanceof AIPlayer) {
                    System.out.println("Wygrywa komputer");
                } else {
                    System.out.println("Gratulacje, wygrales gre!");
                }
                break;
            }
            if (board.isFull()) {
                board.printBoard();
                System.out.println("Remis");
                break;
            }
        }
    }

    private static Player getPlayerForSign(Board.Sign currentSign, Player playerA, Player playerB) {
        if (playerA.getPlayerSign().equals(currentSign))
            return playerA;
        else
            return playerB;
    }

    private static Board.Sign getStartingSign() {
        if (Math.random() > 0.5) {
            return Board.Sign.X;
        } else {
            return Board.Sign.O;
        }
    }

    private static int getPlayerSquare(final Scanner sc, Player player, Board board) {
        if (player instanceof AIPlayer) {
            return ((AIPlayer) player).getOptimalMove(board);
        }
        board.printBoard();
        System.out.println("Ktora pozycje chcesz zajac?");

        int square = 10;
        do {
            square = sc.nextInt();
        }
        while (square < 0 || square > 9);

        return square;
    }
}
