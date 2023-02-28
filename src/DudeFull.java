import java.util.*;

import processing.core.PImage;

/**
 * An entity that exists in the world. See EntityKind for the
 * different kinds of entities that exist.
 */
public final class DudeFull implements  Movable {
    private final String id;
    private Point position;
    private final List<PImage> images;
    private int imageIndex;
    private final int resourceLimit;
    private final double actionPeriod;
    private final double animationPeriod;


    /*-----------------------Keys---------------------------------------*/


    // don't technically need resource count ... full
    public static DudeFull createDudeFull(String id, Point position, double actionPeriod, double animationPeriod, int resourceLimit, List<PImage> images) {
        return new DudeFull(id, position, images, resourceLimit, actionPeriod, animationPeriod);
    }


    public DudeFull(String id, Point position, List<PImage> images, int resourceLimit, double actionPeriod, double animationPeriod) {
        this.id = id;
        this.setPosition(position);
        this.images = images;
        this.imageIndex = 0;
        this.resourceLimit = resourceLimit;
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
        Optional<Entity> fullTarget = world.findNearest(this.getPosition(), new ArrayList<>(List.of(House.class)));

        if (fullTarget.isPresent() && this.moveTo(world, fullTarget.get(), scheduler)) {
            this.transformFull(world, scheduler, imageStore);
        } else {
            scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), this.actionPeriod);
        }
    }



    @Override
    public void moveHelper(WorldModel world, Entity target, EventScheduler scheduler) {}

    public void transformFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        Movable dude = DudeNotFull.createDudeNotFull(this.getId(), this.position, this.actionPeriod, this.animationPeriod, this.resourceLimit, this.getImages());

        Movable.super.transform(dude, world, scheduler, imageStore);
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
        this.imageIndex = this.getImageIndex() + 1;
    }


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