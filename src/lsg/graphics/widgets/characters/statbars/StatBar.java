package lsg.graphics.widgets.characters.statbars;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import lsg.graphics.ImageFactory;
import lsg.graphics.widgets.texts.GameLabel;

import static lsg.graphics.ImageFactory.SPRITES_ID.HERO_HEAD;

public class StatBar extends BorderPane{

    private ImageView avatar;
    private GameLabel name;
    private ProgressBar lifeBar;
    private ProgressBar stamBar;

    public StatBar() {
        super();
        this.setMinSize(350,100);
        avatar = new ImageView();
        avatar.setImage(ImageFactory.getSprites(HERO_HEAD)[0]);
        avatar.setPreserveRatio(true);
        avatar.setFitHeight(100);
        name = new GameLabel("name");
        name.setStyle("-fx-font-size: 33 px");
        lifeBar = new ProgressBar();
        lifeBar.setMaxWidth(Double.MAX_VALUE);
        stamBar = new ProgressBar();
        stamBar.setMaxWidth(Double.MAX_VALUE);
        lifeBar.setStyle("-fx-accent:red");
        stamBar.setStyle("-fx-accent:greenyellow");
        this.setLeft(avatar);
        VBox vb = new VBox();
        vb.getChildren().addAll(name,lifeBar,stamBar);
        this.setCenter(vb);

    }

    public ImageView getAvatar() {
        return avatar;
    }

    public GameLabel getName() {
        return name;
    }

    public ProgressBar getLifeBar() {
        return lifeBar;
    }

    public ProgressBar getStamBar() {
        return stamBar;
    }

    public void flip () {
        this.setScaleX(-this.getScaleX());
        this.name.setScaleX(this.getScaleX());
    }

}
