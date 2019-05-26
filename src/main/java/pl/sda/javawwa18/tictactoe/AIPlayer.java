package pl.sda.javawwa18.tictactoe;

import java.util.*;

public class AIPlayer extends Player {

    boolean isFirstMove = true;

    public AIPlayer(Board.Sign sign) {
        super(sign);
    }

    public int makeMove(final Board board) {
        if(isFirstMove)
            return makeFirstMove(board);
        else {
            return makeBestMove(board);
        }
    }

    private int makeBestMove(final Board board) {
        List<String> sequencesSortedByScore = Arrays.asList(Board.winningSequences);
        final String currentBoardSequence = board.convertBoardToSequence();
        //sort sequences by score
        sortSequencesByScore(sequencesSortedByScore, currentBoardSequence);
        //best strategy for AI player
        final String bestStrategy = sequencesSortedByScore.get(sequencesSortedByScore.size()-1);
        //best strategy for other player
        final String enemyStrategy = sequencesSortedByScore.get(0);
        //try to place sing on a square which makes trouble for enemy
        final int choosenSquare = chooseSquareToMakeTroubleForEnemy(currentBoardSequence, bestStrategy, enemyStrategy);
        board.placeSign(this, choosenSquare);
        return choosenSquare;
    }

    void sortSequencesByScore(final List<String> sequencesSortedByScore, final String currentBoardSequence) {
        Collections.sort(sequencesSortedByScore, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return scoreGivenSequence(o1, currentBoardSequence) - scoreGivenSequence(o2, currentBoardSequence);
            }
        });
    }

    //szukamy punkty styku miedzy nasza strategia, a strategia wroga
    private int chooseSquareToMakeTroubleForEnemy(final String currentBoardSequence, final String ourStrategy, final String enemyStrategy) {
        List<Integer> possibleSquares = new ArrayList<>();
        for(int i = 0; i < ourStrategy.length(); i++) {
            if(ourStrategy.charAt(i) == 'S') {
                if(currentBoardSequence.charAt(i) == '.') {
                    possibleSquares.add(i);
                }
            }
        }
        //czy ktoras z mozliwych pozycji jest w intencji do zajecia przez wroga
        for(Integer square : possibleSquares) {
            if(enemyStrategy.charAt(square) == 'S')
                return square;
        }
        //jesli nie - bierzemy z brzegu mozliwa pozycje
        return possibleSquares.get(0);
    }

    int scoreGivenSequence(String sequence, String boardSequence) {
        int score = 0;
        for(int i = 0; i < sequence.length(); i++) {
            if(sequence.charAt(i) == 'S') {
                if(boardSequence.charAt(i) == this.getPlayerSign().name().charAt(0)) {
                    score += 2;
                }
                else if(boardSequence.charAt(i) == this.getPlayerSign().other().name().charAt(0)) {
                    score -= 2;
                }
                else {  //tam jest '.'
                    score += 1;
                }
            }
        }
        return score;
    }

    private int makeFirstMove(final Board board) {
        isFirstMove = false;

        if(board.getSigns()[4] == null) {
            board.placeSign(this, 4);
            return 4;
        }
        else {
            double random = Math.random();
            if(random < 0.25) {
                board.placeSign(this, 0);
                return 0;
            }
            else if(random >= 0.25 && random < 0.5) {
                board.placeSign(this, 2);
                return 2;
            }
            else if(random >= 0.5 && random < 0.75) {
                board.placeSign(this, 6);
                return 6;
            }
            else {
                board.placeSign(this, 8);
                return 8;
            }
        }
    }



}
