import processing.core.PImage;

import java.util.List;


/**
 * An entity that exists in the world. See EntityKind for the
 * different kinds of entities that exist.
 */
public final class House implements Entity{
    private final String id;
    private Point position;
    private final List<PImage> images;
    private int imageIndex;



    public static House createHouse(String id, Point position, List<PImage> images) {
        return new House( id, position, images);
    }


    public House(String id, Point position, List<PImage> images) {
        this.id = id;
        this.setPosition(position);
        this.images = images;
        this.imageIndex = 0;
    }



    /**
     * Helper method for testing. Preserve this functionality while refactoring.
     */
    public PImage getCurrentImage() {
        return getImages().get(this.getImageIndex() % this.getImages().size());
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
