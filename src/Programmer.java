import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * An entity that exists in the world. See EntityKind for the
 * different kinds of entities that exist.
 */
public final class Programmer implements Movable {
    private final String id;
    private Point position;
    private final List<PImage> images;
    private int imageIndex;
    private final double actionPeriod;
    private final double animationPeriod;

    //static
    public static Programmer createProgrammer(String id, Point position, double actionPeriod, double animationPeriod, List<PImage> images) {
        return new Programmer(id, position, images, actionPeriod, animationPeriod);
    }



    public Programmer(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod) {
        this.id = id;
        this.setPosition(position);
        this.images = images;
        this.imageIndex = 0;
        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;
    }

    //Actionable Methods
    public double getAnimationPeriod() {
        return this.animationPeriod;
    }
    public double getActionPeriod() {
        return this.actionPeriod;
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> progTarget = world.findNearest(this.getPosition(), new ArrayList<>(List.of(Fairy.class)));

        if (progTarget.isPresent()) {
            Point tgtPos = progTarget.get().getPosition();

            this.moveTo(world, progTarget.get(), scheduler);
            }
        scheduler.scheduleEvent(this, createActivityAction(world, imageStore), this.actionPeriod);
    }

    //Movable Methods
    public void moveHelper(WorldModel world, Entity target, EventScheduler scheduler)
    {
    }

    public boolean posHelper(WorldModel world, Point newPos)
    {
        return false;
    }
    //Entity Functions
    public PImage getCurrentImage() {
        return getImages().get(this.getImageIndex() % this.getImages().size());
    }

    public void nextImage() {
        this.imageIndex = this.getImageIndex() + 1;;
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