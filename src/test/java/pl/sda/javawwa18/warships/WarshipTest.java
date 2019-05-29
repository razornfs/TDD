package pl.sda.javawwa18.warships;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class WarshipTest {

    //1 -
    //2 -
    //3 -
    //4 - FourMastedShip

    @Test
    public void ship_is_placed_on_board() {
        Ship ship = new FourMastedShip();
        Player player = new Player();
        //x2, y2 sa proponowane przez komputer dla 2-, 3-, 4-masztowca
        player.getBoard().placeShip(ship, x1, y1, x2, y2);

        //assertions
        ships.isPlaced;
        ship.position == x, y
    }

    //chodzi o przetestowanie player.getBoard().placeShip(ship, x1, y1, x2, y2);
    @Test(dataProvider = )
    public void places_ship_for_valid_X2_Y2() {

    }

    @Test(dataProvider = "", expectedExceptions = IllegalArgumentException.class)
    public void cannot_place_ship_for_invalid_X2_Y2() {

    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void ship_cannot_be_placed_outside_of_board() {
        //skrajne polozenie x1, y1 statku to rogi/boki planszy
    }

    @DataProvider(name = "validX2Y2")
    public Object[][] validPositions() {
        return new Object[][] {
                {FourMastedShip, Point(10, 10), {Point(10, 7), Point(7, 10)}}
        };
    }

    @Test(dataProvider = "validX2Y2")
    public void calulcated_fine_x2_y2_position() {

    }

    @DataProvider(name = "shipsStats")
    public Object[][] getShipsTypes() {
        return new Object[][] {
                {OneMastedShip, 1},
                {TwoMastedShip, 2},
                {ThreeMastedShip, 3},
                {FourMastedShip, 4},
        };
    }

    @Test(dataProvider = "shipsStats")
    public void ships_of_different_types_has_according_attributes() {

    }

}
