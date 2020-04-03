package com.github.zelmothedragon.cube.core.entity;

import com.github.zelmothedragon.cube.core.asset.AssetManager;
import com.github.zelmothedragon.cube.core.entity.behavior.Controllable;
import com.github.zelmothedragon.cube.core.entity.data.Mandelbrot;
import com.github.zelmothedragon.cube.core.entity.debug.Clock;
import com.github.zelmothedragon.cube.core.entity.geometry.Camera;
import com.github.zelmothedragon.cube.core.entity.geometry.Dimension;
import com.github.zelmothedragon.cube.core.entity.geometry.Orientation;
import com.github.zelmothedragon.cube.core.entity.geometry.Point;
import com.github.zelmothedragon.cube.core.entity.geometry.Rectangle;
import com.github.zelmothedragon.cube.core.entity.geometry.Vector;
import com.github.zelmothedragon.cube.core.entity.image.AnimatedImage;
import com.github.zelmothedragon.cube.core.entity.image.AnimatedImageMetaData;
import com.github.zelmothedragon.cube.core.entity.image.FontImage;
import com.github.zelmothedragon.cube.core.entity.image.Image;
import com.github.zelmothedragon.cube.core.entity.image.ImageMap;

/**
 * Fabrique d'entités. Une instance unique de cette classe est requise pour le
 * fonctionnement de l'application. Le gestionnaire doit être accessible depuis
 * le conteneur du jeu.
 *
 * @see GameContainer
 *
 * @author MOSELLE Maxime
 */
public final class EntityFactory {

    /**
     * Gestionnaire d'entités.
     */
    private final EntityManager entities;

    /**
     * Gestionnaire des ressources numériques.
     */
    private final AssetManager<?> assets;

    /**
     * Constructeur.Construit une fabrique d'entités. Pour le bon fonctionnement
     * du programme cette classe doit être instanciée une seul fois.
     *
     * @param entities Gestionnaire d'entités
     * @param assets Gestionnaire des ressources numériques
     */
    public EntityFactory(
            final EntityManager entities,
            final AssetManager<?> assets) {

        this.entities = entities;
        this.assets = assets;
    }

    /**
     * Fabriquer une entité pour les informations de débogage du jeu.
     *
     * @return L'entité de la famille <code>Family.DEBUG</code>
     */
    public Entity createDebugInformation() {

        var clock = new Clock();
        var point = new Point();
        var font = assets.loadFontImagge(
                AssetManager.DEBUG_8X8_TEXT_SHADOW,
                AssetManager.DEBUG_8X8_TEXT_MAP,
                8,
                8
        );

        var entity = new Entity(Family.DEBUG);
        entity.addComponent(clock);
        entity.addComponent(point);
        entity.addComponent(FontImage.class, font);
        entities.add(entity);
        return entity;
    }

    public Entity createDebugPlayer() {

        var w = 16;
        var h = 32;
        var dimension = new Dimension(w, h);
        var vector = new Vector();
        var animation = assets.loadAnimatedImage(
                AssetManager.DEBUG_PLAYER_IMAGE,
                w,
                h,
                75,
                4
        );

        var metadata = new AnimatedImageMetaData();
        metadata.addOffset(Orientation.LEFT, new Rectangle(new Point(0, 96), dimension));
        metadata.addOffset(Orientation.RIGHT, new Rectangle(new Point(0, 32), dimension));
        metadata.addOffset(Orientation.UP, new Rectangle(new Point(0, 64), dimension));
        metadata.addOffset(Orientation.DOWN, new Rectangle(new Point(0, 0), dimension));
        metadata.setOrientation(Orientation.DOWN);

        var rectangle = new Rectangle(new Point(0, 8), dimension);

        var entity = new Entity(Family.PLAYER);
        entity.addComponent(Controllable.INSTANCE);
        entity.addComponent(Camera.INSTANCE);
        entity.addComponent(vector);
        entity.addComponent(metadata);
        entity.addComponent(rectangle);
        entity.addComponent(AnimatedImage.class, animation);
        entities.add(entity);
        return entity;
    }

    public Entity createMapDebug() {

        var point = new Point();
        var image = assets.loadImageMap(
                AssetManager.DEBUG_BACKGROUND_IMAGE,
                AssetManager.DEBUG_BACKGROUND_MAP_LAYER_0,
                16,
                16
        );

        var entity = new Entity(Family.MAP_DEBUG);
        entity.addComponent(point);
        entity.addComponent(ImageMap.class, image);
        entities.add(entity);
        return entity;
    }

    public Entity createMandelbrot() {

        var w = 800;
        var h = w / 16 * 9;
        var vector = new Vector();
        var rectangle = new Rectangle(new Dimension(w, h));
        var image = assets.loadImage(w, h);
        var mandelbrot = new Mandelbrot(10, 10);

        var entity = new Entity(Family.MANDELBROT);
        entity.addComponent(Controllable.INSTANCE);
        entity.addComponent(vector);
        entity.addComponent(rectangle);
        entity.addComponent(mandelbrot);
        entity.addComponent(Image.class, image);
        entities.add(entity);
        return entity;
    }

}
