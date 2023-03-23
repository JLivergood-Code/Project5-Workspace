import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import processing.core.*;

public final class VirtualWorld extends PApplet {
    private static String[] ARGS;

    private static final int VIEW_WIDTH = 640;
    private static final int VIEW_HEIGHT = 480;
    private static final int TILE_WIDTH = 32;
    private static final int TILE_HEIGHT = 32;

    private static final int NUM_COLS = 40;
    private static final int NUM_ROWS = 30;

    private static final int VIEW_COLS = VIEW_WIDTH / TILE_WIDTH;
    private static final int VIEW_ROWS = VIEW_HEIGHT / TILE_HEIGHT;

    private static final String IMAGE_LIST_FILE_NAME = "imagelist";
    private static final String DEFAULT_IMAGE_NAME = "background_default";
    private static final int DEFAULT_IMAGE_COLOR = 0x808080;

    private static final String FAST_FLAG = "-fast";
    private static final String FASTER_FLAG = "-faster";
    private static final String FASTEST_FLAG = "-fastest";
    private static final double FAST_SCALE = 0.5;
    private static final double FASTER_SCALE = 0.25;
    private static final double FASTEST_SCALE = 0.10;

    private String loadFile = "world.sav";
    private long startTimeMillis = 0;
    private double timeScale = 1.0;

    private ImageStore imageStore;
    private WorldModel world;
    private WorldView view;
    private EventScheduler scheduler;

    public void settings() {
        size(VIEW_WIDTH, VIEW_HEIGHT);
    }

    /*
       Processing entry point for "sketch" setup.
    */
    public void setup() {
        parseCommandLine(ARGS);
        loadImages(IMAGE_LIST_FILE_NAME);
        loadWorld(loadFile, this.imageStore);

        this.view = new WorldView(VIEW_ROWS, VIEW_COLS, this, world, TILE_WIDTH, TILE_HEIGHT);
        this.scheduler = new EventScheduler();
        this.startTimeMillis = System.currentTimeMillis();
        this.scheduleActions(world, scheduler, imageStore);
    }

    public void draw() {
        double appTime = (System.currentTimeMillis() - startTimeMillis) * 0.001;
        double frameTime = (appTime - scheduler.getCurrentTime())/timeScale;
        this.update(frameTime);
        view.drawViewport();
    }

    public void update(double frameTime){
        scheduler.updateOnTime(frameTime);
    }

