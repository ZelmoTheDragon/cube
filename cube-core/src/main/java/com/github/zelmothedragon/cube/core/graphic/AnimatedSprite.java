package com.github.zelmothedragon.cube.core.graphic;

/**
 * Image animée sous forme de tableau de pixel.
 *
 * @author MOSELLE Maxime
 */
public final class AnimatedSprite extends Sprite {

    /**
     * Image de l'animation courante.
     */
    private final Sprite currentSprite;

    /**
     * Delais de l'animation.
     */
    private final int duration;

    /**
     * Nombre d'image pour une animation complète.
     */
    private final int count;

    /**
     * Index de l'image courante.
     */
    private int frame;

    /**
     * Décalage en abcisse. Ce décalage indique à partir d'où l'image initiale
     * doit être lu depuis la feuille d'image.
     */
    private int xOffset;

    /**
     * Décalage en ordonnée. Ce décalage indique à partir d'où l'image initiale
     * doit être lu depuis la feuille d'image.
     */
    private int yOffset;

    /**
     * Indique si l'animastion est en cours d'exécution.
     */
    private boolean playing;

    /**
     * Constructeur. Construit une image animée.
     *
     * @param spriteSheet Feuille d'image contenant toutes les animations
     * @param duration Delais de l'animation
     * @param count Nombre d'image composant une animation
     * @param spriteWidth Largeur en pixel d'une image composant l'animation
     * @param spriteHeight Hauteur en pixel d'une image composant l'animation
     */
    public AnimatedSprite(
            final Sprite spriteSheet,
            final int duration,
            final int count,
            final int spriteWidth,
            final int spriteHeight) {

        super(spriteSheet.width, spriteSheet.height, spriteSheet.buffer);

        this.currentSprite = new Sprite(spriteWidth, spriteHeight);
        this.duration = duration;
        this.count = count;
        this.frame = 0;
        this.xOffset = 0;
        this.yOffset = 0;
        this.playing = false;

    }

    /**
     * Mettre à jour l'animation.
     *
     * @param deltaTime Valeur d'interpolation pour ajuster l'animation en
     * fonction de la fréquence d'horloge du jeu
     */
    public void update(final double deltaTime) {

        // Réinitialiser l'index de l'animation lorsque qu'un cycle complet
        // de l'animation est exécuter.
        if (playing) {
            frame++;
            if (frame > duration) {
                frame = 0;
            }
        }

        // Calculer l'index et la position de la prochaine image.
        var frac = frame * count / duration;
        var index = Math.min((int) Math.floor(frac), count - 1);
        var columns = width / currentSprite.width;
        var xp = (index % columns) * currentSprite.width + xOffset;
        var yp = (index / columns) * currentSprite.height + yOffset;

        // Effacer l'image courante.
        currentSprite.clear();

        // Extraire de la feuille d'image
        // l'image de l'animation courante.
        for (var y = 0; y < currentSprite.height; y++) {
            var ya = yp + y;

            for (var x = 0; x < currentSprite.width; x++) {
                var xa = xp + x;
                var pixel = getPixel(xa, ya);
                currentSprite.setPixel(x, y, pixel);
            }
        }
    }

    /**
     * Lancer l'animation.
     */
    public void play() {
        playing = true;
    }

    /**
     * Mettre en pause l'animation.
     */
    public void pause() {
        playing = false;
    }

    /**
     * Arrêter l'animation.
     */
    public void stop() {
        playing = false;
        frame = 0;
    }

    /**
     * Changer le décalage.
     *
     * @param xOffset Décalage en abcisse
     * @param yOffset Décalage en ordonnée
     */
    public void setOffset(final int xOffset, final int yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    /**
     * Accesseur, obtenir l'image de l'animation courante.
     *
     * @return L'image de l'animation courante
     */
    public Sprite getCurrentSprite() {
        return currentSprite;
    }

    /**
     * Accesseur, indiquer si l'animation est en cours d'exécution.
     *
     * @return La valeur <code>true</code> si l'animation est en cours, sinon la
     * valeur <code>false</code>
     */
    public boolean isPlaying() {
        return playing;
    }

}