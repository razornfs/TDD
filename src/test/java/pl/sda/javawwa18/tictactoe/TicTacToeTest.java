package pl.sda.javawwa18.tictactoe;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

public class TicTacToeTest {

    @Test
    public void board_is_3_by_3() {
        Board board = new Board();
        Assertions.assertThat(board.getSigns().length % 9 == 0).isTrue();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void cannot_place_sign_outside_of_board() {
        Board board = new Board();
        Player playerX = new Player(Board.Sign.X);
        board.placeSign(playerX, 10);
    }

    @Test
    public void can_place_sign_within_board() {

    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void cannot_place_sing_on_already_taken_square() {

    }

    @Test
    public void all_squares_for_initialized_board_are_same() {

    }

    @Test
    public void when_X_started_second_move_is_O() {

    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void fails_on_X_makes_2nd_move_in_turn() {

    }

    @Test
    public void starting_sign_starts_game() {

    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void non_starting_sign_cannot_start_game() {

    }

    @Test
    public void prints_correct_board_for_given_game_state() {

    }

    //data provider?
    @Test
    public void board_knows_winning_sequences() {

    }

    @Test
    public void X_wins_for_diagonal_sequence() {

    }

    @Test
    public void O_wins_for_column_sequence() {

    }

    @Test
    public void X_wins_for_row_sequence() {

    }

}