    // Just for debugging and for P5
    // Be sure to refactor this method as appropriate
    public void mousePressed() {
        Point pressed = mouseToPoint();
        System.out.println("CLICK! " + pressed.getX() + ", " + pressed.getY());
        Optional<Entity> entityOptional = world.getOccupant(pressed);
        if (entityOptional.isPresent()) {
            Entity entity = entityOptional.get();
            if (entity instanceof Plant) {
                System.out.println(entity.getClass() + " : " + ((Plant) entity).getHealth());
            } else if (entity instanceof Fairy) {
                magicPress(pressed);
            }
            else if (entity instanceof House){
                house_click(pressed);
            }
            System.out.println(entity.getClass());
        }
        else {
            deadPress(pressed);
        }
    }
private void house_click(Point pressed){
    Point up = new Point(pressed.getX(),pressed.getY()+1);
    Point down = new Point(pressed.getX(),pressed.getY()-1);
    if (world.withinBounds(up) && !world.isOccupied(up)){
        DudeNotFull dude = DudeNotFull.createDudeNotFull("dude", up, 0.8, 0.2, world.DUDE_LIMIT,imageStore.getImageList("dude"),3);
        world.addEntity(dude);
        dude.scheduleActions(scheduler, world, imageStore);
    }
    if (world.withinBounds(down) && !world.isOccupied(down)){
        DudeNotFull dude = DudeNotFull.createDudeNotFull("dude", down, 0.8, 0.2, world.DUDE_LIMIT,imageStore.getImageList("dude"),3);
        world.addEntity(dude);
        dude.scheduleActions(scheduler, world, imageStore);
    }
}
    private void swap_dead_background(Point current){
        if (world.getBackgroundCell(current).getImages().equals(imageStore.getImageList("grass"))) {
            world.setBackgroundCell(current, new Background("deadGrass", imageStore.getImageList("deadGrass")));
        }
        else if (world.getBackgroundCell(current).getImages().equals(imageStore.getImageList("flowers"))) {
            world.setBackgroundCell(current, new Background("deadGrass", imageStore.getImageList("deadFlowers")));
        }
        else if (world.getBackgroundCell(current).getImages().equals(imageStore.getImageList("dirt_bot_left_corner"))) {
            world.setBackgroundCell(current, new Background("dead_dirt_bot_left_corner", imageStore.getImageList("dead_dirt_bot_left_corner")));
        }
        else if (world.getBackgroundCell(current).getImages().equals(imageStore.getImageList("dirt_bot_right_up"))) {
            world.setBackgroundCell(current, new Background("dead_dirt_bot_right_up", imageStore.getImageList("dead_dirt_bot_right_up")));
        }
        else if (world.getBackgroundCell(current).getImages().equals(imageStore.getImageList("dirt_horiz"))) {
            world.setBackgroundCell(current, new Background("dead_dirt_horiz", imageStore.getImageList("dead_dirt_horiz")));
        }
        else if (world.getBackgroundCell(current).getImages().equals(imageStore.getImageList("dirt_vert_left"))) {
            world.setBackgroundCell(current, new Background("dead_dirt_vert_right", imageStore.getImageList("dead_dirt_vert_right")));
        }
        else if (world.getBackgroundCell(current).getImages().equals(imageStore.getImageList("dirt_vert_left_bot"))) {
            world.setBackgroundCell(current, new Background("dead_dirt_vert_left_bot", imageStore.getImageList("dead_dirt_vert_left_bot")));
        }
        else if (world.getBackgroundCell(current).getImages().equals(imageStore.getImageList("dirt_vert_right"))) {
            world.setBackgroundCell(current, new Background("dead_dirt_vert_left", imageStore.getImageList("dead_dirt_vert_left")));
        }
    }

