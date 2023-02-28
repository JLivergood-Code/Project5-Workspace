public interface Plant extends Actionable{
    //plant interface
    int getHealth();
    void setHealth(int health);

    default void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        if (!this.transform(world, scheduler, imageStore)) {
            scheduler.scheduleEvent(this, Actionable.super.createActivityAction(world, imageStore), this.getActionPeriod());
        }
    }

    default boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore)
    {
        if (this.getHealth() <= 0) {
            Stump stump = Stump.createStump(WorldModel.getStumpKey() + "_" + this.getId(), this.getPosition(), imageStore.getImageList(WorldModel.getStumpKey()));

            world.removeEntity(scheduler, this);

            world.addEntity(stump);

            return true;
        }
        return false;
    }

}
