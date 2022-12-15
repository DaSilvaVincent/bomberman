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

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * la classe Player représente le personnage du joueur qui utilise l'application.
 *
 * @author Romain Wallon
 *
 * @version 0.1.0
 */
public class Player extends AbstractCharacter {

    /**
     * La liste des bombes dont dispose ce joueur.
     */
    private ObservableList<AbstractBomb> bombs;

    /**
     * Construit un nouveau Player.
     *
     * @param bomberman Le jeu du Bomberman, dans lequel le personnage se déplace.
     */
    public Player(Bomberman bomberman) {
        super(3, bomberman);
        this.bombs = FXCollections.observableArrayList();
        init();
    }

    /**
     * Initialise les bombes de ce joueur.
     */
    private void init() {
        bombs.add(new Bomb());
        bombs.add(new Bomb());
        bombs.add(new Bomb());
        bombs.add(new Bomb());
        bombs.add(new Bomb());
        bombs.add(new LargeBomb());
        bombs.add(new LargeBomb());
        bombs.add(new LargeBomb());
        bombs.add(new LargeBomb());
        bombs.add(new LargeBomb());
        bombs.add(new RowBomb());
        bombs.add(new RowBomb());
        bombs.add(new RowBomb());
        bombs.add(new RowBomb());
        bombs.add(new RowBomb());
        bombs.add(new ColumnBomb());
        bombs.add(new ColumnBomb());
        bombs.add(new ColumnBomb());
        bombs.add(new ColumnBomb());
        bombs.add(new ColumnBomb());
    }

    /*
     * (non-Javadoc)
     *
     * @see fr.univartois.butinfo.ihm.bomberman.model.AbstractCharacter#getName()
     */
    @Override
    public String getName() {
        return "guy";
    }

    /**
     * Donne la liste des bombes dont dispose ce joueur.
     *
     * @return La liste des bombes.
     */
    public ObservableList<AbstractBomb> getBombs() {
        return bombs;
    }

    /**
     * Donne le nombre de bombes dont dispose ce joueur.
     *
     * @return Le nombre de bombes dont dispose ce joueur.
     */
    public int getNbBombs() {
        return bombs.size();
    }

    /**
     * Retire une bombe de la liste des bombes dont dispose ce joueur.
     *
     * @param bomb La bombe à retirer.
     */
    public void removeBomb(AbstractBomb bomb) {
        bombs.remove(bomb);
    }

}