    private void deadPress(Point pressed){
        for (int row = Math.max(pressed.getY() - 10, 0); row < Math.min(pressed.getY() + 10, NUM_ROWS); row++)
        {
            for (int col = Math.max(pressed.getX() - 10, 0); col < Math.min(pressed.getX() + 10, NUM_COLS); col++)
            {
                Point current = new Point(col, row);

                if (Point.distanceSquared(current, pressed) < 8 )
                {
                    Optional<Entity> entityOptional = world.getOccupant(current);
                    if (entityOptional.isPresent()) {
                        Entity entity = entityOptional.get();
                        if (entity instanceof Tree) {
                            ((Tree) entity).transformDead(world,scheduler,imageStore);
                        }
                    }
                    swap_dead_background(current);
                    if (Point.adjacent(current, pressed) && !world.isOccupied(current))
                    {
                        Skeleton newSkeleton = Skeleton.createSkeleton("skeleton", current, 0.5, 0.4, imageStore.getImageList("skeleton"), 1);
                        world.addEntity(newSkeleton);
                        newSkeleton.scheduleActions(scheduler, world, imageStore);
                    }
                }
            }
        }
        swap_dead_background(pressed);
    }
    private void swap_magic_background(Point current){
        if (world.getBackgroundCell(current).getImages().equals(imageStore.getImageList("grass"))||world.getBackgroundCell(current).getImages().equals(imageStore.getImageList("deadGrass"))) {
            world.setBackgroundCell(current, new Background("magicGrass", imageStore.getImageList("magicGrass")));
        }
        else if (world.getBackgroundCell(current).getImages().equals(imageStore.getImageList("flowers")) || world.getBackgroundCell(current).getImages().equals(imageStore.getImageList("deadFlowers"))) {
            world.setBackgroundCell(current, new Background("magicFlowers", imageStore.getImageList("magicFlowers")));
        }
        else if (world.getBackgroundCell(current).getImages().equals(imageStore.getImageList("dirt_bot_left_corner"))||world.getBackgroundCell(current).getImages().equals(imageStore.getImageList("dead_dirt_bot_left_corner"))) {
            world.setBackgroundCell(current, new Background("magic_dirt_bot_left_corner", imageStore.getImageList("magic_dirt_bot_left_corner")));
        }
        else if (world.getBackgroundCell(current).getImages().equals(imageStore.getImageList("dirt_bot_right_up"))||world.getBackgroundCell(current).getImages().equals(imageStore.getImageList("dead_dirt_bot_right_up"))) {
            world.setBackgroundCell(current, new Background("magic_dirt_bot_right_up", imageStore.getImageList("magic_dirt_bot_right_up")));
        }
        else if (world.getBackgroundCell(current).getImages().equals(imageStore.getImageList("dirt_horiz"))||world.getBackgroundCell(current).getImages().equals(imageStore.getImageList("dead_dirt_horiz"))) {
            world.setBackgroundCell(current, new Background("magic_dirt_horiz", imageStore.getImageList("magic_dirt_horiz")));
        }
        else if (world.getBackgroundCell(current).getImages().equals(imageStore.getImageList("dirt_vert_left"))||world.getBackgroundCell(current).getImages().equals(imageStore.getImageList("dead_dirt_vert_right"))) {
            world.setBackgroundCell(current, new Background("magic_dirt_vert_right", imageStore.getImageList("magic_dirt_vert_right")));
        }
        else if (world.getBackgroundCell(current).getImages().equals(imageStore.getImageList("dirt_vert_left_bot"))||world.getBackgroundCell(current).getImages().equals(imageStore.getImageList("dead_dirt_vert_left_bot"))) {
            world.setBackgroundCell(current, new Background("magic_dirt_vert_left_bot", imageStore.getImageList("magic_dirt_vert_left_bot")));
        }
        else if (world.getBackgroundCell(current).getImages().equals(imageStore.getImageList("dirt_vert_right"))||world.getBackgroundCell(current).getImages().equals(imageStore.getImageList("dead_dirt_vert_left"))) {
            world.setBackgroundCell(current, new Background("magic_dirt_vert_left", imageStore.getImageList("magic_dirt_vert_left")));
        }
    }
    private void magicPress(Point pressed){
        Point corner1 = (new Point(pressed.getX()+3, pressed.getY()+3));
        Point corner2 = (new Point(pressed.getX()-3, pressed.getY()+3));
        Point corner3 = (new Point(pressed.getX()+3, pressed.getY()-3));
        Point corner4 = (new Point(pressed.getX()-3, pressed.getY()-3));
        Point behind = new Point(pressed.getX() -1, pressed.getY()-1);
        Point infront = new Point(pressed.getX() +1, pressed.getY()+1);
        if (world.withinBounds(behind) && !world.isOccupied(behind)){
            Fairy fairy = Fairy.createFairy("fairy", behind, 0.75,.25, imageStore.getImageList("fairy"));
            world.addEntity(fairy);
            fairy.scheduleActions(scheduler, world, imageStore);
        }
        if (world.withinBounds(infront)&& !world.isOccupied(infront)){
            Fairy fairy = Fairy.createFairy("fairy", infront, 0.75,.25, imageStore.getImageList("fairy"));
            world.addEntity(fairy);
            fairy.scheduleActions(scheduler, world, imageStore);
        }
        for (int row = Math.max(pressed.getY() - 10, 0); row < Math.min(pressed.getY() + 10, NUM_ROWS); row++) {
            for (int col = Math.max(pressed.getX() - 10, 0); col < Math.min(pressed.getX() + 10, NUM_COLS); col++) {
                Point current = new Point(col, row);

                if (Point.distanceSquared(current, pressed) < 10 &
                        (Point.distanceSquared(current, corner1) > 6) &
                        (Point.distanceSquared(current, corner2) > 6) &
                        (Point.distanceSquared(current, corner3) > 6) &
                        (Point.distanceSquared(current, corner4) > 6)) {
                    Optional<Entity> entityOptional = world.getOccupant(current);
                    if (entityOptional.isPresent()) {
                        Entity entity = entityOptional.get();
                        if (entity instanceof Tree) {
                            ((Tree) entity).transformMagic(world, scheduler, imageStore);
                        } else if (entity instanceof Sapling) {
                            ((Sapling) entity).transformMagic(world, scheduler, imageStore);
                        } else if (entity instanceof Stump) {
                            ((Stump) entity).transformMagic(world, scheduler, imageStore);
                        }
                    }

                    swap_magic_background(current);

                }
            }
            Optional<Entity> entityO = world.getOccupant(pressed);
            if (entityO.isPresent()) {
                Entity pressedEntity = entityO.get();
                if (pressedEntity instanceof Fairy) {
                    Hero hero = Hero.createHero("hero", pressed, 0.4, 0.3, imageStore.getImageList("hero"));
                    world.removeEntity(scheduler, pressedEntity);
                    world.addEntity(hero);
                    hero.scheduleActions(scheduler, world, imageStore);
                }
            }
        }
        swap_magic_background(pressed);
    }
    public void scheduleActions(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        for (Entity entity : world.getEntities()) {
            if(entity instanceof Actionable)
            {
                Actionable a = (Actionable) entity;
                a.scheduleActions(scheduler, world, imageStore);
            }

            else if (entity instanceof Scheduable) {
                Scheduable a = (Scheduable) entity;
                a.scheduleActions(scheduler, world, imageStore);
            }
        }
    }

