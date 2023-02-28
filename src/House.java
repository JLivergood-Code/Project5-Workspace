import processing.core.PImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * An entity that exists in the world. See EntityKind for the
 * different kinds of entities that exist.
 */
public final class House implements Entity{
    private EntityKind kind;
    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;
    private int resourceLimit;
    private int resourceCount;
    private double actionPeriod;
    private double animationPeriod;
    private int health;
    private int healthLimit;

    /*-----------------------Keys---------------------------------------*/

    private static final double SAPLING_ACTION_ANIMATION_PERIOD = 1.000; // have to be in sync since grows and gains health at same time
    private static final int SAPLING_HEALTH_LIMIT = 5;

    private static final double TREE_ANIMATION_MAX = 0.600;
    private static final double TREE_ANIMATION_MIN = 0.050;
    private static final double TREE_ACTION_MAX = 1.400;
    private static final double TREE_ACTION_MIN = 1.000;
    private static final int TREE_HEALTH_MAX = 3;
    private static final int TREE_HEALTH_MIN = 1;


    public static House createHouse(String id, Point position, List<PImage> images) {
        return new House( id, position, images, 0, 0, 0, 0, 0, 0);
    }


    public House(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, double actionPeriod, double animationPeriod, int health, int healthLimit) {
        this.setId(id);
        this.setPosition(position);
        this.setImages(images);
        this.setImageIndex(0);
        this.resourceLimit = resourceLimit;
        this.resourceCount = resourceCount;
        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;
        this.setHealth(health);
        this.healthLimit = healthLimit;
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
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {

        throw new UnsupportedOperationException(String.format("scheduleActions not supported for %s", this.getClass()));
    }


    public Action createAnimationAction(int repeatCount) {
        return new Animation( this, repeatCount);
    }

    public Action createActivityAction(WorldModel world, ImageStore imageStore) {
        return new Activity(this, world, imageStore);
    }


    /*----------------------------------Getters ands Setters------------------------------------------------------------
    --------------------------------------------------------------------------------------------------------------------
     */

    public void nextImage() {
        this.setImageIndex(this.getImageIndex() + 1);
    }


    public Point getPosition() { return position; }

    public void setPosition(Point position) { this.position = position; }

    public void setKind(EntityKind kind) { this.kind = kind; }

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
