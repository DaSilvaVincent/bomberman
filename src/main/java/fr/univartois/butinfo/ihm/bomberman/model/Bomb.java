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
 * La classe Bomb représente une bombe qui peut être déposée sur une tuile de la map afin
 * de faire exploser le contenu des tuiles environnantes.
 *
 * @author Romain Wallon
 *
 * @version 0.1.0
 */
public class Bomb extends AbstractBomb {

    /*
     * (non-Javadoc)
     *
     * @see fr.univartois.butinfo.ihm.bomberman.model.AbstractBomb#getName()
     */
    @Override
    public String getName() {
        return "bomb";
    }

    /*
     * (non-Javadoc)
     *
     * @see fr.univartois.butinfo.ihm.bomberman.model.AbstractBomb#getDescription()
     */
    @Override
    public String getDescription() {
        return "Cette bombe fait exploser le contenu des tuiles environnantes.";
    }

    /*
     * (non-Javadoc)
     * 
     * @see fr.univartois.butinfo.ihm.bomberman.model.AbstractBomb#getDelay()
     */
    @Override
    public int getDelay() {
        return 3;
    }

    /*
     * (non-Javadoc)
     * 
     * @see fr.univartois.butinfo.ihm.bomberman.model.AbstractBomb#explode()
     */
    @Override
    public void explode() {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (gameMap.isOnMap(row + i, column + j)) {
                    gameMap.get(row + i, column + j).explode();
                }
            }
        }
    }

}
