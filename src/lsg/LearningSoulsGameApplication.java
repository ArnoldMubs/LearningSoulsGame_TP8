package lsg;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lsg.graphics.CSSFactory;
import lsg.graphics.ImageFactory;
import lsg.graphics.panes.AnimationPane;
import lsg.graphics.panes.CreationPane;
import lsg.graphics.panes.TitlePane;
import lsg.graphics.widgets.texts.GameLabel;

public class LearningSoulsGameApplication extends Application{
    private Scene scene;
    private AnchorPane root;
    private TitlePane gameTitle;
    private CreationPane creationPane;
    private TitlePane playerNameTitle;
    private String heroName;
    private AnimationPane animationPane;

    private void addListeners() {
        creationPane.getNameField().setOnAction((event -> {
            heroName = creationPane.getNameField().getText();
            System.out.println("Nom du héro : "+ heroName);
            if (heroName != null){
                root.getChildren().removeAll(creationPane);
                root.getChildren().removeAll(playerNameTitle);
            }
            gameTitle.zoomOut((event1 -> {
                play();
            }));
        }));
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Learning Souls Game");
        this.root = new AnchorPane();
        this.scene = new Scene(root,1200,800);
        primaryStage.setScene(this.scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        buildUI(primaryStage);
        addListeners();
        startGame();
    }

    private void buildUI(Stage stage) {
        this.scene.getStylesheets().add(CSSFactory.getStyleSheet("LSG.css"));
        this.scene.getStylesheets().add(CSSFactory.getStyleSheet("LSGFont.css"));
        this.gameTitle = new TitlePane(this.scene,"Learning Souls Game");
        this.playerNameTitle = new TitlePane(this.scene,"Player name");
        this.creationPane = new CreationPane(this.scene);
        AnchorPane.setLeftAnchor(gameTitle,0.0);
        AnchorPane.setTopAnchor(gameTitle,0.0);
        AnchorPane.setRightAnchor(gameTitle,0.0);
        //****** creationPane
        creationPane.setOpacity(0);
        AnchorPane.setLeftAnchor(creationPane,0.0);
        AnchorPane.setTopAnchor(creationPane,90.0);
        AnchorPane.setRightAnchor(creationPane,0.0);
        AnchorPane.setBottomAnchor(creationPane,0.0);
        //****** Player name
        AnchorPane.setLeftAnchor(playerNameTitle,0.0);
        AnchorPane.setTopAnchor(playerNameTitle,0.0);
        AnchorPane.setRightAnchor(playerNameTitle,0.0);
        AnchorPane.setBottomAnchor(playerNameTitle,40.0);

        root.getChildren().addAll(this.playerNameTitle);
        root.getChildren().addAll(this.gameTitle);
        root.getChildren().addAll(this.creationPane);
        animationPane = new AnimationPane(root);
    }

    public void startGame () {
        gameTitle.zoomIn((event -> {
                //System.out.println("terminée");
               creationPane.fadeIn((event1 -> {
                   ImageFactory.preloadAll((() -> {
                       System.out.println("pré-chargement des images terminé");
                   }));
               }));
        }));

    }

    private void play() {
        root.getChildren().addAll(this.animationPane);
        animationPane.startDemo();
    }
}
