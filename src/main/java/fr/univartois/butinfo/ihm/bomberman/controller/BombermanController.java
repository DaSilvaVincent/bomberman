/**
 * Ce logiciel est distribué à des fins éducatives.
 *
 * Il est fourni "tel quel", sans garantie d’aucune sorte, explicite
 * ou implicite, notamment sans garantie de qualité marchande, d’adéquation
 * à un usage particulier et d’absence de contrefaçon.
 * En aucun cas, les auteurs ou titulaires du droit d’auteur ne seront
 * responsables de tout dommage, réclamation ou autre responsabilité, que ce
 * soit dans le cadre d’un contrat, d’un délit ou autre, en provenance de,
 * consécutif à ou en relation avec le logiciel ou son utilisation, ou avec
 * d’autres éléments du logiciel.
 *
 * (c) 2022 Romain Wallon - Université d'Artois.
 * Tous droits réservés.
 */

package fr.univartois.butinfo.ihm.bomberman.controller;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;

import fr.univartois.butinfo.ihm.bomberman.model.AbstractBomb;
import fr.univartois.butinfo.ihm.bomberman.model.AbstractCharacter;
import fr.univartois.butinfo.ihm.bomberman.model.Bomberman;
import fr.univartois.butinfo.ihm.bomberman.model.GameMap;
import fr.univartois.butinfo.ihm.bomberman.model.IBombermanController;
import fr.univartois.butinfo.ihm.bomberman.model.ITileContent;
import fr.univartois.butinfo.ihm.bomberman.model.Player;
import fr.univartois.butinfo.ihm.bomberman.model.Tile;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * La classe BombermanController propose un contrôleur permettant de gérer une partie du
 * Bomberman présentée à l'utilisateur sous la forme d'une interface graphique JavaFX.
 *
 * @author Romain Wallon
 *
 * @version 0.1.0
 */
public class BombermanController implements IBombermanController {

    /**
     * La {@link Stage} sur laquelle se déroule la partie de Bomberman.
     */
    private Stage stage;

    /**
     * La {@link Scene} sur laquelle la partie de Bomberman est affichée.
     */
    private Scene scene;

    /**
     * Le conteneur permettant d'afficher la map du jeu.
     */
    @FXML
    private GridPane gridPane;

    /**
     * Le label affichant les points de vie restants.
     */
    @FXML
    private Label healthLabel;

    /**
     * Le label affichant le nombre de bombes restantes.
     */
    @FXML
    private Label bombLabel;

    /**
     * Le modèle gérant la partie en cours.
     */
    private Bomberman bomberman;

    /**
     * La liste des bombes disponibles pour le joueur.
     */
    private ObservableList<AbstractBomb> availableBombs;

    /**
     * The eventFilter
     */
    private EventHandler<KeyEvent> eventFilter = e -> move(e);

