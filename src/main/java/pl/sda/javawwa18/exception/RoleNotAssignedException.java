package pl.sda.javawwa18.exception;

import pl.sda.javawwa18.Customer;

public class RoleNotAssignedException extends Exception {
    public RoleNotAssignedException(Customer.Role role) {
        super(String.format
                ("Nie mozna wykonac operacji ze wzgledu na brak uprawnienia %s",
                        role));
    }
}
