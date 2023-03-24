import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class DeadObelisk implements Actionable{

    private final String id;
    private Point position;
    private final List<PImage> images;
    private int imageIndex;
//    private final double actionPeriod;
    private double animationPeriod;
    private int time;

    public static DeadObelisk createDeadObelisk(String id, Point position, double animationPeriod, List<PImage> images, int time) {
        return new DeadObelisk(id, position, images,  animationPeriod, time);
    }

    public DeadObelisk(String id, Point position, List<PImage> images, double animationPeriod, int time)
    {
        this.id = id;
        this.position = position;
        this.images = images;
        this.animationPeriod = animationPeriod;
        this.time = time;
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        this.time = (this.time - 1);
        if (!this.transform(world, scheduler, imageStore)) {
            scheduler.scheduleEvent(this, Actionable.super.createActivityAction(world, imageStore), this.getActionPeriod());
        }
    }

    @Override
    public double getActionPeriod() {
        return animationPeriod;
    }

    private void swap_reg_background(Point current, WorldModel world, ImageStore imageStore){
        if (world.getBackgroundCell(current).getImages().equals(imageStore.getImageList("deadGrass"))) {
            world.setBackgroundCell(current, new Background("grass", imageStore.getImageList("grass")));
        }
        else if (world.getBackgroundCell(current).getImages().equals(imageStore.getImageList("deadFlowers"))) {
            world.setBackgroundCell(current, new Background("flowers", imageStore.getImageList("flowers")));
        }
        else if (world.getBackgroundCell(current).getImages().equals(imageStore.getImageList("dead_dirt_bot_left_corner"))) {
            world.setBackgroundCell(current, new Background("dirt_bot_left_corner", imageStore.getImageList("dirt_bot_left_corner")));
        }
        else if (world.getBackgroundCell(current).getImages().equals(imageStore.getImageList("dead_dirt_bot_right_up"))) {
            world.setBackgroundCell(current, new Background("dirt_bot_right_up", imageStore.getImageList("dirt_bot_right_up")));
        }
        else if (world.getBackgroundCell(current).getImages().equals(imageStore.getImageList("dead_dirt_horiz"))) {
            world.setBackgroundCell(current, new Background("dirt_horiz", imageStore.getImageList("dirt_horiz")));
        }
        else if (world.getBackgroundCell(current).getImages().equals(imageStore.getImageList("dead_dirt_vert_right"))) {
            world.setBackgroundCell(current, new Background("dirt_vert_left", imageStore.getImageList("dirt_vert_left")));
        }
        else if (world.getBackgroundCell(current).getImages().equals(imageStore.getImageList("dead_dirt_vert_left_bot"))) {
            world.setBackgroundCell(current, new Background("dirt_vert_left_bot", imageStore.getImageList("dirt_vert_left_bot")));
        }
        else if (world.getBackgroundCell(current).getImages().equals(imageStore.getImageList("dead_dirt_vert_left"))) {
            world.setBackgroundCell(current, new Background("dirt_vert_right", imageStore.getImageList("dirt_vert_right")));
        }
    }

    private boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore)
    {
        if(time <= 0) {
            for (int row = Math.max(position.getY() - 10, 0); row < Math.min(position.getY() + 10, world.getNumRows()); row++) {
                for (int col = Math.max(position.getX() - 10, 0); col < Math.min(position.getX() + 10, world.getNumCols()); col++) {
                    Point current = new Point(col, row);

                    swap_reg_background(current, world, imageStore);
                }
            }
            swap_reg_background(position, world, imageStore);

            world.removeEntity(scheduler, this);
            return true;
        }
        return false;
    }


    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public void setPosition(Point position) {
        this.position = position;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public int getImageIndex() {
        return imageIndex;
    }

    @Override
    public List<PImage> getImages() {
        return images;
    }

    @Override
    public double getAnimationPeriod() {
        return animationPeriod;
    }

    @Override
    public void nextImage() {
        this.imageIndex = this.imageIndex +1;

    }
}
