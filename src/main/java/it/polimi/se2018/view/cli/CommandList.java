package it.polimi.se2018.view.cli;

import it.polimi.se2018.view.cli.commands.*;

public class CommandList {

    private CommandList() {
    }

    public static final Command[] COMMANDS = {
            new ListPrivObjCards(),
            new ListPubObjCards(),
            new ListToolCards(),
            new PlaceDice(),
            new ShowMaps(),
    };

}
