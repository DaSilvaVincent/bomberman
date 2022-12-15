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

import javafx.beans.property.BooleanProperty;

/**
 * L'interface IBombermanController définit le contrat qui doit être respecté par
 * n'importe quel contrôleur du jeu du Bomberman.
 *
 * @author Romain Wallon
 *
 * @version 0.1.0
 */
public interface IBombermanController {

    /**
     * Modifie le modèle du jeu qui doit être affiché par la vue.
     *
     * @param bomberman Le modèle du jeu, qui gère la partie en cours.
     */
    void setBomberman(Bomberman bomberman);

    /**
     * Initialise la map du jeu du Bomberman.
     *
     * @param map La map du jeu.
     */
    void initMap(GameMap map);

    /**
     * Initialise le joueur du Bomberman.
     *
     * @param player Le personnage du joueur.
     */
    void initPlayer(Player player);

    /**
     * Initalise les conditions d'arrêt du jeu.
     *
     * @param won La propriété correspondant à une partie gagnée.
     * @param lost La propriété corrspondant à une partie perdue.
     */
    void initEndConditions(BooleanProperty won, BooleanProperty lost);

}
