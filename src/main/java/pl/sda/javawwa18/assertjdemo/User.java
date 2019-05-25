package pl.sda.javawwa18.assertjdemo;

import pl.sda.javawwa18.exception.StatusChangeNotPossibleException;

public class User {

    private Status status;

    public User() {
        this.status = Status.INITIALIZED;
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

    public enum Status {
        INITIALIZED,
        ACTIVATED,
        DEACTIVATED
    }

}
