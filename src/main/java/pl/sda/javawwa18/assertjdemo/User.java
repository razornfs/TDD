package pl.sda.javawwa18.assertjdemo;

import pl.sda.javawwa18.exception.StatusChangeNotPossibleException;

import java.time.LocalDateTime;

public class User {

    private Status status;
    private LocalDateTime registeredDate;
    private LocalDateTime lastLoggedIn;
    private String nickname;

    public User(final String nickname) {
        this.status = Status.INITIALIZED;
        this.registeredDate = LocalDateTime.now();
        this.nickname = nickname;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) throws StatusChangeNotPossibleException {
        if(this.status.equals(Status.INITIALIZED) && status.equals(Status.DEACTIVATED))
            throw new StatusChangeNotPossibleException();
        if(this.status.equals(Status.DEACTIVATED) && status.equals(Status.INITIALIZED))
            throw new StatusChangeNotPossibleException();

        this.status = status;
    }

    public LocalDateTime getRegisteredDate() {
        return registeredDate;
    }

    public void login() {
        lastLoggedIn = LocalDateTime.now();
    }

    public LocalDateTime getLastLoggedIn() {
        return lastLoggedIn;
    }

    public void printLastLoggedIn() {
        System.out.println(getLastLoggedInText());
    }

    String getLastLoggedInText() {
        return String.format("%s@%s", nickname, lastLoggedIn);
    }

    public enum Status {
        INITIALIZED,
        ACTIVATED,
        DEACTIVATED
    }

}
