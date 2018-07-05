package it.polimi.se2018.view.cli;

import it.polimi.se2018.view.cli.commands.*;

public class CommandList {

    private CommandList() {
    }

    protected static final Command[] COMMANDS = {
            new ListPrivObjCards(),
            new ListPubObjCards(),
            new ListToolCards(),
            new PlaceDice(),
            new ShowMaps(),
            new SetReady(),
            new CompleteTurn(),
            new UseToolCard(),
            new MovePlacedDiceColor(),
            new MovePlacedDiceNumber(),
            new PlaceNotAdjacentDice(),
            new PlaceSecondDice(),
            new RethrowDice(),
            new RepositionRethrownDice(),
            new FlipDice(),
            new ChangeDiceValue(),
            new MovePlacedDices(),
            new ShakeDices(),
            new SwapTrackDice(),
            new ReplaceDice(),
            new PlaceNewDice(),
            new ShowTrack(),
            new ShowTokensCounts(),
            new MoveDicesWithTrack(),
    };

}
