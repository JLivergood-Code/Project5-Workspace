/**
 * An action that can be taken by an entity
 */
public final class Activity implements Action{

    private Actionable entity;
    private final WorldModel world;
    private final ImageStore imageStore;


    //get rid of this, no constructor for
    public Activity(Actionable entity, WorldModel world, ImageStore imageStore) {
        this.entity = entity;
        this.world = world;
        this.imageStore = imageStore;
    }

    public void executeAction(EventScheduler scheduler) {
        this.entity.executeActivity(this.world, this.imageStore, scheduler);
    }
}
