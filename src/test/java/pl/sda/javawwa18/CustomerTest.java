package pl.sda.javawwa18;

import org.junit.Test;
import pl.sda.javawwa18.exception.RoleNotAssignedException;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class CustomerTest {

    @Test
    public void user_gets_his_roles_on_login() {
        Customer c007 = new Customer("007");
        assertTrue(c007.roles.isEmpty());
        Customer c090 = new Customer("090");
        assertTrue(c090.roles.isEmpty());
        Customer c200 = new Customer("200");
        assertTrue(c200.roles.isEmpty());

        Set<Customer.Role> expectedRolesSet = new HashSet<>();
        c007.login();
        assertEquals(expectedRolesSet, c007.roles);

        expectedRolesSet.add(Customer.Role.CAN_VIEW_PRICES);
        c200.login();
        assertEquals(expectedRolesSet, c200.roles);

        expectedRolesSet.add(Customer.Role.CAN_PLACE_ORDER);
        expectedRolesSet.add(Customer.Role.CAN_SIMULATE_ORDER);
        c090.login();
        assertEquals(expectedRolesSet, c090.roles);
    }

    @Test
    public void non_existing_user_has_no_roles() {
        Customer c001 = new Customer("001");
        c001.login();
        assertNull(c001.roles);
    }

    @Test
    public void user_with_CAN_PLACE_ORDER_places_order() throws Exception {
        Customer c090 = new Customer("090");
        c090.login();
        c090.placeOrder();
    }

    @Test(expected = RoleNotAssignedException.class)
    public void user_without_CAN_PLACE_ORDER_cannot_place_order() throws Exception {
        Customer c007 = new Customer("007");
        c007.login();
        c007.placeOrder();
    }

    @Test
    public void user_with_CAN_SIMULATE_ORDER_simulates_order() throws Exception {
        Customer c090 = new Customer("090");
        c090.login();
        c090.simulateOrder();
    }

    @Test(expected = RoleNotAssignedException.class)
    public void user_without_CAN_SIMULATE_ORDER_cannot_simulate_order() throws Exception {
        Customer c007 = new Customer("007");
        c007.login();
        c007.simulateOrder();
    }

    @Test
    public void user_with_CAN_VIEW_PRICES_views_prices() throws Exception {
        Customer c200 = new Customer("200");
        c200.login();
        c200.viewPrices();
    }

    @Test(expected = RoleNotAssignedException.class)
    public void user_without_CAN_VIEW_PRICES_cannot_view_prices() throws Exception {
        Customer c007 = new Customer("007");
        c007.login();
        c007.viewPrices();
    }

}
