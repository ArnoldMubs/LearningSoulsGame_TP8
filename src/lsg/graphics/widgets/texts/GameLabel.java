package lsg.graphics.widgets.texts;

import javafx.scene.Node;
import javafx.scene.control.Label;
import lsg.graphics.CSSFactory;

public class GameLabel extends Label{
    public GameLabel() {
        super();
        applyMyCss();

    }

    public GameLabel(String text) {
        super(text);
        applyMyCss();

    }

    public GameLabel(String text, Node graphic) {
        super(text, graphic);
        applyMyCss();
    }

    private void applyMyCss(){
        //super.getStylesheets().add(CSSFactory.getStyleSheet("LSGFont.css"));
        this.getStyleClass().addAll("game-font");
        this.getStyleClass().addAll("game-font-fx");

    }
}
