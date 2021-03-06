package com.github.zelmothedragon.cube.core.system;

import com.github.zelmothedragon.cube.core.GameManager;
import com.github.zelmothedragon.cube.core.model.BoundedBox;
import com.github.zelmothedragon.cube.core.model.Entity;
import com.github.zelmothedragon.cube.core.model.Camera;
import com.github.zelmothedragon.cube.core.graphic.Renderer;

/**
 * Gestion de la caméra.
 *
 * @author MOSELLE Maxime
 */
public final class CameraSystem extends AbstractSystem {

    /**
     * Constructeur. Constuire un système, une seule instance est nécessaire
     * pour le fonctionnemenr global de l'application. Le système doit être
     * instancier dans le gestionnaire de système.
     *
     * @param manager Gestionnaire du jeu
     * @param priority Priorié d'exécuter du système
     */
    CameraSystem(final GameManager manager, final int priority) {
        super(manager, priority);
    }

    @Override
    public void update() {

        manager
                .getEntities()
                .filter(Camera.class)
                .stream()
                .forEach(CameraSystem::updateCamera);
    }

    @Override
    public void draw(final Renderer<?> renderer) {

        // Appliquer la caméra pour le rendu.
        renderer.setOffset(
                Camera.INSTANCE.getXp(),
                Camera.INSTANCE.getYp()
        );

    }

    /**
     * Faire suivre la caméra par rapport à une entité.
     *
     * @param entity Une entité possédant l'instance de la caméra
     */
    private static void updateCamera(final Entity entity) {

        var camera = Camera.INSTANCE;
        if (entity.hasComponent(BoundedBox.class)) {
            var box = entity.getComponent(BoundedBox.class);
            camera.setXp(box.getBound().getXp());
            camera.setYp(box.getBound().getYp());
        }
    }

}
