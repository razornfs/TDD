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
        while(noOfPlayers != 1 && noOfPlayers != 2);

        if(noOfPlayers == 1) {
            singlePlayerGame(sc);
        }
        else {
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
            board.printBoard();
            currentPlayer = getPlayerForSign(board.getCurrentSign(), playerA, playerB);
            board.placeSign(currentPlayer, getPlayerSquare(sc));
            isGameWon = board.checkGameWon(currentPlayer);

            if(isGameWon)
                board.printBoard();
                System.out.println(String.format("Gratulacje, gre wygral gracz %s", currentPlayer.getPlayerSign().name()));
        }
        while(!isGameWon);
    }

    private static void singlePlayerGame(final Scanner sc) {
        Player player = new Player(getSignForChar(getPlayerSignChar(sc)));
        AIPlayer aiPlayer = new AIPlayer(player.getPlayerSign().other());

        Board.Sign startingSing = getStartingSign();
        System.out.println(String.format("Gre zaczyna gracz: %s", startingSing));
        Board board = new Board(startingSing);

        Player currentPlayer = player;
        boolean isGameWon = false;

        do {
            board.printBoard();
            currentPlayer = getPlayerForSign(board.getCurrentSign(), player, aiPlayer);

            //jezeli komputer
            if(currentPlayer instanceof AIPlayer) {
                aiPlayer.makeMove(board);
            }
            //jezeli czlowiek
            else {
                board.placeSign(currentPlayer, getPlayerSquare(sc));
            }

            isGameWon = board.checkGameWon(currentPlayer);

            if(isGameWon) {
                board.printBoard();
                System.out.println(String.format("Gratulacje, gre wygral gracz %s", currentPlayer.getPlayerSign().name()));
            }
        }
        while(!isGameWon);
    }

    private static Player getPlayerForSign(Board.Sign currentSign, Player playerA, Player playerB) {
        if(playerA.getPlayerSign().equals(currentSign))
            return playerA;
        else
            return playerB;
    }

    private static Board.Sign getSignForChar(char c) {
        if(c == 'O')
            return Board.Sign.O;

        return Board.Sign.X;
    }

    private static Board.Sign getStartingSign() {
        if(Math.random() > 0.5) {
            return Board.Sign.X;
        }
        else {
            return Board.Sign.O;
        }
    }

    private static int getPlayerSquare(final Scanner sc) {
        System.out.println("Ktora pozycje chcesz zajac?");

        int square = 10;
        do {
            square = sc.nextInt();
        }
        while(square < 0 || square > 9);

        return square;
    }

    private static char getPlayerSignChar(Scanner sc) {
        System.out.println("Jakim znakiem chcesz grac? [X] czy [O]?");
        sc = new Scanner(System.in);

        char signChar = 'a';
        do {
            signChar = sc.nextLine().charAt(0);
        }
        while(signChar != 'O' && signChar != 'X');

        return signChar;
    }

}
