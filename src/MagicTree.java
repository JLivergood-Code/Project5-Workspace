import processing.core.PImage;

import java.util.List;

/**
 * An entity that exists in the world. See EntityKind for the
 * different kinds of entities that exist.
 */
public final class MagicTree implements Actionable,Plant {
    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;
    private final double actionPeriod;
    private final double animationPeriod;
    private int health;

    /*-----------------------Keys---------------------------------------*/


    //static
    public static MagicTree createMagicTree(String id, Point position, double actionPeriod, double animationPeriod, int health, List<PImage> images) {
        return new MagicTree(id, position, images, actionPeriod, animationPeriod, health);
    }


    public MagicTree(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod, int health) {
        this.id = id;
        this.setPosition(position);
        this.images = images;
        this.imageIndex = 0;
        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;
        this.setHealth(health);
    }
    public void transformDead(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        DeadTree deadTree = DeadTree.createDeadTree(DeadTree.DEADTREE_KEY + "_" + this.getId(), this.getPosition(), Functions.getNumFromRange(Sapling.TREE_ANIMATION_MAX, Sapling.TREE_ANIMATION_MIN),imageStore.getImageList(DeadTree.DEADTREE_KEY));

        world.removeEntity(scheduler, this);

        world.addEntity(deadTree);
        deadTree.scheduleActions(scheduler, world, imageStore);
    }



    /**
     * Helper method for testing. Preserve this functionality while refactoring.
     */

    public double getAnimationPeriod() {
        return this.animationPeriod;
    }

    public double getActionPeriod() {
        return this.actionPeriod;
    }


    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if (this.getHealth() <= 0) {
            MagicStump magicStump = MagicStump.createMagicStump("magicStump", this.getPosition(), imageStore.getImageList("magicStump"));

            world.removeEntity(scheduler, this);

            world.addEntity(magicStump);

            return true;
        }
        return false;
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

    public int getImageIndex()
    {
        return imageIndex;
    }

}