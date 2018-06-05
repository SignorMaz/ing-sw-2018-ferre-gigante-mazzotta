package it.polimi.se2018.model;

import it.polimi.se2018.util.WindowPatternLoader;

import java.util.ArrayList;
import java.util.List;

public class WindowPatternsDeck {
    private final List<WindowPatternCard> windowPatternCards;

    private static final String JSON_CARDS[] = {
            "auroraemagnificus-aurorasagradis.json",
            "battle-bellesguard.json",
            "chromaticsplendor-comitas.json",
            "firelight-sunsglory.json",
            "fractaldrops-ripplesoflight.json",
            "fulgordelcielo-luzcelestial.json",
            "gravitas-wateroflife.json",
            "kaleidoscopicdream-firmitas.json",
            "luxmundi-luxastram.json",
            "suncatcher-shadowthief.json",
            "vialux-industria.json",
            "virtus-symphonyoflight.json",
    };

    public WindowPatternsDeck() {
        windowPatternCards = new ArrayList<>();
        for (String jsonCard : JSON_CARDS) {
            windowPatternCards.add(WindowPatternLoader.loadFromResource(jsonCard));
        }
    }

    public List<WindowPatternCard> getWindowPatternCards() {
        return windowPatternCards;
    }
}
