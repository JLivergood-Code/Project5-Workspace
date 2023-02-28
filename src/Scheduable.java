public interface Scheduable extends Entity{

    default void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore)
    {
        scheduler.scheduleEvent(this, createAnimationAction(0), this.getAnimationPeriod());
    }
    double getAnimationPeriod();
    void nextImage();
    default Action createAnimationAction(int repeatCount)
    {
        return new Animation( this, repeatCount);
    }
}
