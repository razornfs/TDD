package pl.sda.javawwa18.fizzbuzztdd;

import org.assertj.core.api.Assertions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class FizzBuzzTDDTest {

    //testy parametryzowane
    //wielokrotne odpalenie metody testowej

    @DataProvider(name = "inputOutputForPlayMethod")
    public Object[][] getDividableBy3() {
        return new Object[][] {
                {3, FizzBuzzTDD.FBEnums.FIZZ.name()},
                {9, "FIZZ"},
                {15, "FIZZBUZZ"},
                {30, "FIZZBUZZ"},
                {10, "BUZZ"},
                {11, "11"}
        };
    }

    @Test(dataProvider = "inputOutputForPlayMethod")
    public void prints_correct_output_for_given_input(int input, String output) {
        Assertions.assertThat(FizzBuzzTDD.play(input))
                .isEqualTo(output);
    }

    @Test(invocationCount = 1000)
    public void prints_correct_output_for_random_input() {
        Object[] inputOutput = generate_random_input_and_output();
        System.out.println(String.format("%d -> %s", inputOutput[0], inputOutput[1]));
        Assertions.assertThat(FizzBuzzTDD.play((int)inputOutput[0]))
                .isEqualTo((String)inputOutput[1]);
    }

    private Object[] generate_random_input_and_output() {
        Object[] inputOutput = new Object[2];
        int randomNumber = (int)(Math.random() * 1000000);
        if(randomNumber < 500000)
            randomNumber *= -1;
        inputOutput[0] = randomNumber;
        if(randomNumber % 15 == 0)
            inputOutput[1] = "FIZZBUZZ";
        else if(randomNumber % 3 == 0)
            inputOutput[1] = "FIZZ";
        else if(randomNumber % 5 == 0)
            inputOutput[1] = "BUZZ";
        else
            inputOutput[1] = String.valueOf(randomNumber);

        return inputOutput;
    }

/*    @Test
    public void prints_buzz_for_dividable_by_5_and_not_by_3() {

    }

    @Test
    public void prints_fizzbuzz_for_dividable_by_3_and_5() {

    }

    @Test
    public void prints_given_number_for_other_cases() {

    }*/

}
