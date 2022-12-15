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

package fr.univartois.butinfo.ihm.bomberman.model;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.util.Duration;

/**
 * La classe Tile représente une tuile composant la map du jeu du Bomberman.
 * Une fois créée, une telle tuile devient fixe sur la map : c'est son contenu qui change
 * au cours du jeu.
 *
 * @author Romain Wallon
 *
 * @version 0.1.0
 */
public class Tile {

    /**
     * La ligne où cette tuile est positionnée sur la map.
     */
    private final int row;

    /**
     * La colonne où cette tuile est positionnée sur la map.
     */
    private final int column;

    /**
     * Le contenu de cette tuile.
     */
    private final ObjectProperty<ITileContent> content;

    /**
     * L'éventuel personnage qui peut se trouver sur cette tuile.
     */
    private final ObjectProperty<AbstractCharacter> character;

    /**
     * L'éventuelle bombe qui a été déposée sur cette tuile.
     */
    private final ObjectProperty<AbstractBomb> bomb;

    /**
     * L'état d'explosion de cette tuile.
     */
    private final BooleanProperty exploded;

    /**
     * Construit une nouvelle instance de Tile.
     * 
     * @param row La ligne où la tuile est positionnée sur la map.
     * @param column La colonne où la tuile est positionnée sur la map.
     */
    public Tile(int row, int column) {
        this.row = row;
        this.column = column;
        this.content = new SimpleObjectProperty<>();
        this.character = new SimpleObjectProperty<>();
        this.bomb = new SimpleObjectProperty<>();
        this.exploded = new SimpleBooleanProperty();
    }

    /**
     * Donne la ligne où cette tuile est positionnée sur la map.
     *
     * @return La ligne où cette tuile est positionnée sur la map.
     */
    public int getRow() {
        return row;
    }

    /**
     * Donne la colonne où cette tuile est positionnée sur la map.
     *
     * @return La colonne où cette tuile est positionnée sur la map.
     */
    public int getColumn() {
        return column;
    }

    /**
     * Donne le contenu de cette tuile.
     *
     * @return Le contenu de cette tuile.
     */
    public ITileContent getContent() {
        return content.get();
    }

    /**
     * Donne le contenu de cette tuile.
     *
     * @return Le contenu de cette tuile.
     */
    public ObjectProperty<ITileContent> getContentProperty() {
        return content;
    }

    /**
     * Change le contenu de cette tuile.
     *
     * @param content Le nouveau contenu de cette tuile.
     */
    public void setContent(ITileContent content) {
        this.content.set(content);
    }

    /**
     * Donne la propriété représentant l'éventuel personnage qui peut se trouver
     * sur cette tuile.
     * 
     * @return La propriété représentant l'éventuel personnage sur cette tuile.
     */
    public ObjectProperty<AbstractCharacter> getCharacterProperty() {
        return character;
    }

    /**
     * Modifie le personnage se trouvant sur cette tuile.
     *
     * @param character Le nouveau personnage sur cette tuile.
     */
    public void setCharacter(AbstractCharacter character) {
        this.character.set(character);
    }

    /**
     * Retire le personnage actuellement présent sur cette tuile.
     */
    public void removeCharacter() {
        this.character.set(null);
    }

    /**
     * Donne la propriété représentant l'éventuelle bombe qui a été déposée sur cette
     * tuile.
     * 
     * @return La propriété représentant l'éventuelle bombe sur cette tuile.
     */
    public ObjectProperty<AbstractBomb> getBombProperty() {
        return bomb;
    }

    /**
     * Modifie la bombe qui a été déposée sur cette tuile.
     *
     * @param bomb La nouvelle bombe déposée sur cette tuile.
     */
    public void putBomb(AbstractBomb bomb) {
        this.bomb.set(bomb);
    }

    /**
     * Retire la bombe déposée sur cette tuile.
     */
    public void removeBomb() {
        this.bomb.set(null);
    }

    /**
     * Donne la propriété décrivant si cette tuile est en cours d'explosion ou non.
     *
     * @return La propriété décrivant l'état d'explosion de cette tuile.
     */
    public BooleanProperty getExplodedProperty() {
        return exploded;
    }

    /**
     * Vérifie si cette tuile est vide, c'est-à-dire que son contenu est vide et qu'aucun
     * personnage n'est présent sur cette tuile.
     *
     * @return Si cette tuile est vide.
     *
     * @see ITileContent#isEmpty()
     */
    public boolean isEmpty() {
        return content.get().isEmpty() && (character.get() == null);
    }

    /**
     * Fait exploser cette tuile.
     */
    public void explode() {
        if (content.get().isDestroyableByExplosion()) {
            // Si un personnage est sur cette tuile, il perd de la vie.
            AbstractCharacter characterHere = character.get();
            if (characterHere != null) {
                characterHere.decHealth();
            }

            // On passe en mode explosé.
            exploded.set(true);

            // Après 3 secondes, le contenu de la tuile est détruit.
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), e -> {
                exploded.set(false);
                setContent(new Lawn());
            }));
            timeline.play();
        }
    }

}
