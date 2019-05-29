package pl.sda.javawwa18.tictactoe;

import org.assertj.core.api.Assertions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class TicTacToeTest {

    Board board;
    Player playerX;
    Player playerO;
    AIPlayer aiPlayer;

    @BeforeMethod   //odpowiada @Before w JUnit
    public void setUp() {
        board = new Board(Board.Sign.X);
        playerX = new Player(Board.Sign.X);
        playerO = new Player(Board.Sign.O);
    }

    @Test
    public void board_is_3_by_3() {
        Assertions.assertThat(board.getSigns().length % 9 == 0).isTrue();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void cannot_place_sign_outside_of_board() {
        board.placeSign(playerX, 10);
    }

    @Test
    public void can_place_sign_within_board() {
        board.placeSign(playerX, 4);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void cannot_place_sing_on_already_taken_square() {
        board.placeSign(playerX, 4);
        board.placeSign(playerO, 4);
        Assertions.assertThat(board.getSigns()[4]).isEqualTo(playerX.getPlayerSign());
    }

    @Test
    public void all_squares_for_initialized_board_are_nulls() {
        Assertions.assertThat(board.getSigns()).containsOnlyNulls();
    }

    @Test
    public void when_X_started_second_move_is_O() {
        board.placeSign(playerX, 4);
        board.placeSign(playerO, 8);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void fails_on_X_makes_2nd_move_in_turn() {
        board.placeSign(playerX, 4);
        board.placeSign(playerX, 8);
    }

    @Test
    public void starting_sign_starts_game() {
        board.placeSign(playerX, 4);
        Assertions.assertThat(board.getSigns()[4]).isEqualTo(playerX.getPlayerSign());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void non_starting_sign_cannot_start_game() {
        board.placeSign(playerO, 4);
    }

    @Test
    public void prints_correct_board_for_given_game_state() {
        board.placeSign(playerX, 4);
        board.placeSign(playerO, 8);
        board.placeSign(playerX, 0);
        board.placeSign(playerO, 2);

        board.printBoard();

        Assertions.assertThat(board.getPrintableBoard()).isEqualTo(
                "|X|1|O|\n|3|X|5|\n|6|7|O|"
        );
    }

    @Test
    public void converts_board_to_sequence() {
        board.placeSign(playerX, 4);
        board.placeSign(playerO, 8);
        board.placeSign(playerX, 0);
        board.placeSign(playerO, 2);

        Assertions.assertThat(board.convertBoardToSequence()).isEqualTo(
            "X.O.X...O"
        );
    }

    @Test
    public void X_wins_for_diagonal_sequence() {
        board.placeSign(playerX, 4);
        board.placeSign(playerO, 8);
        board.placeSign(playerX, 2);
        board.placeSign(playerO, 7);
        board.placeSign(playerX, 6);

        Assertions.assertThat(board.checkGameWon(playerX)).isTrue();
    }

    @Test
    public void O_wins_for_column_sequence() {
        board.placeSign(playerX, 4);
        board.placeSign(playerO, 8);
        board.placeSign(playerX, 0);
        board.placeSign(playerO, 2);
        board.placeSign(playerX, 6);
        board.placeSign(playerO, 5);

        Assertions.assertThat(board.checkGameWon(playerO)).isTrue();
    }

    @Test
    public void X_wins_for_row_sequence() {
        board.placeSign(playerX, 4);
        board.placeSign(playerO, 8);
        board.placeSign(playerX, 5);
        board.placeSign(playerO, 2);
        board.placeSign(playerX, 3);

        Assertions.assertThat(board.checkGameWon(playerX)).isTrue();
    }

    //test for AI game
    @Test
    public void AI_player_starts_game_first() {
        aiPlayer = new AIPlayer(Board.Sign.X);
        aiPlayer.makeMove(board);

        Assertions.assertThat(board.getSigns()[4]).isEqualTo(aiPlayer.getPlayerSign());
    }

    @Test
    public void first_AI_player_move_is_2nd() {
        aiPlayer = new AIPlayer(Board.Sign.O);
        board.placeSign(playerX, 4);
        int squareChoosen = aiPlayer.makeMove(board);

        Assertions.assertThat(squareChoosen).isIn(0, 2, 6, 8);
        Assertions.assertThat(board.getSigns()[squareChoosen]).isEqualTo(aiPlayer.getPlayerSign());
    }

    @DataProvider(name = "scoringSequences")
    public Object[][] scoreSequences() {
        return new Object[][] {
                {"SSS......", 3},
                {"...SSS...", 0},
                {"......SSS", 1},
                {"S..S..S..", 4},
                {".S..S..S.", 0},
                {"..S..S..S", 0},
                {"S...S...S", -3},
                {"..S.S.S..", 1}
        };
    }

    @Test(dataProvider = "scoringSequences")
    public void scores_sequence(String sequenceToEvaluate, int expectedScore) {
        aiPlayer = new AIPlayer(Board.Sign.O);
        int actualScore = aiPlayer.scoreGivenSequence(sequenceToEvaluate, "....X.O.X");
        Assertions.assertThat(actualScore).isEqualTo(expectedScore);
    }

    @Test
    public void sorts_sequences_by_score() {
        aiPlayer = new AIPlayer(Board.Sign.O);
        List<String> sequencesSortedByScore = Arrays.asList(Board.winningSequences);
        aiPlayer.sortSequencesByScore(sequencesSortedByScore, "....X.O.X");

        Assertions.assertThat(sequencesSortedByScore.get(0)).isEqualTo("S...S...S");
        Assertions.assertThat(sequencesSortedByScore.get(sequencesSortedByScore.size()-1)).isEqualTo("S..S..S..");
    }

    @Test
    public void makes_move_by_taking_enemies_square() {
        aiPlayer = new AIPlayer(Board.Sign.O);
        board.placeSign(playerX, 4);
        board.placeSign(aiPlayer, 6);
        aiPlayer.isFirstMove = false;
        board.placeSign(playerX, 8);
        int choosenSquare = aiPlayer.makeMove(board);

        Assertions.assertThat(choosenSquare).isIn(0);
        Assertions.assertThat(board.getSigns()[choosenSquare]).isEqualTo(aiPlayer.getPlayerSign());
    }

}
