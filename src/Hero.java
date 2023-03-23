import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Hero implements Movable{

    private final String id;
    private Point position;
    private final List<PImage> images;
    private int imageIndex;
    private final double actionPeriod;
    private final double animationPeriod;

    public static double HERO_ACTION_VALUE = 0.787;
    public static double HERO_ANIMATION_VALUE = 0.180;


    /*-----------------------Keys---------------------------------------*/


    // don't technically need resource count ... full
    public static Hero createHero(String id, Point position, double actionPeriod, double animationPeriod, List<PImage> images) {
        return new Hero(id, position, images, actionPeriod, animationPeriod);
    }


    public Hero(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod) {
        this.id = id;
        this.setPosition(position);
        this.images = images;
        this.imageIndex = 0;
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
        Optional<Entity> target = world.findNearest(this.getPosition(), new ArrayList<>(List.of(Skeleton.class)));

        if (target.isPresent()) {
            Point tgtPos = target.get().getPosition();

            //if the hero has moved next to the dude
            this.moveTo(world, target.get(), scheduler);
            scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), this.actionPeriod);

            }
        }

    public boolean posHelper(WorldModel world, Point newPos)
    {
        //heros can move over stumps
        return world.getOccupancyCell(newPos).getClass() == Stump.class;
    }

    @Override
    public void moveHelper(WorldModel world, Entity target, EventScheduler scheduler) {
        if(target instanceof Skeleton) {
            Skeleton skeleton = (Skeleton) target;
            skeleton.setHealth(skeleton.getHealth() - 1);
        }
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
