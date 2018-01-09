package lsg;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lsg.characters.Hero;
import lsg.characters.Zombie;
import lsg.exceptions.StaminaEmptyException;
import lsg.exceptions.WeaponBrokenException;
import lsg.exceptions.WeaponNullException;
import lsg.graphics.CSSFactory;
import lsg.graphics.ImageFactory;
import lsg.graphics.panes.*;
import lsg.graphics.widgets.characters.renderers.HeroRenderer;
import lsg.graphics.widgets.characters.renderers.ZombieRenderer;
import lsg.weapons.Sword;

import static lsg.graphics.ImageFactory.SPRITES_ID.HERO_HEAD;
import static lsg.graphics.ImageFactory.SPRITES_ID.ZOMBIE_HEAD;

public class LearningSoulsGameApplication extends Application{

    private Scene scene;
    private AnchorPane root;
    private TitlePane gameTitle;
    private CreationPane creationPane;
    private TitlePane playerNameTitle;
    private String heroName;
    private AnimationPane animationPane;
    private Hero hero;
    private HeroRenderer heroRenderer;
    private Zombie zombie;
    private ZombieRenderer zombieRenderer;
    private HUDPane hudPane;

    public void createHero() {
        this.hero = new Hero(this.heroName);
        this.hero.setWeapon(new Sword());
        hudPane.getHeroStatBar().getName().setText(this.hero.getName());
        this.heroRenderer = animationPane.createHeroRenderer();
        this.heroRenderer.goTo(animationPane.getPrefWidth()*0.5 - heroRenderer.getFitWidth()*0.65, null);
        hudPane.getHeroStatBar().getLifeBar().progressProperty().bind(hero.lifeRateProperty());
        hudPane.getHeroStatBar().getStamBar().progressProperty().bind(hero.staminaRateProperty());
    }

    public void createMonster(EventHandler<ActionEvent> finishedHandler){
        this.zombie = new Zombie();
        hudPane.getMonsterStatBar().getName().setText(this.zombie.getName());
        hudPane.getMonsterStatBar().getAvatar().setImage(ImageFactory.getSprites(ZOMBIE_HEAD)[0]);
        hudPane.getMonsterStatBar().getAvatar().setRotate(20);
        this.zombieRenderer  =animationPane.createZombieRenderer();
        zombieRenderer.goTo(animationPane.getPrefWidth()*0.5 - zombieRenderer.getBoundsInLocal().getWidth() * 0.15, finishedHandler);
        hudPane.getMonsterStatBar().getLifeBar().progressProperty().bind(zombie.lifeRateProperty());
        hudPane.getMonsterStatBar().getStamBar().progressProperty().bind(zombie.staminaRateProperty());
    }

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
        this.hudPane = new HUDPane();
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
        //******hudPane
        AnchorPane.setLeftAnchor(hudPane,0.0);
        AnchorPane.setTopAnchor(hudPane,0.0);
        AnchorPane.setRightAnchor(hudPane,0.0);
        AnchorPane.setBottomAnchor(hudPane,200.0);

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
        root.getChildren().addAll(this.hudPane);
        createHero();
        this.createMonster((
                event -> {
                    System.out.println("Fini");
                    try {
                        this.hero.attack();
                    } catch (WeaponNullException e) {
                        e.printStackTrace();
                    } catch (WeaponBrokenException e) {
                        e.printStackTrace();
                    } catch (StaminaEmptyException e) {
                        e.printStackTrace();
                    }
                    hudPane.getMessagePane().showMessage("FIGHT !");
                }
        ));
    }
}
