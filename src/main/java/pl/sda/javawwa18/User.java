package pl.sda.javawwa18;

import java.util.Arrays;
import java.util.List;

public class User {

    int age;
    private String name;

    //package-private
    void sayHello() {

    }

    public double getAvgScore() {
        List<Double> scores = getScores();
        double scoresSum = 0.0;
        for(Double score : scores)
            scoresSum += score;
        return scoresSum/scores.size();
    }

    List<Double> getScores() {
        return Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0);
    }

}
