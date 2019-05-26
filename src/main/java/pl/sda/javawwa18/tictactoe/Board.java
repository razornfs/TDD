package pl.sda.javawwa18.tictactoe;

import com.google.common.base.Preconditions;

public class Board {

    private Sign[] signs = new Sign[9];

    public void placeSign(Player player, int square) {
        //argument opisuje to JAKIE WARUNKI SA POPRAWNE
        Preconditions.checkArgument(square >= 0 && square < 9, "Cannot place sign outside of board");
    }

    public enum Sign {
        X,
        O
    }

    public Sign[] getSigns() {
        return signs;
    }
}
