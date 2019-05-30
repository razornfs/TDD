package pl.sda.javawwa18.warships;

public enum GameObject {

    EMPTY_SPACE(" "), HIT_SPACE("·"), HIDDEN_SHIP("O"), HIT_SHIP("X");

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    private String symbol;

    GameObject(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