    /**
     * Configure la {@link Stage} sur laquelle se déroule la partie de Bomberman.
     *
     * @param stage La fenêtre sur laquelle la partie de déroule.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Configure la {@link Scene} sur laquelle la partie de Bomberman est affichée.
     *
     * @param scene La Scene sur laquelle le jeu est affiché.
     */
    public void setScene(Scene scene) {
        this.scene = scene;
        this.scene.addEventFilter(KeyEvent.KEY_PRESSED, eventFilter);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * fr.univartois.butinfo.ihm.bomberman.model.IBombermanController#setBomberman(fr.
     * univartois.butinfo.ihm.bomberman.model.Bomberman)
     */
    @Override
    public void setBomberman(Bomberman bomberman) {
        this.bomberman = bomberman;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * fr.univartois.butinfo.ihm.bomberman.controller.IBombermanController#initMap(fr.
     * univartois.butinfo.ihm.bomberman.model.GameMap)
     */
    @Override
    public void initMap(GameMap map) {
        Label[][] labelMap = new Label[map.getHeight()][map.getWidth()];

        for (int i = 0; i < map.getHeight(); i++) {
            for (int j = 0; j < map.getWidth(); j++) {
                labelMap[i][j] = createLabel(map.get(i, j), i, j);
                gridPane.add(labelMap[i][j], j, i);
            }
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see fr.univartois.butinfo.ihm.bomberman.model.IBombermanController#initPlayer(fr.
     * univartois.butinfo.ihm.bomberman.model.Player)
     */
    @Override
    public void initPlayer(Player player) {
        availableBombs = player.getBombs();
        healthLabel.textProperty().bind(player.getHealthProperty().asString());
        bombLabel.textProperty().bind(Bindings.size(player.getBombs()).asString());
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * fr.univartois.butinfo.ihm.bomberman.model.IBombermanController#initEndConditions(
     * javafx.beans.property.BooleanProperty, javafx.beans.property.BooleanProperty)
     */
    @Override
    public void initEndConditions(BooleanProperty won, BooleanProperty lost) {
        BooleanBinding finished = won.or(lost);
        gridPane.disableProperty().bind(finished);
        finished.addListener((p, o, n) -> {
            if (Boolean.TRUE.equals(n)) {
                scene.removeEventFilter(KeyEvent.KEY_PRESSED, eventFilter);
            }
        });
    }

    /**
     * Crée le label représentant la tuile donnée.
     *
     * @param tile La tuile représentée par le label.
     *
     * @return Le label créé.
     */
    private Label createLabel(Tile tile, int row, int col) {
        // On crée le label avec les bonnes dimensions.
        Label label = new Label();
        label.setId(row + "-" + col);
        label.setPrefWidth(50);
        label.setPrefHeight(50);

        // On modifie l'affichage du label.
        label.setGraphic(new StackPane());
        label.setBackground(createBackground(tile.getContent()));

        // On écoute le déplacement d'un personnage sur la tuile.
        tile.getCharacterProperty().addListener(
                (p, o, n) -> updateGraphic(label, createImageFor(n), "character"));

        // On écoute le dépôt d'une bombe sur la tuile.
        tile.getBombProperty().addListener((p, o, n) -> {
            if (n == null) {
                updateGraphic(label, null, "bomb");
            } else {
                updateGraphic(label, loadImage(n.getName()), "bomb");
            }
        });

        // On écoute l'explosion d'une bombe sur la tuile.
        tile.getExplodedProperty().addListener((p, o, n) -> {
            if (Boolean.TRUE.equals(n)) {
                updateGraphic(label, loadImage("explosion"), "explode");
            } else {
                updateGraphic(label, null, "explode");
            }
        });

        // On écoute le changement de contenu de la tuile.
        tile.getContentProperty().addListener(
                (p, o, n) -> label.setBackground(createBackground(n)));

        return label;
    }

    /**
     * Met à jour l'affichage des composants d'un label donné.
     *
     * @param label Le label dont les composants graphiques doivent être mis à jour.
     * @param image L'image à afficher sur le label.
     * @param id L'identifiant de l'image à mettre à jour.
     */
    private void updateGraphic(Label label, Image image, String id) {
        String viewId = label.getId() + "-" + id;
        StackPane pane = (StackPane) label.getGraphic();

        if (image == null) {
            // On retire l'image du label.
            pane.getChildren().removeIf(n -> n.getId().equals(viewId));

        } else {
            // On ajoute l'image au label.
            ImageView view = new ImageView(image);
            view.setFitHeight(50);
            view.setFitWidth(50);
            view.setId(viewId);
            pane.getChildren().add(view);
        }
    }

    /**
     * Détermine l'arrière-plan de la tuile donnée, afin d'afficher son contenu.
     *
     * @param tile La tuile pour laquelle l'arrière-plan doit être déterminé.
     *
     * @return L'arrière-plan associé à la tuile.
     */
    private Background createBackground(ITileContent content) {
        BackgroundImage backgroundImage = new BackgroundImage(
                loadImage(content.getName()),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        return new Background(backgroundImage);
    }

    /**
     * Crée l'image permenttant d'afficher le personnage donné sur la map.
     *
     * @param character Le personnage à afficher.
     *
     * @return L'image du personnage.
     */
    private Image createImageFor(AbstractCharacter character) {
        if (character == null) {
            return null;
        }

        return loadImage(character.getName());
    }

    /**
     * Charge une image depuis les ressources de l'application.
     *
     * @param name Le nom de l'image à charger.
     *
     * @return L'image qui a été chargée.
     */
    private Image loadImage(String name) {
        URL urlImage = getClass().getResource("../view/images/" + name + ".png");
        return new Image(urlImage.toExternalForm(), 50, 50, true, false);
    }

    /**
     * Effectue un déplacement en fonction de la touche du clavier sur laquelle
     * l'utilisateur a appuyé.
     * 
     * @param event L'événement ayant déclenché l'appel de cette méthode.
     */
    @SuppressWarnings("incomplete-switch")
    private void move(KeyEvent event) {
        switch (event.getCode()) {
            case UP -> bomberman.moveUp();
            case LEFT -> bomberman.moveLeft();
            case DOWN -> bomberman.moveDown();
            case RIGHT -> bomberman.moveRight();
            case SPACE -> {
                if (event.isControlDown()) {
                    chooseBomb();
                } else {
                    bomberman.dropBomb();
                }
            }
        }
    }

    /**
     * Change la vue affichée dans la fenêtre pour permettre au joueur de choisir la bombe
     * qu'il souhaite poser.
     */
    private void chooseBomb() {
        try {
            // Il faut d'abord récupérer la description de la nouvelle vue.
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("../view/choose-bomb.fxml"));
            Parent viewContent = fxmlLoader.load();

            // Ensuite, on la place dans une nouvelle Scene...
            Scene chooseBombScene = new Scene(viewContent, stage.getWidth(), stage.getHeight());
            // que l'on place elle-même dans la fenêtre pour remplacer la scène courante.
            stage.setScene(chooseBombScene);

            // On lie le modèle au nouveau contrôleur.
            ChooseBombController controller = fxmlLoader.getController();
            controller.setStage(stage);
            controller.setMainScene(scene);
            controller.setBomberman(bomberman);
            controller.setBombs(availableBombs);

        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}
