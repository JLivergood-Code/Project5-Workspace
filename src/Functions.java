import java.util.*;

import processing.core.PImage;
import processing.core.PApplet;

/**
 * This class contains many functions written in a procedural style.
 * You will reduce the size of this class over the next several weeks
 * by refactoring this codebase to follow an OOP style.
 */
public final class Functions {
    public static final Random rand = new Random();

    public static final int COLOR_MASK = 0xffffff;
    public static final int KEYED_IMAGE_MIN = 5;
    private static final int KEYED_RED_IDX = 2;
    private static final int KEYED_GREEN_IDX = 3;
    private static final int KEYED_BLUE_IDX = 4;

    private static final List<String> PATH_KEYS = new ArrayList<>(Arrays.asList("bridge", "dirt", "dirt_horiz", "dirt_vert_left", "dirt_vert_right", "dirt_bot_left_corner", "dirt_bot_right_up", "dirt_vert_left_bot"));

    public static final double SAPLING_ACTION_ANIMATION_PERIOD = 1.000; // have to be in sync since grows and gains health at same time
    public static final int SAPLING_HEALTH_LIMIT = 5;


    public static final int PROPERTY_ID = 1;
    public static final int PROPERTY_COL = 2;
    public static final int PROPERTY_ROW = 3;
    public static final int ENTITY_NUM_PROPERTIES = 4;















    // Entity
    //getAnimationPeriod

    // Entity
    // nextImage


    //Action
    //executeAction


    // Action
    //executeAnimationAction


    //Action
    //executeActivityAnimation


    //Entity
    //executeSaplingActivity
//    public static void executeSaplingActivity(Entity entity, WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
//        entity.health++;
//        if (!transformPlant(entity, world, scheduler, imageStore)) {
//            scheduleEvent(scheduler, entity, createActivityAction(entity, world, imageStore), entity.actionPeriod);
//        }
//    }

    //Entity
    //executeTreeActivity


    //Entity
    //executeFairyActivity


    //Entity
    //executeDudeNotFullActivity


    //Entity
    //executeDudeFullActivity

    //Entity
    //scheduleActions


    //Entity
    //transfromNotFull


    //Entity
    //transformFull


    //Entity
    //transformPlant


    //Entity
    //transformTree


    //Entity
    //transformSapling


    //Entity
    //moveToFairy


    //entity
    //moveToNotFull


    //entity
    //moveToFull


    //entity
    //nextPositionFairy


    //entity
    //nextPositionDude


    //stay
    public static boolean adjacent(Point p1, Point p2) {
        return (p1.x == p2.x && Math.abs(p1.y - p2.y) == 1) || (p1.y == p2.y && Math.abs(p1.x - p2.x) == 1);
    }

    //stay
    public static int getIntFromRange(int max, int min) {
        Random rand = new Random();
        return min + rand.nextInt(max-min);
    }

    //stay
    public static double getNumFromRange(double max, double min) {
        Random rand = new Random();
        return min + rand.nextDouble() * (max - min);
    }

    //EventScheduler
    //scheduleEvent

    //EventScheduler
    //unscheduleAllEvents


    //Event Scheduler
    //removePendingEvent

    //eventScheduler
    //updateOnTime


    //WorldModel
    //parseSapling

    //WorldModel
    //parseDude

    //WorldModel
    //parseFairy


    //WorldModel
    //parseTree


    //WorldModel
    //parseObstacle


    //static
    //parseHouse


    //static


    //WorldModel
    //tryAddEntity

    //worldmodel
    //withinBounds


    //worldmodel
    //isOccupied


    //static
    public static Optional<Entity> nearestEntity(List<Entity> entities, Point pos) {
        if (entities.isEmpty()) {
            return Optional.empty();
        } else {
            Entity nearest = entities.get(0);
            int nearestDistance = distanceSquared(nearest.getPosition(), pos);

            for (Entity other : entities) {
                int otherDistance = distanceSquared(other.getPosition(), pos);

                if (otherDistance < nearestDistance) {
                    nearest = other;
                    nearestDistance = otherDistance;
                }
            }

            return Optional.of(nearest);
        }
    }

    //static or Point
    public static int distanceSquared(Point p1, Point p2) {
        int deltaX = p1.x - p2.x;
        int deltaY = p1.y - p2.y;

        return deltaX * deltaX + deltaY * deltaY;
    }

    //WorldModel
    //findNearest

    /*
       Assumes that there is no entity currently occupying the
       intended destination cell.
    */

    //worldmodel
    //addEntity

    //WorldModel
    //moveEntity

    //Worldmodel, same as addEntity
    //removeEntity

    //worldmodel
    //removeEntityAt


    //worldmodel
    //getOccupant

    //worldemodel
    //getOccupancyCell

    //worldmodel


    //Entity
    //createAnimationAction


    //Entity
    //createActivityAction


    //static
    public static Entity createHouse(String id, Point position, List<PImage> images) {
        return new Entity(EntityKind.HOUSE, id, position, images, 0, 0, 0, 0, 0, 0);
    }

