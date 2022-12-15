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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.util.Duration;

/**
 * La classe Bomberman gère une partie du jeu.
 *
 * @author Romain Wallon
 *
 * @version 0.1.0
 */
public class Bomberman {

    /**
     * Le génarateur de nombres aléatoires utilisé dans le jeu.
     */
    public static final Random RANDOM = new Random();

    /**
     * La map du jeu.
     */
    private final GameMap gameMap;

    /**
     * Le personnage du joueur.
     */
    private final Player player;

    /**
     * Les personnages des adversaires du joueur.
     */
    private final List<Enemy> enemies;

    /**
     * Le contrôleur de l'application.
     */
    private IBombermanController controller;

    /**
     * La propriété enregistrant si la partie est gagnée.
     */
    private final BooleanProperty gameWon;

    /**
     * La propriété enregistrant si la partie est perdue.
     */
    private final BooleanProperty gameLost;

    /**
     * Construit une nouvelle instance de Bomberman.
     *
     * @param gameMap La map du jeu.
     * @param nbEnemies Le nombre d'adversaires à affronter.
     */
    public Bomberman(GameMap gameMap, int nbEnemies) {
        this.gameMap = gameMap;
        this.player = new Player(this);
        this.enemies = new ArrayList<>(nbEnemies);
        this.gameWon = new SimpleBooleanProperty();
        this.gameLost = new SimpleBooleanProperty();

        for (int i = 0; i < nbEnemies; i++) {
            Enemy enemy = new Enemy("minotaur", this);
            enemies.add(enemy);
        }
    }

    /**
     * Modifie le contrôleur avec lequel interagir pour mettre à jour l'affichage.
     *
     * @param controller Le contrôleur avec lequel interagir.
     */
    public void setController(IBombermanController controller) {
        this.controller = controller;
    }

    /**
     * Démarre une partie.
     */
    public void startGame() {
        controller.initMap(gameMap);
        controller.initPlayer(player);
        controller.initEndConditions(gameWon, gameLost);

        spawnCharacter(player);

        for (Enemy enemy : enemies) {
            spawnCharacter(enemy);
            enemy.animate();
        }
    }

    /**
     * Place un personnage aléatoirement sur la map du jeu.
     *
     * @param character Le personnage à placer.
     */
    private void spawnCharacter(AbstractCharacter character) {
        List<Tile> emptyTiles = gameMap.getEmptyTiles();
        Tile tile = emptyTiles.get(RANDOM.nextInt(emptyTiles.size()));
        character.setPosition(tile.getRow(), tile.getColumn());
        tile.setCharacter(character);
    }

    /**
     * Retire un personnage de la map du jeu.
     *
     * @param character Le character à retirer.
     */
    public void removeCharacter(AbstractCharacter character) {
        // On retire le personnage.
        gameMap.get(character.getRow(), character.getColumn()).removeCharacter();

        // Si le personnage est le joueur, alors la partie est perdue.
        if (character == player) {
            gameLost.set(true);
            return;
        }

        // Si on retire le dernier ennemi, alors la partie est gagnée.
        enemies.remove(character);
        if (enemies.isEmpty()) {
            gameWon.set(true);
        }
    }

    /**
     * Déplace le personnage du joueur vers le haut.
     */
    public void moveUp() {
        moveUp(player);
    }

    /**
     * Déplace le personnage du joueur vers la droite.
     */
    public void moveRight() {
        moveRight(player);
    }

    /**
     * Déplace le personnage du joueur vers le bas.
     */
    public void moveDown() {
        moveDown(player);
    }

    /**
     * Déplace le personnage du joueur vers la gauche.
     */
    public void moveLeft() {
        moveLeft(player);
    }

    /**
     * Déplace un personnage vers le haut.
     *
     * @param character Le personnage à déplacer.
     */
    public void moveUp(AbstractCharacter character) {
        move(character, -1, 0);
    }

    /**
     * Déplace un personnage vers la droite.
     *
     * @param character Le personnage à déplacer.
     */
    public void moveRight(AbstractCharacter character) {
        move(character, 0, +1);
    }

    /**
     * Déplace un personnage vers le bas.
     *
     * @param character Le personnage à déplacer.
     */
    public void moveDown(AbstractCharacter character) {
        move(character, +1, 0);
    }

    /**
     * Déplace un personnage vers la gauche.
     *
     * @param character Le personnage à déplacer.
     */
    public void moveLeft(AbstractCharacter character) {
        move(character, 0, -1);
    }

    /**
     * Déplace un personnage dans une direction donnée.
     *
     * @param character Le personnage à déplacer.
     * @param rowShift Le déplacement à réaliser en ligne.
     * @param columnShift Le déplacement à réaliser en colonne.
     */
    private void move(AbstractCharacter character, int rowShift, int columnShift) {
        int row = character.getRow();
        int column = character.getColumn();

        if (gameMap.isOnMap(row + rowShift, column + columnShift)
                && gameMap.get(row + rowShift, column + columnShift).isEmpty()) {
            gameMap.get(row, column).removeCharacter();
            gameMap.get(row + rowShift, column + columnShift).setCharacter(character);
            character.setPosition(row + rowShift, column + columnShift);
        }
    }

    /**
     * Dépose une bombe sur la tuile où se trouve le joueur, et programme l'explosion de
     * cette bombe.
     */
    public void dropBomb() {
        if (player.getNbBombs() > 0) {
            AbstractBomb bomb = player.getBombs().get(0);
            dropBomb(bomb);
        }
    }

    /**
     * Dépose une bombe sur la tuile où se trouve le joueur, et programme l'explosion de
     * cette bombe.
     *
     * @param bomb La bombe à déposer.
     */
    public void dropBomb(AbstractBomb bomb) {
        int row = player.getRow();
        int column = player.getColumn();

        bomb.setPosition(row, column);
        bomb.setGameMap(gameMap);
        gameMap.get(row, column).putBomb(bomb);
        player.removeBomb(bomb);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(bomb.getDelay()), e -> {
                    bomb.explode();
                    gameMap.get(row, column).removeBomb();
                }));
        timeline.play();
    }

}
