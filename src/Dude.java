public interface Dude extends Movable {

    int getHealth();
    void setHealth(int health);

    default boolean transformSkeleton(WorldModel world, EventScheduler scheduler, ImageStore imageStore)
    {
        if (this.getHealth() <= 0) {
            System.out.println("Dude Transformed Into Skeleton!");
            Skeleton skeleton = Skeleton.createSkeleton("skeleton" + "_" + this.getId(), this.getPosition(), VirtualWorld.SKELETON_ACTION_VALUE, VirtualWorld.SKELETON_ANIMATION_VALUE, imageStore.getImageList("skeleton"), VirtualWorld.SKELETON_HEALTH);

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
