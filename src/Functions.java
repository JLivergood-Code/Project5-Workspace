import java.util.*;

import processing.core.PImage;
import processing.core.PApplet;

/**
 * This class contains many functions written in a procedural style.
 * You will reduce the size of this class over the next several weeks
 * by refactoring this codebase to follow an OOP style.
 */
public final class Functions {
    private static final Random rand = new Random();

    public static final int COLOR_MASK = 0xffffff;
    public static final int KEYED_IMAGE_MIN = 5;
    private static final int KEYED_RED_IDX = 2;
    private static final int KEYED_GREEN_IDX = 3;
    private static final int KEYED_BLUE_IDX = 4;

    private static final List<String> PATH_KEYS = new ArrayList<>(Arrays.asList("bridge", "dirt", "dirt_horiz", "dirt_vert_left", "dirt_vert_right", "dirt_bot_left_corner", "dirt_bot_right_up", "dirt_vert_left_bot"));




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
        return (p1.getX() == p2.getX() && Math.abs(p1.getY() - p2.getY()) == 1) || (p1.getY() == p2.getY() && Math.abs(p1.getX() - p2.getX()) == 1);
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


    //static or Point
    public static int distanceSquared(Point p1, Point p2) {
        int deltaX = p1.getX() - p2.getX();
        int deltaY = p1.getY() - p2.getY();

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
    // create functions

    //worldmodel
    //load

    //worldmodel
    //parseSaveFile

    //worldmodel
    //parsebackgroundRow


    //worldmodel
    //parseEntity


    //static
//    public static PImage getCurrentImage(Object object) {
//        if (object instanceof Background background) {
//            return background.getImages().get(background.getImageIndex());
//        } else if (object instanceof Entity entity) {
//            return entity.getImages().get(entity.getImageIndex() % entity.getImages().size());
//        } else {
//            throw new UnsupportedOperationException(String.format("getCurrentImage not supported for %s", object));
//        }
//    }

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
