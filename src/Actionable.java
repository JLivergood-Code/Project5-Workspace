import processing.core.PImage;

import java.util.List;

public interface Actionable extends Scheduable{
    //movable
    void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler);

    default void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore)
    {
        scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), this.getActionPeriod());
        Scheduable.super.scheduleActions(scheduler, world, imageStore);
    }
    default Action createActivityAction(WorldModel world, ImageStore imageStore)
    {
        return new Activity(this, world, imageStore);
    }

    double getActionPeriod();

}
