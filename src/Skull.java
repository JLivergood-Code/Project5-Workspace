import processing.core.PImage;

import java.util.List;

/**
 * An entity that exists in the world. See EntityKind for the
 * different kinds of entities that exist.
 */
public final class Skull implements Scheduable{
    private final String id;
    private Point position;
    private final List<PImage> images;
    private int imageIndex;
    private final double actionPeriod;
    private final double animationPeriod;
    private int health;
    private final int healthLimit;

    /*-----------------------Keys---------------------------------------*/

    public static final double TREE_ANIMATION_MAX = 0.600;
    public static final double TREE_ANIMATION_MIN = 0.050;
    private static final double TREE_ACTION_MAX = 1.400;
    private static final double TREE_ACTION_MIN = 1.000;
    private static final int TREE_HEALTH_MAX = 3;
    private static final int TREE_HEALTH_MIN = 1;
    private static final double SKULL_ACTION_ANIMATION_PERIOD = 1.000; // have to be in sync since grows and gains health at same time
    private static final int SKULL_HEALTH_LIMIT = 16;
    public static final String MAGICTREE_KEY = "magicTree";



    //static
    // health starts at 0 and builds up until ready to convert to Tree
    public static Skull createSkull(String id, Point position, List<PImage> images) {
        return new Skull(id, position, images);
    }

    public Skull(String id, Point position, List<PImage> images) {
        this.id = id;
        this.setPosition(position);
        this.images = images;
        this.imageIndex = 0;
        this.health = 0;
        this.actionPeriod = SKULL_ACTION_ANIMATION_PERIOD;
        this.animationPeriod = SKULL_ACTION_ANIMATION_PERIOD;
        this.healthLimit = SKULL_HEALTH_LIMIT;
    }

    public double getAnimationPeriod() {
        return this.animationPeriod;
    }
    public double getActionPeriod() {
        return this.actionPeriod;
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        this.health = (this.health + 1);
        this.transform(world,scheduler,imageStore);
    }

    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {

        if (this.getHealth() >= this.healthLimit) {
            world.removeEntity(scheduler, this);
            return true;
        }
        return false;
    }





    public Action createAnimationAction(int repeatCount) {
        return new Animation( this, repeatCount);
    }
    public PImage getCurrentImage() {
        return getImages().get(this.getImageIndex() % this.getImages().size());
    }


    /*----------------------------------Getters ands Setters------------------------------------------------------------
    --------------------------------------------------------------------------------------------------------------------
     */

    public void nextImage() {
        this.imageIndex = this.getImageIndex() + 1;
    }


    public Point getPosition() { return position; }

    public void setPosition(Point position) { this.position = position; }

    public String getId() {
        return id;
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


    public int getImageIndex() {
        return imageIndex;
    }

}