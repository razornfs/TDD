package pl.sda.javawwa18.tictactoe;

import com.google.common.base.Preconditions;

public class Board {

    private Sign[] signs = new Sign[9];
    private Sign currentSign;
    public static String[] winningSequences = {
            "SSS......", "...SSS...", "......SSS",   //winning rows
            "S..S..S..", ".S..S..S.", "..S..S..S",  //winning cols
            "S...S...S", "..S.S.S.."    //diagonals
    };

    public Board(Sign currentSign) {
        this.currentSign = currentSign;
    }

    public void placeSign(Player player, int square) {
        //argument opisuje to JAKIE WARUNKI SA POPRAWNE
        Preconditions.checkArgument(square >= 0 && square < 9, "Cannot place sign outside of board.");
        Preconditions.checkArgument(signs[square] == null, "Cannot place sign on already taken square.");
/*        Preconditions.checkArgument(player.getPlayerSign().equals(currentSign),
                                    String.format("Current turn is for sign %s",
                                                  player.getPlayerSign().other().name()));*/

        signs[square] = player.getPlayerSign();
        currentSign = player.getPlayerSign().other();
    }

    String convertBoardToSequence() {
        StringBuilder sequenceBuilder = new StringBuilder();
        for (Sign s : signs) {
            if (s != null) {
                sequenceBuilder.append(s.name());
            } else {
                sequenceBuilder.append(".");
            }
        }
        return sequenceBuilder.toString();
    }

    public void printBoard() {
        System.out.println(getPrintableBoard());
    }

    String getPrintableBoard() {
        //jesli tworzymy Stringa zlozonego z kilku+ fragmentow, optymalnie jest uzyc StringBuilder
        StringBuilder boardBuilder = new StringBuilder();

        //row * 3 + col
        //0 + 0 -> 0
        //0 + 1 -> 1
        //0 + 2 -> 2

        //3 + 0 -> 3
        //3 + 1 -> 4

        //6 + 0 -> 6
        //6 + 2 -> 8
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                boardBuilder.append("|");
                Sign signOnGivenSquare = signs[row * 3 + col];
                if (signOnGivenSquare != null)
                    boardBuilder.append(signOnGivenSquare.name());
                else
                    boardBuilder.append(row * 3 + col);
            }
            boardBuilder.append("|");
            if (row != 2)
                boardBuilder.append("\n");
        }

        return boardBuilder.toString();
    }

    public boolean checkGameWon(Player player) {
        final String convertedBoard = convertBoardToSequence();
        for (String winningSequence : winningSequences) {
            int winningCharsCount = 0;
            for (int i = 0; i < winningSequence.length(); i++) {
                if (winningSequence.charAt(i) == 'S') {
                    if (convertedBoard.charAt(i) == player.getPlayerSign().name().charAt(0)) {
                        winningCharsCount++;
                    } else {
                        break;
                    }
                }
            }
            if (winningCharsCount == 3)
                return true;
        }
        return false;
    }

    public boolean checkGameWon(Board.Sign sign) {
        return checkGameWon(new Player(sign));
    }

    public boolean isFull() {
        for (Sign sign : signs) {
            if (sign == null) {
                return false;
            }
        }
        return true;
    }

    public enum Sign {
        X {
            @Override
            public Sign other() {
                return O;
            }
        },
        O {
            @Override
            public Sign other() {
                return X;
            }
        };

        public abstract Sign other();
    }

    public Sign[] getSigns() {
        return signs;
    }

    public Sign getCurrentSign() {
        return currentSign;
    }
}
