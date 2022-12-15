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
 * La classe BrickWall représente un mur de briques sur la map.
 * Il s'agit d'un élément qui ne peut pas être traversé, mais qui peut être détruit par
 * une explosion.
 *
 * @author Romain Wallon
 *
 * @version 0.1.0
 */
public class BrickWall implements ITileContent {

    /*
     * (non-Javadoc)
     *
     * @see fr.univartois.butinfo.ihm.bomberman.model.ITileContent#getName()
     */
    @Override
    public String getName() {
        return "bricks";
    }

    /*
     * (non-Javadoc)
     *
     * @see fr.univartois.butinfo.ihm.bomberman.model.ITileContent#isEmpty()
     */
    @Override
    public boolean isEmpty() {
        return false;
    }

    /*
     * (non-Javadoc)
     *
     * @see fr.univartois.butinfo.ihm.bomberman.model.ITileContent#isDestroyableByExplosion()
     */
    @Override
    public boolean isDestroyableByExplosion() {
        return true;
    }

}
