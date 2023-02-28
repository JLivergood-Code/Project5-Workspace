/**
 * An action that can be taken by an entity
 */
public interface Action {


    //turn this into an abstract method
    void executeAction(EventScheduler scheduler);


}
