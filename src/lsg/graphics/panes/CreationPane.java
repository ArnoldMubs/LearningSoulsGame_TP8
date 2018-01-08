package lsg.graphics.panes;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class CreationPane extends VBox{
    private TextField nameField;
    private static final Duration ANIMATION_DURATION = Duration.millis(1500);
    private Scene scene;
    private String heroName;


    public CreationPane(Scene scene){
        this.scene = scene;
        this.nameField = new TextField();
        this.setAlignment(Pos.CENTER);
        this.nameField.setMaxWidth(200.0);
        this.getChildren().add(nameField);

    }

    public TextField getNameField() {
        return nameField;
    }

    public void fadeIn(EventHandler<ActionEvent> finishedHandler){
        FadeTransition ft = new FadeTransition(ANIMATION_DURATION);
        ft.setNode(this);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.setDuration(Duration.millis(3000));
        ft.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                finishedHandler.handle(event);
            }
        });

        ft.play();
    }



}
