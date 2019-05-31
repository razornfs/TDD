package pl.sda.javawwa18.tictactoe;

import org.assertj.core.api.Assertions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Ignore;
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
}
