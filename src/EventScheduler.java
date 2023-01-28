import java.util.*;

/**
 * Keeps track of events that have been scheduled.
 */
public final class EventScheduler {
    private PriorityQueue<Event> eventQueue;
    private Map<Entity, List<Event>> pendingEvents;
    private double currentTime;

    public EventScheduler() {
        this.eventQueue = new PriorityQueue<>(new EventComparator());
        this.pendingEvents = new HashMap<>();
        this.setCurrentTime(0);
    }

    public void updateOnTime(double time) {
        double stopTime = getCurrentTime() + time;
        while (!eventQueue.isEmpty() && eventQueue.peek().time <= stopTime) {
            Event next = eventQueue.poll();
            removePendingEvent(next);
            setCurrentTime(next.time);
            next.action.executeAction(this);
        }
        setCurrentTime(stopTime);
    }

    public void scheduleEvent(Entity entity, Action action, double afterPeriod) {
        double time = this.getCurrentTime() + afterPeriod;

        Event event = new Event(action, time, entity);

        this.eventQueue.add(event);

        // update list of pending events for the given entity
        List<Event> pending = this.pendingEvents.getOrDefault(entity, new LinkedList<>());
        pending.add(event);
        this.pendingEvents.put(entity, pending);
    }

    public void unscheduleAllEvents(Entity entity) {
        List<Event> pending = this.pendingEvents.remove(entity);

        if (pending != null) {
            for (Event event : pending) {
                this.eventQueue.remove(event);
            }
        }
    }

    public void removePendingEvent(Event event) {
        List<Event> pending = pendingEvents.get(event.entity);

        if (pending != null) {
            pending.remove(event);
        }
    }

    public double getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(double currentTime) {
        this.currentTime = currentTime;
    }
}
