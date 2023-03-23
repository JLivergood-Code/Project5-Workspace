import processing.core.PImage;

import java.util.List;

/**
 * An entity that exists in the world. See EntityKind for the
 * different kinds of entities that exist.
 */
public final class Skull implements Actionable{
    private final String id;
    private Point position;
    private final List<PImage> images;
    private int imageIndex;
    private final double actionPeriod;
    private final double animationPeriod;
    private int health;
    private final int healthLimit;

    /*-----------------------Keys---------------------------------------*/

    private static final double SKULL_ACTION_ANIMATION_PERIOD = .100; // have to be in sync since grows and gains health at same time
    private static final int SKULL_HEALTH_LIMIT = 16;



    //static
    // health starts at 0 and builds up until ready to convert to Tree
    public static Skull createSkull(String id, Point position, List<PImage> images) {
        return new Skull(id, position, images);
    }

    public Skull(String id, Point position, List<PImage> images) {
        this.id = id;
        this.position = position;
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
        setHealth(this.health + 1);
        if (!transform(world,scheduler,imageStore))
        {
            scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), this.getActionPeriod());
        }

    }

    public Action createActivityAction(WorldModel world, ImageStore imageStore) {
        return new Activity(this, world, imageStore);
    }

    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if (this.health >= this.healthLimit) {
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