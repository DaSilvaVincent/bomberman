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

import fr.univartois.butinfo.ihm.bomberman.model.AbstractBomb;
import fr.univartois.butinfo.ihm.bomberman.model.Bomberman;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * La classe ChooseBombController fournit le contrôleur de la vue permettant de
 * choisir une bombe parmi celles dont dispose le joueur.
 *
 * @author Romain Wallon
 *
 * @version 0.1.0
 */
public class ChooseBombController {

    /**
     * La {@link Stage} sur laquelle se déroule la partie de Bomberman.
     */
    private Stage stage;

    /**
     * La {@link Scene} principale, sur laquelle la partie de Bomberman est affichée.
     */
    private Scene mainScene;

    /**
     * La vue affichant la liste des bombes disponibles.
     */
    @FXML
    private ListView<AbstractBomb> bombList;

    /**
     * Le label affichant le nom de la bombe sélectionnée.
     */
    @FXML
    private Label nameLabel;

    /**
     * Le label affichant la description de la bombe sélectionnée.
     */
    @FXML
    private Label descriptionLabel;

    /**
     * La vue affichant l'image de la bombe sélectionnée.
     */
    @FXML
    private ImageView imageView;

    /**
     * Le label affichant le délai d'explosion de la bombe sélectionnée.
     */
    @FXML
    private Label delayLabel;

    /**
     * La partie de Bomberman en cours.
     */
    private Bomberman bomberman;

    /**
     * Configure la {@link Stage} sur laquelle se déroule la partie de Bomberman.
     *
     * @param stage La fenêtre sur laquelle la partie de déroule.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Configure la {@link Scene} principale, sur laquelle la partie de Bomberman est
     * affichée.
     *
     * @param scene La Scene sur laquelle le jeu est affiché.
     */
    public void setMainScene(Scene mainScene) {
        this.mainScene = mainScene;
    }

    /**
     * Modifie la partie de Bomberman en cours.
     *
     * @param bomberman La partie de Bomberman en cours.
     */
    public void setBomberman(Bomberman bomberman) {
        this.bomberman = bomberman;
    }

    /**
     * Configure la liste des bombes à afficher.
     *
     * @param availableBombs Les bombes dont dispose l'utilisateur.
     */
    public void setBombs(ObservableList<AbstractBomb> availableBombs) {
        bombList.setItems(availableBombs);

        // Affiche la description de la bombe sélectionnée à chaque sélection.
        bombList.getSelectionModel().selectedItemProperty().addListener((o, p, n) -> {
            nameLabel.setText(n.getName());
            descriptionLabel.setText(n.getDescription());
            imageView.setImage(new Image(getClass().getResource(
                    "../view/images/" + n.getName() + ".png").toExternalForm(),
                    100, 100, true, false));
            delayLabel.setText(Integer.toString(n.getDelay()));
        });
    }

    /**
     * Annule la sélection de la bombe, et revient à la {@link Scene} principale.
     */
    @FXML
    void cancel() {
        stage.setScene(mainScene);
    }

    /**
     * Dépose la bombe sélectionnée, et revient à la {@link Scene} principale.
     */
    @FXML
    void chooseBomb() {
        bomberman.dropBomb(bombList.getSelectionModel().getSelectedItem());
        stage.setScene(mainScene);
    }

    /**
     * Réalise l'opération correspondant à l'appui sur une touche alors qu'une bombe est
     * sélectionnée dans la liste.
     *
     * @param event L'événement qui a déclenché l'appel de cette méthode.
     */
    @FXML
    void keyPressedOnList(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            // La bombe actuellement sélectionnée doit être choisie.
            chooseBomb();

        } else if (event.getCode() == KeyCode.ESCAPE) {
            // Il faut annuler le choix d'une bombe.
            cancel();
        }
    }

}
