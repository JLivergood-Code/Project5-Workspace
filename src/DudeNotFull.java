import java.util.*;

import processing.core.PImage;

/**
 * An entity that exists in the world. See EntityKind for the
 * different kinds of entities that exist.
 */
public final class DudeNotFull implements Movable {

    private final String id;
    private Point position;
    private final List<PImage> images;
    private int imageIndex;
    private final int resourceLimit;
    private int resourceCount;
    private final double actionPeriod;
    private final double animationPeriod;


    //static
    // need resource count, though it always starts at 0
    public static DudeNotFull createDudeNotFull(String id, Point position, double actionPeriod, double animationPeriod, int resourceLimit, List<PImage> images) {
        return new DudeNotFull(id, position, images, resourceLimit, 0, actionPeriod, animationPeriod);
    }



    public DudeNotFull(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, double actionPeriod, double animationPeriod) {
        this.id = id;
        this.setPosition(position);
        this.images = images;
        this.imageIndex = 0;
        this.resourceLimit = resourceLimit;
        this.resourceCount = resourceCount;
        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;
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
    //Entity
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> target = world.findNearest(this.getPosition(), new ArrayList<>(Arrays.asList(Tree.class, Sapling.class)));

        if (target.isEmpty() || !this.moveTo(world, target.get(), scheduler) || !this.transformNotFull(world, scheduler, imageStore)) {
            if (!this.skeletonTransform(world, scheduler, imageStore))
                scheduler.scheduleEvent(this, createActivityAction(world, imageStore), this.actionPeriod);
        }
    }

    public boolean transformNotFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if (this.resourceCount >= this.resourceLimit) {
            Movable dude = DudeFull.createDudeFull(this.id, this.position, this.actionPeriod, this.animationPeriod, this.resourceLimit, this.images);

            scheduler.unscheduleAllEvents(this);
            Movable.super.transform(dude, world, scheduler, imageStore);

            return true;
        }

        return false;
    }

    public boolean posHelper(WorldModel world, Point newPos)
    {

            return world.getOccupancyCell(newPos).getClass() == Stump.class;

    }

    public boolean skeletonTransform(WorldModel world, EventScheduler scheduler, ImageStore imageStore)
    {
        Optional<Entity> target = world.findNearest(this.getPosition(), new ArrayList<>(Arrays.asList(Skeleton.class)));
        if (target.isPresent() && Point.adjacent(target.get().getPosition(), this.position))
        {
            Skeleton newSkeleton = Skeleton.createSkeleton("skeleton", this.position, 0.5, 0.5, imageStore.getImageList("skeleton"), 1);

            world.removeEntity(scheduler, this);

            world.addEntity(newSkeleton);
            newSkeleton.scheduleActions(scheduler, world, imageStore);
            return true;
        }
        return false;
    }

    public void moveHelper(WorldModel world, Entity target, EventScheduler scheduler) {
        if(target instanceof Plant) {
            Plant p = (Plant) target;
            this.resourceCount += 1;
            p.setHealth(p.getHealth() - 1);
        }
    }

    public PImage getCurrentImage() {
        return getImages().get(this.getImageIndex() % this.getImages().size());
    }


    /*----------------------------------Getters ands Setters------------------------------------------------------------
    --------------------------------------------------------------------------------------------------------------------
     */

    public void nextImage() {
        this.imageIndex = this.getImageIndex() + 1;    }


    public Point getPosition() { return position; }

    public void setPosition(Point position) { this.position = position; }


    public String getId() {
        return id;
    }


    public List<PImage> getImages() {
        return images;
    }


    public int getImageIndex() {
        return imageIndex;
    }

}