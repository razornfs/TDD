package pl.sda.javawwa18.assertjdemo;

import org.assertj.core.api.Assertions;
import org.junit.Ignore;
import org.junit.Test;
import pl.sda.javawwa18.exception.StatusChangeNotPossibleException;

import java.time.LocalDateTime;

public class UserTest {

    //possible status: ACTIVATED, DEACTIVATED, INITIALIZED
    //INITIALIZED -> ACTIVATED -> DEACTIVATED
    //DEACTIVATED -> ACTIVATED

    @Test
    public void newly_created_user_has_INITIALIZED_status() {
        User user = new User("pw92");
        Assertions.assertThat(user.getStatus()).isEqualTo(User.Status.INITIALIZED);
    }

    @Test
    public void INITIALIZED_to_ACTIVATED() throws Exception {
        User user = new User("pw92");
        Assertions.assertThat(user.getStatus()).isEqualTo(User.Status.INITIALIZED);
        user.setStatus(User.Status.ACTIVATED);
        Assertions.assertThat(user.getStatus()).isEqualTo(User.Status.ACTIVATED);
    }

    @Test
    public void ACTIVATED_to_DEACTIVATED() throws Exception {
        User user = new User("pw92");
        user.setStatus(User.Status.ACTIVATED);
        Assertions.assertThat(user.getStatus()).isEqualTo(User.Status.ACTIVATED);
        user.setStatus(User.Status.DEACTIVATED);
        Assertions.assertThat(user.getStatus()).isEqualTo(User.Status.DEACTIVATED);
    }

    @Test
    public void DEACTIVATED_to_ACTIVATED() throws Exception {
        User user = new User("pw92");
        user.setStatus(User.Status.ACTIVATED);
        user.setStatus(User.Status.DEACTIVATED);
        Assertions.assertThat(user.getStatus()).isEqualTo(User.Status.DEACTIVATED);
        user.setStatus(User.Status.ACTIVATED);
        Assertions.assertThat(user.getStatus()).isEqualTo(User.Status.ACTIVATED);
    }

    @Test(expected = StatusChangeNotPossibleException.class)
    public void INITIALIZED_to_DEACTIVATED_not_possible() throws Exception {
        User user = new User("pw92");
        Assertions.assertThat(user.getStatus()).isEqualTo(User.Status.INITIALIZED);
        user.setStatus(User.Status.DEACTIVATED);
    }

    @Test(expected = StatusChangeNotPossibleException.class)
    public void DEACTIVATED_to_INITIALIZED_not_possible() throws Exception {
        User user = new User("pw92");
        user.setStatus(User.Status.ACTIVATED);
        user.setStatus(User.Status.DEACTIVATED);
        user.setStatus(User.Status.INITIALIZED);
    }

    //do zrobienia w domu - ponizej
    @Test
    public void register_date_before_now() {
        User user = new User("pw92");
        Assertions.assertThat(user.getRegisteredDate()).isBefore(LocalDateTime.now())
        .as("Data rejestracji uzytkownika nie moze byc pozniejsza niz 'teraz'.");
    }

    @Test
    public void last_logged_in_date_before_now() {
        User user = new User("pw92");
        user.login();
        Assertions.assertThat(user.getLastLoggedIn()).isBefore(LocalDateTime.now());
    }

    @Ignore
    @Test(expected = IllegalArgumentException.class)
    public void cannot_set_register_date_in_future() {
        //taka sytuacja nie moze wystapic - registeredDate jest ustawiany na
        //LocalDateTime.now w momencie tworzenia uzytkownika
    }

    @Ignore
    @Test(expected = IllegalArgumentException.class)
    public void cannot_set_last_logged_in_date_in_future() {
        //j.w. tylko ze w metodzie login()
    }

    //https://stackoverflow.com/questions/29038609/how-can-i-fake-the-date-returned-by-java-time-localdate
    //dla chetnych - zamockowac zwracany czas now i sprawdzic czy dobrze liczy 'odleglosc' pomiedzy czasami

    //AssertJ + assertion for String matches given RegEx
    //pattern to match: pw92@2019-05-26T09:20:50.364
    @Test
    public void print_last_logged_in_matches_given_pattern() {
        User user = new User("pw92");
        user.login();
        //user.printLastLoggedIn();
        Assertions.assertThat(user.getLastLoggedInText())
                .matches("([A-Za-z0-9])*@\\d\\d\\d\\d-\\d\\d-\\d\\dT\\d\\d:\\d\\d:\\d\\d.\\d\\d\\d");
    }

}
