public interface Dude extends Movable {

    int getHealth();
    void setHealth(int health);

    default boolean transformSkeleton(WorldModel world, EventScheduler scheduler, ImageStore imageStore)
    {
        if (this.getHealth() <= 0) {
            Skeleton skeleton = Skeleton.createSkeleton("skeleton" + "_" + this.getId(), this.getPosition(), 0.5, 0.4, imageStore.getImageList("skeleton"), 3);

            world.removeEntity(scheduler, this);

            world.addEntity(skeleton);
            skeleton.scheduleActions(scheduler, world, imageStore);
            return true;
        }
        return false;
    }

    default boolean posHelper(WorldModel world, Point newPos)
    {
        return world.getOccupancyCell(newPos).getClass() == Stump.class;
    }

}
