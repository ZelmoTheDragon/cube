package com.github.zelmothedragon.cube.core.input;

import java.io.Serializable;
import java.util.Objects;

/**
 * Touche de la manette.
 *
 * @author MOSELLE Maxime
 */
public final class Key implements Serializable {

    /**
     * Numéro de série.
     */
    private static final long serialVersionUID = -4613165600205365885L;

    /**
     * Touche factice.
     */
    public static final Key EMPTY = new Key(GamePad.EMPTY, -1);

    /**
     * Nom de la touche.
     */
    private final GamePad name;

    /**
     * Code technique de la touche.
     */
    private final int keyCode;

    /**
     * Indique si la touche est appuyée durant la boucle de la mise à jour du
     * jeu.
     */
    private boolean pressed;

    /**
     * Indique si la touche est appuyée hors de la boucle principale du jeu.
     */
    private boolean hold;

    /**
     * Constructeur. Construit une touche avec un nom associé avec le code
     * physique de la touche.
     *
     * @param name Nom de la touche
     * @param keyCode Code technique de la touche
     */
    public Key(final GamePad name, final int keyCode) {
        this.name = name;
        this.keyCode = keyCode;
        this.pressed = false;
        this.hold = false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(42, name);
    }

    @Override
    public boolean equals(final Object obj) {
        final boolean eq;
        if (this == obj) {
            eq = true;
        } else if (Objects.isNull(obj) || !Objects.equals(getClass(), obj.getClass())) {
            eq = false;
        } else {
            final Key other = (Key) obj;
            eq = Objects.equals(this.name, other.name);
        }
        return eq;
    }

    @Override
    public String toString() {
        return String.format(
                "Key{name=%s, keyCode=%s, pressed=%s, hold=%s}",
                name,
                keyCode,
                pressed,
                hold
        );
    }

    /**
     * Synchroniser la touche durant la phase de mise à jour du jeu.
     */
    public void update() {
        pressed = hold;
    }

    /**
     * Accesseur, indique si la touche est appuyée ou non.
     *
     * @return La valeur <code>true</code> si la touche est appuyée, sinon
     * <code>false</code>
     */
    public boolean isKeyUp() {
        return pressed;
    }

    /**
     * Accesseur, indique si la touche est relâchée ou non.
     *
     * @return La valeur <code>true</code> si la touche est relâchée, sinon
     * <code>false</code>
     */
    public boolean isKeyDown() {
        return !pressed;
    }

    /**
     * Appuyer sur la touche. Cette méthode permet le traitement technique hors
     * temps de la boucle principale du jeu.
     */
    public void keyPressed() {
        hold = true;
    }

    /**
     * Relâcher la touche. Cette méthode permet le traitement technique hors de
     * la boucle principale du jeu.
     */
    public void keyReleased() {
        hold = false;
    }

    /**
     * Accesseur, obtenir le nom de la touche.
     *
     * @return Le nom de la touche
     */
    public GamePad getName() {
        return name;
    }

    /**
     * Accesseur, obtenir le code technique de la touche.
     *
     * @return Le code technique de la touche
     */
    public int getKeyCode() {
        return keyCode;
    }

}