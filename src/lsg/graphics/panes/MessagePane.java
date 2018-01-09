package lsg.graphics.panes;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import lsg.graphics.widgets.texts.GameLabel;

public class MessagePane extends VBox{

    private static final Duration ANIMATION_DURATION = Duration.millis(1500);

    private GameLabel messageLabel;

    public MessagePane(){
        this.setAlignment(Pos.CENTER);

    }

    public void showMessage (String Msg){
        this.messageLabel = new GameLabel(Msg);
        this.getChildren().add(messageLabel);
        translate();

    }

    public void translate () {
        FadeTransition ft = new FadeTransition(ANIMATION_DURATION);
        ft.setNode(this);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        ft.setDuration(Duration.millis(3000));

        TranslateTransition tt = new TranslateTransition(ANIMATION_DURATION);
        tt.setToY(1);

        ParallelTransition pt = new ParallelTransition(ft,tt);
        pt.setNode(messageLabel);
        pt.setCycleCount(1);
        pt.play();

    }



}
