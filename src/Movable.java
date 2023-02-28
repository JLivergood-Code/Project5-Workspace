public interface Movable extends Actionable{

    //
    default boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler) {
        if (Functions.adjacent(this.getPosition(), target.getPosition())) {
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
        int horiz = Integer.signum(destPos.getX() - this.getPosition().getX());
        Point newPos = new Point(this.getPosition().getX() + horiz, this.getPosition().getY());

        if (horiz == 0 || world.isOccupied(newPos) && world.getOccupancyCell(newPos).getClass() != Stump.class) {
            int vert = Integer.signum(destPos.getY() - this.getPosition().getY());
            newPos = new Point(this.getPosition().getX(), this.getPosition().getY() + vert);

            if (vert == 0 || world.isOccupied(newPos) && world.getOccupancyCell(newPos).getClass() != Stump.class) {
                newPos = this.getPosition();
            }
        }

        return newPos;
    }

    //move code out of transformFull nad transformNotFull
    default void transform(Movable dude, WorldModel world, EventScheduler scheduler, ImageStore imageStore) {

        world.removeEntity(scheduler, this);

        world.addEntity(dude);
        dude.scheduleActions(scheduler, world, imageStore);
    }
}