    private Point mouseToPoint() {
        return view.getViewport().viewportToWorld(mouseX / TILE_WIDTH, mouseY / TILE_HEIGHT);
    }

    public void keyPressed() {
        if (key == CODED) {
            int dx = 0;
            int dy = 0;

            switch (keyCode) {
                case UP -> dy -= 1;
                case DOWN -> dy += 1;
                case LEFT -> dx -= 1;
                case RIGHT -> dx += 1;
            }
            view.shiftView( dx, dy);
        }
    }

    public static Background createDefaultBackground(ImageStore imageStore) {
        return new Background(DEFAULT_IMAGE_NAME, imageStore.getImageList( DEFAULT_IMAGE_NAME));
    }

    public static PImage createImageColored(int width, int height, int color) {
        PImage img = new PImage(width, height, RGB);
        img.loadPixels();
        Arrays.fill(img.pixels, color);
        img.updatePixels();
        return img;
    }

    public void loadImages(String filename) {
        this.imageStore = new ImageStore(createImageColored(TILE_WIDTH, TILE_HEIGHT, DEFAULT_IMAGE_COLOR));
        try {
            Scanner in = new Scanner(new File(filename));
            this.imageStore.loadImages(in, this);
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    public void loadWorld(String file, ImageStore imageStore) {
        this.world = new WorldModel();
        try {
            Scanner in = new Scanner(new File(file));
            world.load(in, imageStore, createDefaultBackground(imageStore));
        } catch (FileNotFoundException e) {
            Scanner in = new Scanner(file);
            world.load(in, imageStore, createDefaultBackground(imageStore));
        }
    }

    public void parseCommandLine(String[] args) {
        for (String arg : args) {
            switch (arg) {
                case FAST_FLAG -> timeScale = Math.min(FAST_SCALE, timeScale);
                case FASTER_FLAG -> timeScale = Math.min(FASTER_SCALE, timeScale);
                case FASTEST_FLAG -> timeScale = Math.min(FASTEST_SCALE, timeScale);
                default -> loadFile = arg;
            }
        }
    }

    public static void main(String[] args) {
        VirtualWorld.ARGS = args;
        PApplet.main(VirtualWorld.class);
    }

    public static List<String> headlessMain(String[] args, double lifetime){
        VirtualWorld.ARGS = args;

        VirtualWorld virtualWorld = new VirtualWorld();
        virtualWorld.setup();
        virtualWorld.update(lifetime);

        return virtualWorld.world.log();
    }
}
