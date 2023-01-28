import processing.core.PApplet;
import processing.core.PImage;

import java.util.Optional;

public final class WorldView {
    private PApplet screen;
    private WorldModel world;
    private int tileWidth;
    private int tileHeight;
    private Viewport viewport;

    public WorldView(int numRows, int numCols, PApplet screen, WorldModel world, int tileWidth, int tileHeight) {
        this.screen = screen;
        this.world = world;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.setViewport(new Viewport(numRows, numCols));
    }

    public void shiftView(int colDelta, int rowDelta) {
        int newCol = Functions.clamp(this.getViewport().col + colDelta, 0, this.world.getNumCols() - this.getViewport().numCols);
        int newRow = Functions.clamp(this.getViewport().row + rowDelta, 0, this.world.getNumRows() - this.getViewport().numRows);

        this.getViewport().shift(newCol, newRow);
    }

    public void drawBackground() {
        for (int row = 0; row < this.getViewport().numRows; row++) {
            for (int col = 0; col < this.getViewport().numCols; col++) {
                Point worldPoint = this.getViewport().viewportToWorld(col, row);
                Optional<PImage> image = this.world.getBackgroundImage(worldPoint);
                if (image.isPresent()) {
                    this.screen.image(image.get(), col * this.tileWidth, row * this.tileHeight);
                }
            }
        }
    }
    public void drawViewport() {
        drawBackground();
        drawEntities();
    }

    public void drawEntities() {
        for (Entity entity : this.world.getEntities()) {
            Point pos = entity.getPosition();

            if (this.getViewport().contains(pos)) {
                Point viewPoint = this.getViewport().worldToViewport(pos.x, pos.y);
                this.screen.image(Functions.getCurrentImage(entity), viewPoint.x * this.tileWidth, viewPoint.y * this.tileHeight);
            }
        }
    }

    public Viewport getViewport() {
        return viewport;
    }

    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }
}
