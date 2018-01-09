package lsg.graphics.panes;

import javafx.geometry.Insets;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import lsg.graphics.widgets.characters.statbars.StatBar;

public class HUDPane extends BorderPane {
    private MessagePane messagePane;
    private StatBar heroStatBar;
    private StatBar monsterStatBar;

    public HUDPane() {
        buildCenter();
        buildTop();
    }
    public void buildCenter(){
        messagePane = new MessagePane();
        this.setCenter(messagePane);
    }
    public MessagePane getMessagePane() {
        return messagePane;

    }

    public StatBar getHeroStatBar() {
        return heroStatBar;
    }

    public StatBar getMonsterStatBar() {
        return monsterStatBar;
    }

    public void buildTop() {
        BorderPane bd = new BorderPane();
        this.setTop(bd);
        heroStatBar = new StatBar();
        monsterStatBar= new StatBar();
        monsterStatBar.flip();
        bd.setLeft(heroStatBar);
        bd.setRight(monsterStatBar);
        this.setPadding(new Insets(100,20,0,20));
    }
}
