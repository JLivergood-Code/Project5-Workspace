import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Skeleton implements Movable {
    private final String id;
    private Point position;
    private final List<PImage> images;
    private int imageIndex;
    private final double actionPeriod;
    private final double animationPeriod;
    private int health;

    public Skeleton(String id, Point position, List<PImage> images, double actionPeriod, double animationPeriod, int health)
    {
        this.id = id;
        this.setPosition(position);
        this.images = images;
        this.imageIndex = 0;
        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;
        this.health = health;
    }

    public double getAnimationPeriod() {
        return this.animationPeriod;
    }
    public double getActionPeriod() {
        return this.actionPeriod;
    }

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
    public boolean transformSkull(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {

        if (this.getHealth() <= 0) {
            Skull skull = Skull.createSkull("skull", this.getPosition(),  imageStore.getImageList("skull"));

            world.removeEntity(scheduler, this);

            world.addEntity(skull);
            skull.scheduleActions(scheduler, world, imageStore);

            return true;
        }
        return false;
    }
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> target = world.findNearest(this.getPosition(), new ArrayList<>(List.of(DudeFull.class, DudeNotFull.class)));
        if (target.isPresent()) {
            moveTo(world, target.get(), scheduler);
            scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), this.actionPeriod);
        }
        if (!this.transformSkull(world, scheduler, imageStore))
        {
            scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), this.actionPeriod);
        }
    }

    public boolean posHelper(WorldModel world, Point newPos)
    {
        return false;
    }

    public void moveHelper(WorldModel world, Entity target, EventScheduler scheduler)
    {
        if(target instanceof Dude) {
            Dude dude = (Dude) target;
            dude.setHealth(dude.getHealth() - 1);
        }
    }

    public Action createAnimationAction(int repeatCount) {
        return new Animation( this, repeatCount);
    }

    public Action createActivityAction(WorldModel world, ImageStore imageStore) {
        return new Activity(this, world, imageStore);
    }

    public static Skeleton createSkeleton(String id, Point position, double actionPeriod, double animationPeriod, List<PImage> images, int health) {
        return new Skeleton(id, position, images, actionPeriod, animationPeriod, health);
    }
    public int getHealth(){return health;}
    public void setHealth(int health){this.health=health;}



}
