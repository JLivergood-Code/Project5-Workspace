import processing.core.PImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * An entity that exists in the world. See EntityKind for the
 * different kinds of entities that exist.
 */
public final class Obstacle implements Scheduable{

    private final String id;
    private Point position;
    private final List<PImage> images;
    private int imageIndex;
    private final double animationPeriod;

    //static
    public static Obstacle createObstacle(String id, Point position, double animationPeriod, List<PImage> images) {
        return new Obstacle(id, position, images,  animationPeriod);
    }



    public Obstacle(String id, Point position, List<PImage> images, double animationPeriod) {

        this.id= id;
        this.setPosition(position);
        this.images = images;
        this.imageIndex = 0;
        this.animationPeriod = animationPeriod;
    }



    /**
     * Helper method for testing. Preserve this functionality while refactoring.
     */

    public double getAnimationPeriod() {
        return this.animationPeriod;
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

}
