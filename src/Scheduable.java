public interface Scheduable extends Entity{

    void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore);
    double getAnimationPeriod();
    void nextImage();
    Action createAnimationAction(int repeatCount);
}
