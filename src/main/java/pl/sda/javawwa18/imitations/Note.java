package pl.sda.javawwa18.imitations;

//Note - przyklad klasy niemutowalnej [ang. immutable class]
public class Note {

    private final String fullName;
    private final double score;

    //Metoda fabrykujaca [ang. factory method]
    public static Note of(final String fullName, final double score) {
        return new Note(fullName, score);
    }

    private Note(String fullName, double score) {
        this.fullName = fullName;
        this.score = score;
    }

    public String getFullName() {
        return fullName;
    }

    public double getScore() {
        return score;
    }
}
