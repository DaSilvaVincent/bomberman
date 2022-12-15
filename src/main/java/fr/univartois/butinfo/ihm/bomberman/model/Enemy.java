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

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * La classe Enemy représente un adversaire du joueur dans le jeu du Bomberman.
 *
 * @author Romain Wallon
 *
 * @version 0.1.0
 */
public class Enemy extends AbstractCharacter {

    /**
     * Le nom de ce personnage.
     */
    private final String name;

    /**
     * La Timeline permettant à ce personnage de se déplacer seul.
     */
    private Timeline timeline;

    /**
     * Construit un nouvel Enemy.
     *
     * @param name Le nom du personnage.
     * @param bomberman Le jeu du Bomberman, dans lequel le personnage se déplace.
     */
    public Enemy(String name, Bomberman bomberman) {
        super(1, bomberman);
        this.name = name;
    }

    /*
     * (non-Javadoc)
     *
     * @see fr.univartois.butinfo.ihm.bomberman.model.AbstractCharacter#getName()
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Anime ce personnage afin qu'il se déplace seul.
     */
    public void animate() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> moveRandomly()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    /**
     * Réalise un déplacement aléatoire de ce personnage.
     */
    private void moveRandomly() {
        switch (Bomberman.RANDOM.nextInt(4)) {
            case 0 -> bomberman.moveUp(this);
            case 1 -> bomberman.moveLeft(this);
            case 2 -> bomberman.moveDown(this);
            default -> bomberman.moveRight(this);
        }
    }
    
    /* 
     * (non-Javadoc)
     * 
     * @see fr.univartois.butinfo.ihm.bomberman.model.AbstractCharacter#decHealth()
     */
    @Override
    public void decHealth() {
        super.decHealth();
        if (getHealth() == 0) {
            timeline.stop();
        }
    }

}
