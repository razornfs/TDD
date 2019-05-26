package pl.sda.javawwa18.tictactoe;

public class Player {

    final Board.Sign playerSign;

    public Player(Board.Sign playerSign) {
        this.playerSign = playerSign;
    }

    public Board.Sign getPlayerSign() {
        return playerSign;
    }
}
