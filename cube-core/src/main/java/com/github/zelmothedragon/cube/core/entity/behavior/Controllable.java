package com.github.zelmothedragon.cube.core.entity.behavior;

import com.github.zelmothedragon.cube.core.entity.Component;

/**
 * Composant marqueur qui indique qu'une entité est contrôlable.
 *
 * @author MOSELLE Maxime
 */
public final class Controllable implements Component {

    /**
     * Singleton. Instance unique permet de marquer un entité commme
     * contrôlable.
     */
    public static final Controllable INSTANCE = new Controllable();

    /**
     * Constructeur interne.
     */
    private Controllable() {
        // RAS
    }

}
