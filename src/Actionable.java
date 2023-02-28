import processing.core.PImage;

import java.util.List;

public interface Actionable extends Entity{
    //movable
    void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler);

    Action createActivityAction(WorldModel world, ImageStore imageStore);



    //dude
    List<PImage> getImages();



}
