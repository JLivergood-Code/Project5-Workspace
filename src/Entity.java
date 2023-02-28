import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public interface Entity {


    String log();
    Point getPosition();
    void setPosition(Point position);
    String getId();
    int getImageIndex();




    //plant interface
    int getHealth();
    public void setHealth(int health);


    default Optional<Entity> nearestEntity(List<Entity> entities, Point pos) {
        if (entities.isEmpty()) {
            return Optional.empty();
        } else {
            Entity nearest = entities.get(0);
            int nearestDistance = Functions.distanceSquared(nearest.getPosition(), pos);

            for (Entity other : entities) {
                int otherDistance = Functions.distanceSquared(other.getPosition(), pos);

                if (otherDistance < nearestDistance) {
                    nearest = other;
                    nearestDistance = otherDistance;
                }
            }

            return Optional.of(nearest);
        }
    }
}
