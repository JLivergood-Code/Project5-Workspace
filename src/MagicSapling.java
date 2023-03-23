import processing.core.PImage;

import java.util.List;

/**
 * An entity that exists in the world. See EntityKind for the
 * different kinds of entities that exist.
 */
public final class MagicSapling implements Plant{
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
    private static final double SAPLING_ACTION_ANIMATION_PERIOD = 1.000; // have to be in sync since grows and gains health at same time
    private static final int SAPLING_HEALTH_LIMIT = 5;
    public static final String MAGICTREE_KEY = "magicTree";



    //static
    // health starts at 0 and builds up until ready to convert to Tree
    public static MagicSapling createMagicSapling(String id, Point position, List<PImage> images, int health) {
        return new MagicSapling(id, position, images,  SAPLING_ACTION_ANIMATION_PERIOD, SAPLING_ACTION_ANIMATION_PERIOD, SAPLING_HEALTH_LIMIT);
    }

    public static MagicSapling createMagicSapling(String id, Point position, List<PImage> images, double action_period, double animationPeriod, int health) {
        return new MagicSapling(id, position, images, action_period, animationPeriod, health);
    }


    public MagicSapling(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod, int healthLimit) {
        this.id = id;
        this.setPosition(position);
        this.images = images;
        this.imageIndex = 0;
        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;
        this.healthLimit = healthLimit;
    }

    public double getAnimationPeriod() {
        return this.animationPeriod;
    }
    public double getActionPeriod() {
        return this.actionPeriod;
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        this.health = (this.health + 1);
        Plant.super.executeActivity(world, imageStore, scheduler);
    }

    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {

        if (!Plant.super.transform(world, scheduler, imageStore) && this.health >= this.healthLimit) {
            MagicTree magicTree = MagicTree.createMagicTree("magicTree",this.position, Functions.getNumFromRange(TREE_ACTION_MAX, TREE_ACTION_MIN), Functions.getNumFromRange(TREE_ANIMATION_MAX, TREE_ANIMATION_MIN), Functions.getIntFromRange(TREE_HEALTH_MAX, TREE_HEALTH_MIN), imageStore.getImageList("magicTree"));

            world.removeEntity(scheduler, this);

            world.addEntity(magicTree);
            magicTree.scheduleActions(scheduler, world, imageStore);

            return true;
        }

        return false;
    }





    public Action createAnimationAction(int repeatCount) {
        return new Animation( this, repeatCount);
    }

    public Action createActivityAction(WorldModel world, ImageStore imageStore) {
        return new Activity(this, world, imageStore);
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