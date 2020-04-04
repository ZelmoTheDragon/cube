package com.github.zelmothedragon.cube.core.system;

import com.github.zelmothedragon.cube.core.GameManager;
import com.github.zelmothedragon.cube.core.component.Block;
import com.github.zelmothedragon.cube.core.component.BoundedBox;
import com.github.zelmothedragon.cube.core.component.Movable;
import com.github.zelmothedragon.cube.core.entity.Entity;
import com.github.zelmothedragon.cube.core.entity.geometry.Vector;
import com.github.zelmothedragon.cube.core.graphic.Renderer;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Système de collision.
 *
 * @author MOSELLE Maxime
 */
public final class CollisionSystem extends AbstractSystem {
    
    public CollisionSystem(final GameManager manager, final int priority) {
        super(manager, priority);
    }
    
    @Override
    public void update() {
        var solidBlocks = manager
                .getEntities()
                .filter(BoundedBox.class)
                .stream()
                .map(e -> e.getComponent(BoundedBox.class))
                .filter(e -> Objects.equals(e.getBlock(), Block.SOLID))
                .collect(Collectors.toList());
        
        manager
                .getEntities()
                .filter(Movable.class)
                .forEach(e -> checkCollision(solidBlocks, e));
    }
    
    @Override
    public void draw(final Renderer<?> renderer) {
    }
    
    private static void checkCollision(final List<BoundedBox> solidBlocks, final Entity entity) {
        
        var vector = entity.getComponent(Vector.class);
        var box = entity.getComponent(BoundedBox.class);
        var bound = box.getBound();
        
        solidBlocks
                .stream()
                .filter(b -> !Objects.equals(b, box))
                .map(b -> b.getBound())
                .filter(b -> b.intersects(bound))
                .map(b -> b.createIntersection(bound))
                .forEach(r -> bound.move(r.getWidth() * -vector.getDx(), r.getHeight() * -vector.getDy()));
    }
    
}
