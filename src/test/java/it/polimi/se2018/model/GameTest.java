import it.polimi.se2018.model.Game;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.Position;
import it.polimi.se2018.model.WindowPatternCard;
import it.polimi.se2018.util.WindowPatternLoader;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class GameTest {


    @Test
    public void startGame() {
        WindowPatternCard windowPattern = WindowPatternLoader.loadFromResource("loader-pattern-test.json");
        List<WindowPatternCard> windowListTest = new ArrayList<>();
        List<Player> playerListTest = new ArrayList<>();
        windowListTest.add(windowPattern);
        windowListTest.add(windowPattern);
        Player player1 = new Player("0001", windowListTest, Player.Color.BLUE);
        Player player2 = new Player("0002", windowListTest, Player.Color.RED);
        player1.setReady(0, true);
        player2.setReady(1, false);
        playerListTest.add(player1);
        playerListTest.add(player2);
        Game game = new Game(playerListTest);
        game.placeDice(new Position(3, 4), game.getDraftPool().get(0));
        Assert.assertEquals(player1, game.getCurrentPlayer());
        game.nextTurn();
        Assert.assertEquals(player2, game.getCurrentPlayer());
        game.placeDice(new Position(3, 3), game.getDraftPool().get(0));
        game.nextTurn();
        Assert.assertEquals(player2, game.getCurrentPlayer());
    }
}

