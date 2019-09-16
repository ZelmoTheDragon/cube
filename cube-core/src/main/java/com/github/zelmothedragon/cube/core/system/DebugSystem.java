package com.github.zelmothedragon.cube.core.system;

import com.github.zelmothedragon.cube.core.GameContainer;
import com.github.zelmothedragon.cube.core.asset.AssetManager;
import com.github.zelmothedragon.cube.core.entity.debug.Clock;
import com.github.zelmothedragon.cube.core.entity.geometry.Point;
import com.github.zelmothedragon.cube.core.entity.geometry.Vector;
import com.github.zelmothedragon.cube.core.graphic.AnimatedSprite;
import com.github.zelmothedragon.cube.core.graphic.FontSprite;
import com.github.zelmothedragon.cube.core.graphic.Pixel;
import com.github.zelmothedragon.cube.core.graphic.Render;
import com.github.zelmothedragon.cube.core.graphic.Sprite;
import com.github.zelmothedragon.cube.core.input.GamePad;
import java.util.UUID;

/**
 * Système de déboggage.
 *
 * @author MOSELLE Maxime
 */
public final class DebugSystem extends AbstractSystem {

    private final UUID debug;

    private final UUID player;

    private final Sprite background;

    private final Sprite wood;

    public DebugSystem(final GameContainer gc, final int priority) {
        super(gc, priority);

        this.background = gc.getAssets().loadSprite(AssetManager.DEBUG_BACKGROUND_IMAGE);
        this.wood = gc.getAssets().loadSprite(AssetManager.DEBUG_WOOD_IMAGE);

        this.debug = gc.getFactory().createDebugInformation();
        this.player = gc.getFactory().createDebugPlayer();
    }

    @Override
    public void update() {

        var playerVector = gc.getEntities().getComponent(player, Vector.class);
        var playerPoint = gc.getEntities().getComponent(player, Point.class);
        var playerAnimation = gc.getEntities().getComponent(player, AnimatedSprite.class);

        if (gc.getInputs().isKeyUp(GamePad.LEFT)) {
            playerVector.setDx(-1);
            playerAnimation.setOffset(0, 96);
            playerAnimation.play();
        } else if (gc.getInputs().isKeyUp(GamePad.RIGHT)) {
            playerVector.setDx(1);
            playerAnimation.setOffset(0, 32);
            playerAnimation.play();
        } else if (gc.getInputs().isKeyUp(GamePad.UP)) {
            playerVector.setDy(-1);
            playerAnimation.setOffset(0, 64);
            playerAnimation.play();
        } else if (gc.getInputs().isKeyUp(GamePad.DOWN)) {
            playerVector.setDy(1);
            playerAnimation.setOffset(0, 0);
            playerAnimation.play();
        } else {
            playerAnimation.stop();
        }

        playerPoint.move(playerVector);
        playerVector.reset();
        playerAnimation.update(1.0);

        var debugClock = gc.getEntities().getComponent(debug, Clock.class);
        debugClock.update();
    }

    @Override
    public void draw(final Render g2d) {

        g2d.drawImage(0, 0, background);
        g2d.drawImage(64, 64, wood);

        var playerPoint = gc.getEntities().getComponent(player, Point.class);
        var playerAnimation = gc.getEntities().getComponent(player, AnimatedSprite.class);

        var playerSprite = Pixel.scale(playerAnimation.getCurrentSprite(), 3);
        g2d.drawImage(playerPoint.getXp(), playerPoint.getYp(), playerSprite);

        g2d.drawRect(0, 0, 64, 64, 0xFFFF0000);
        g2d.drawGradientCircle(0, 0, 32, 0xFFFF0000);

        g2d.drawRect(64, 0, 64, 64, 0xFF00FF00);
        g2d.drawGradientCircle(64, 0, 32, 0xFF00FF00);

        g2d.drawRect(128, 0, 64, 64, 0xFF0000FF);
        g2d.drawGradientCircle(128, 0, 32, 0xFF0000FF);

        g2d.drawRect(192, 0, 64, 64, 0xFFFFFFFF);
        g2d.drawGradientCircle(192, 0, 32, 0xFFFFFFFF);

        g2d.drawRect(256, 0, 64, 64, 0xFF000000);
        g2d.drawGradientCircle(256, 0, 32, 0xFF000000);

        var debugPoint = gc.getEntities().getComponent(debug, Point.class);
        var debugFont = gc.getEntities().getComponent(debug, FontSprite.class);
        var debugClock = gc.getEntities().getComponent(debug, Clock.class);
        debugClock.render();

        g2d.drawImage(debugPoint.getXp(), debugPoint.getYp(), debugFont, debugClock.getMessage());

    }

}
