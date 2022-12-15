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

/**
 * L'interface ITileContent définit le contrat qui doit être respecté par les différents
 * éléments qui constituent la map du jeu du Bomberman.
 *
 * @author Romain Wallon
 *
 * @version 0.1.0
 */
public interface ITileContent {

    /**
     * Donne le nom de cet élément de la map.
     *
     * @return Le nom de cet élément.
     */
    String getName();

    /**
     * Vérifie si cet élement est considéré comme vide.
     * Un élément vide est un élément sur lequel un personnage peut se déplacer.
     *
     * @return Si cet élement est vide.
     */
    boolean isEmpty();

    /**
     * Détermine si une explosion peut détruire cet élément.
     *
     * @return Si une explosion a un effet sur cet élément.
     */
    boolean isDestroyableByExplosion();

}
