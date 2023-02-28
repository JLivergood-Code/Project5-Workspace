import java.util.*;

import processing.core.PImage;

/**
 * An entity that exists in the world. See EntityKind for the
 * different kinds of entities that exist.
 */
public final class Stump implements Entity {
    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;
    private int health;


    //static
    public static Stump createStump(String id, Point position, List<PImage> images) {
        return new Stump(id, position, images, 0, 0, 0, 0, 0, 0);
    }


    public Stump(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, double actionPeriod, double animationPeriod, int health, int healthLimit) {
        this.setId(id);
        this.setPosition(position);
        this.setImages(images);
        this.setImageIndex(0);
        this.setHealth(health);
    }



    /**
     * Helper method for testing. Preserve this functionality while refactoring.
     */
    public String log(){
        return this.getId().isEmpty() ? null :
                String.format("%s %d %d %d", this.getId(), this.getPosition().getX(), this.getPosition().getY(), this.getImageIndex());
    }

    public double getAnimationPeriod() {
        throw new UnsupportedOperationException(String.format("getAnimationPeriod not supported for %s", this.getClass()));
    }


    /*----------------------------------Getters ands Setters------------------------------------------------------------
    --------------------------------------------------------------------------------------------------------------------
     */

    public void nextImage() {
        this.setImageIndex(this.getImageIndex() + 1);
    }


    public Point getPosition() { return position; }

    public void setPosition(Point position) { this.position = position; }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public List<PImage> getImages() {
        return images;
    }

    public void setImages(List<PImage> images) {
        this.images = images;
    }

    public int getImageIndex() {
        return imageIndex;
    }

    public void setImageIndex(int imageIndex) {
        this.imageIndex = imageIndex;
    }
}