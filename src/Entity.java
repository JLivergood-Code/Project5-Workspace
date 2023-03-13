import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public interface Entity {


    default String log()
    {
        return this.getId().isEmpty() ? null :
                String.format("%s %d %d %d", this.getId(), this.getPosition().getX(), this.getPosition().getY(), this.getImageIndex());
    }
    Point getPosition();
    void setPosition(Point position);
    String getId();
    int getImageIndex();

    default PImage getCurrentImage() {
        return getImages().get(this.getImageIndex() % this.getImages().size());
    }
    List<PImage> getImages();


    static Optional<Entity> nearestEntity(List<Entity> entities, Point pos) {
        if (entities.isEmpty()) {
            return Optional.empty();
        } else {
            Entity nearest = entities.get(0);
            int nearestDistance = Point.distanceSquared(nearest.getPosition(), pos);

            for (Entity other : entities) {
                int otherDistance = Point.distanceSquared(other.getPosition(), pos);

                if (otherDistance < nearestDistance) {
                    nearest = other;
                    nearestDistance = otherDistance;
                }
            }

            return Optional.of(nearest);
        }
    }
}