    //static
    public static Entity createObstacle(String id, Point position, double animationPeriod, List<PImage> images) {
        return new Entity(EntityKind.OBSTACLE, id, position, images, 0, 0, 0, animationPeriod, 0, 0);
    }

    //static
    public static Entity createTree(String id, Point position, double actionPeriod, double animationPeriod, int health, List<PImage> images) {
        return new Entity(EntityKind.TREE, id, position, images, 0, 0, actionPeriod, animationPeriod, health, 0);
    }

    //static
    public static Entity createStump(String id, Point position, List<PImage> images) {
        return new Entity(EntityKind.STUMP, id, position, images, 0, 0, 0, 0, 0, 0);
    }

    //static
    // health starts at 0 and builds up until ready to convert to Tree
    public static Entity createSapling(String id, Point position, List<PImage> images, int health) {
        return new Entity(EntityKind.SAPLING, id, position, images, 0, 0, SAPLING_ACTION_ANIMATION_PERIOD, SAPLING_ACTION_ANIMATION_PERIOD, 0, SAPLING_HEALTH_LIMIT);
    }

    //static
    public static Entity createFairy(String id, Point position, double actionPeriod, double animationPeriod, List<PImage> images) {
        return new Entity(EntityKind.FAIRY, id, position, images, 0, 0, actionPeriod, animationPeriod, 0, 0);
    }

    //static
    // need resource count, though it always starts at 0
    public static Entity createDudeNotFull(String id, Point position, double actionPeriod, double animationPeriod, int resourceLimit, List<PImage> images) {
        return new Entity(EntityKind.DUDE_NOT_FULL, id, position, images, resourceLimit, 0, actionPeriod, animationPeriod, 0, 0);
    }

    //static
    // don't technically need resource count ... full
    public static Entity createDudeFull(String id, Point position, double actionPeriod, double animationPeriod, int resourceLimit, List<PImage> images) {
        return new Entity(EntityKind.DUDE_FULL, id, position, images, resourceLimit, 0, actionPeriod, animationPeriod, 0, 0);
    }

    //worldmodel
    //load

    //worldmodel
    //parseSaveFile

    //worldmodel
    //parsebackgroundRow


    //worldmodel
    //parseEntity


    //static
    public static PImage getCurrentImage(Object object) {
        if (object instanceof Background background) {
            return background.images.get(background.imageIndex);
        } else if (object instanceof Entity entity) {
            return entity.getImages().get(entity.getImageIndex() % entity.getImages().size());
        } else {
            throw new UnsupportedOperationException(String.format("getCurrentImage not supported for %s", object));
        }
    }

    //worldmodel
    //getBackgroundCell

    //worldmodel
    //setBackgroundCell


    //Viewport
    //viewportToWorld

    //Viewport
    //worldToViewport

    //static
    public static int clamp(int value, int low, int high) {
        return Math.min(high, Math.max(value, low));
    }

    //Worldview
    //shiftView

    //static
    public static void processImageLine(Map<String, List<PImage>> images, String line, PApplet screen) {
        String[] attrs = line.split("\\s");
        if (attrs.length >= 2) {
            String key = attrs[0];
            PImage img = screen.loadImage(attrs[1]);
            if (img != null && img.width != -1) {
                List<PImage> imgs = getImages(images, key);
                imgs.add(img);

                if (attrs.length >= KEYED_IMAGE_MIN) {
                    int r = Integer.parseInt(attrs[KEYED_RED_IDX]);
                    int g = Integer.parseInt(attrs[KEYED_GREEN_IDX]);
                    int b = Integer.parseInt(attrs[KEYED_BLUE_IDX]);
                    setAlpha(img, screen.color(r, g, b), 0);
                }
            }
        }
    }

    //static
    public static List<PImage> getImages(Map<String, List<PImage>> images, String key) {
        return images.computeIfAbsent(key, k -> new LinkedList<>());
    }

    /*
      Called with color for which alpha should be set and alpha value.
      setAlpha(img, color(255, 255, 255), 0));
    */
    //static
    public static void setAlpha(PImage img, int maskColor, int alpha) {
        int alphaValue = alpha << 24;
        int nonAlpha = maskColor & COLOR_MASK;
        img.format = PApplet.ARGB;
        img.loadPixels();
        for (int i = 0; i < img.pixels.length; i++) {
            if ((img.pixels[i] & COLOR_MASK) == nonAlpha) {
                img.pixels[i] = alphaValue | nonAlpha;
            }
        }
        img.updatePixels();
    }

    //Viewport
    //shift

    //Viewport
    //contains


    //WorldModel?
//    public static Optional<PImage> getBackgroundImage(WorldModel world, Point pos) {
//        if (withinBounds(world, pos)) {
//            return Optional.of(getCurrentImage(getBackgroundCell(world, pos)));
//        } else {
//            return Optional.empty();
//        }
//    }

    //Worldview
    //drawBackground()

    //Worldview


    //Worldview
    //drawViewport

    //ImagesStore


    //ImagesStore

}
