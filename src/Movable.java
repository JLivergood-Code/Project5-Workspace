import java.util.List;
import java.util.function.Predicate;
import java.util.function.BiPredicate;


public interface Movable extends Actionable{

    //
    default boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler) {
        if (Point.adjacent(this.getPosition(), target.getPosition())) {
            moveHelper(world, target, scheduler);
            return true;
        } else {
            Point nextPos = this.nextPosition(world, target.getPosition());

            if (!this.getPosition().equals(nextPos)) {
                world.moveEntity(scheduler, this, nextPos);
            }
            return false;
        }
    }

    void moveHelper(WorldModel world, Entity target, EventScheduler scheduler);

    default Point nextPosition(WorldModel world, Point destPos) {

        PathingStrategy strat = new AStarPathingStrategy();
        //simply change the SingleStepPathingStrategy to AStarPathingStrategy when A* is created

        List<Point> path = strat.computePath(getPosition(), destPos, pos -> pos.equals(destPos) || world.withinBounds(pos) && (!world.isOccupied(pos) || posHelper(world, pos)), Point::adjacent, PathingStrategy.CARDINAL_NEIGHBORS);

        if (path.size() > 0)//add statement to check if the path is null or empty
        {
            return path.get(0);
        }
        else {
            return getPosition();
        }



//        int horiz = Integer.signum(destPos.getX() - this.getPosition().getX());
//        Point newPos = new Point(this.getPosition().getX() + horiz, this.getPosition().getY());
//
//        //fix bug, fairy does not need the second half, add a helper method which returns this logic for dudes and true for fairies
//        if (horiz == 0 || !world.isOccupied(newPos) && !posHelper(world, newPos)) {
//            int vert = Integer.signum(destPos.getY() - this.getPosition().getY());
//            newPos = new Point(this.getPosition().getX(), this.getPosition().getY() + vert);
//
//            if (vert == 0 || !world.isOccupied(newPos) && !posHelper(world, newPos)) {
//                newPos = this.getPosition();
//            }
//        }
//        return newPos;
    }

    boolean posHelper(WorldModel world, Point newPos);

    //move code out of transformFull nad transformNotFull
    default void transform(Movable dude, WorldModel world, EventScheduler scheduler, ImageStore imageStore) {

        world.removeEntity(scheduler, this);

        world.addEntity(dude);
        dude.scheduleActions(scheduler, world, imageStore);
    }
}
