public final class Viewport {
    private int row;
    private int col;
    private int numRows;
    private int numCols;

    public Viewport(int numRows, int numCols) {
        this.numRows = numRows;
        this.numCols = numCols;
    }

    public Point worldToViewport(int col, int row) {
        return new Point(col - this.col, row - this.row);
    }

    public  Point viewportToWorld(int col, int row) {
        return new Point(col + this.col, row + this.row);
    }

    public void shift(int col, int row) {
        this.col = col;
        this.row = row;
    }

    public boolean contains( Point p) {
        return p.getY() >= this.getRow() && p.getY() < this.getRow() + this.getNumRows() && p.getX() >= this.getCol() && p.getX() < this.getCol() + this.getNumCols();
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getNumRows() {
        return numRows;
    }

    public void setNumRows(int numRows) {
        this.numRows = numRows;
    }

    public int getNumCols() {
        return numCols;
    }

    public void setNumCols(int numCols) {
        this.numCols = numCols;
    }
}
