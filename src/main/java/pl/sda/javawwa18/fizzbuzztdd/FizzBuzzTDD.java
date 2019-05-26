package pl.sda.javawwa18.fizzbuzztdd;

public class FizzBuzzTDD {

    public static String play(int number) {
        if(number % 15 == 0)
            return FBEnums.FIZZBUZZ.name();
        else if(number % 3 == 0)
            return FBEnums.FIZZ.name();
        else if(number % 5 == 0)
            return FBEnums.BUZZ.name();

        return String.valueOf(number);
    }

    public enum FBEnums {
        FIZZ,
        BUZZ,
        FIZZBUZZ
    }
}
