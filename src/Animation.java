/**
 * An action that can be taken by an entity
 */
public final class Animation implements Action{

    private final Scheduable entity;
    private final int repeatCount;

    //get rid of this, no constructor for
    public Animation(Scheduable entity, int repeatCount) {
        this.entity = entity;
        this.repeatCount = repeatCount;
    }

    //turn this into an abstract method


    public void executeAction(EventScheduler scheduler) {
        this.entity.nextImage();

        if (this.repeatCount != 1) {
            scheduler.scheduleEvent(this.entity, this.entity.createAnimationAction(Math.max(this.repeatCount - 1, 0)), this.entity.getAnimationPeriod());
        }
    }
}
