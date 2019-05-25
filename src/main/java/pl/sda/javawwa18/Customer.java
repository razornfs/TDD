package pl.sda.javawwa18;

import pl.sda.javawwa18.exception.RoleNotAssignedException;

import java.util.*;

/**
 * Customer reprezentuje klienta w naszej aplikacji
 * Klient po zalogowaniu sie, otrzymuje okreslone role klienckie
 * Role okreslaja, co klient moze zrobic
 */
public class Customer {

    Set<Role> roles = new HashSet<>();
    final String UID;

    public Customer(String UID) {
        this.UID = UID;
    }

    public void login() {
        roles = getRoles(UID);
    }

    public void placeOrder() throws RoleNotAssignedException {
        if(roles.contains(Role.CAN_PLACE_ORDER)) {
            System.out.println("Zlozono zamowienie");
        }
        else {
            throw new RoleNotAssignedException(Role.CAN_PLACE_ORDER);
        }
    }

    public void simulateOrder() throws RoleNotAssignedException {
        if(roles.contains(Role.CAN_SIMULATE_ORDER)) {
            System.out.println("Zamowienie zaktualizowane o dane ERP");
        }
        else {
            throw new RoleNotAssignedException(Role.CAN_SIMULATE_ORDER);
        }
    }

    public void viewPrices() throws RoleNotAssignedException {
        if(roles.contains(Role.CAN_VIEW_PRICES)) {
            System.out.println("Wyswietlono ceny produktow w koszyku");
        }
        else {
            throw new RoleNotAssignedException(Role.CAN_VIEW_PRICES);
        }
    }

    public enum Role {
        CAN_PLACE_ORDER,
        CAN_SIMULATE_ORDER,
        CAN_VIEW_PRICES
    }

    //'service' part below
    public static Set<Role> getRoles(final String UID) {
        return uidToRolesMap.get(UID);
    }

    private static Map<String, Set<Role>> uidToRolesMap = new HashMap<>();
    //blok statycznej inicjalizacji - wykona sie przed utworzeniem instancji klasy
    {
        uidToRolesMap.put("007", Collections.EMPTY_SET);
        uidToRolesMap.put("090", new HashSet<>(Arrays.asList(
                Role.CAN_PLACE_ORDER,
                Role.CAN_SIMULATE_ORDER,
                Role.CAN_VIEW_PRICES)));
        uidToRolesMap.put("200", new HashSet<>(Arrays.asList(
                Role.CAN_VIEW_PRICES)));
    }
}
