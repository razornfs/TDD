package pl.sda.javawwa18.warships;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class WarshipsGameTest {

    //test czegos, co bedzie kontrolowalo, czy mozna zaczac gre (czy warunki init sa spelnione)
    @Test
    public void all_players_ships_are_placed() {

    }

    @Test
    public void player_get_next_turn_for_hitting_other_player() {

    }

    @Test
    public void player_looses_turn_for_not_hitting_other_player() {

    }

    @Test
    public void player_sinked_enemy_four_masted_ship() {

    }

    @DataProvider(name = "commandsForAction")+
    public Object[][] getCommandsForAction() {
        return new Object[][] {
                {Point(1, 1), Command.SINKED},
                {Point(2, 5), Command.HIT},
                {Point(6, 5), Command.LOST}
        };
    }

    @Test(dataProvider = "commandsForAction")
    public void gets_correct_command_for_action() {
        //zakodowac polozenia statkow

        //odczytac z data provider, dla jakiego ruchu, jako komenda
    }

    @Test
    public void player_wins() {
        
    }

}
