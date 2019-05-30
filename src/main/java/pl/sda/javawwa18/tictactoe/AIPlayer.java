package pl.sda.javawwa18.tictactoe;

import java.util.Random;

class AIPlayer extends Player {

    private Board board;

    public AIPlayer(Board.Sign playerSign) {
        super(playerSign);
    }

    int getOptimalMove(Board board) {
        this.board = board;
        int bestScore = Integer.MIN_VALUE;
        int bestMove = -1;
        int count = 0;
        Random random = new Random();
        for (int i = 0; i < 9; i++) {
            if (board.getSigns()[i] == null) {
                board.placeSign(this, i);
                int currentScore = minimax(0, false);
                board.getSigns()[i] = null;
                if (currentScore > bestScore) {
                    count = 0;
                    bestScore = currentScore;
                    bestMove = i;
                } else if (currentScore == bestScore) {
                    count++;
                    if (count == 1) {
                        bestScore = currentScore;
                        bestMove = i;
                    } else {
                        int rand = random.nextInt(count);
                        if (rand == 0) {
                            bestScore = currentScore;
                            bestMove = i;
                        }
                    }
                }
            }
        }
        return bestMove;
    }

    private int minimax(int depth, boolean isMaximizing) {
        int score = evaluate();
        if (score == 10) {
            return score - depth;
        }
        if (score == -10) {
            return score + depth;
        }
        if (board.isFull()) {
            return 0;
        }
        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 9; i++) {
                if (board.getSigns()[i] == null) {
                    board.placeSign(this, i);
                    bestScore = Math.max(bestScore, minimax(depth + 1, false));
                    board.getSigns()[i] = null;
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 9; i++) {
                if (board.getSigns()[i] == null) {
                    board.placeSign(new Player(opponent(playerSign)), i);
                    bestScore = Math.min(bestScore, minimax(depth + 1, true));
                    board.getSigns()[i] = null;
                }
            }
            return bestScore;
        }
    }

    private int evaluate() {
        if (playerSign != Board.Sign.X && playerSign != Board.Sign.O) {
            throw new IllegalArgumentException("Invalid player");
        }
        if (board.checkGameWon(playerSign)) {
            return 10;
        }
        if (board.checkGameWon(opponent(playerSign))) {
            return -10;
        }
        return 0;
    }

    private Board.Sign opponent(Board.Sign tile) {
        if (tile == Board.Sign.X) {
            return Board.Sign.O;
        }
        if (tile == Board.Sign.O) {
            return Board.Sign.X;
        }
        throw new IllegalArgumentException();
    }
}