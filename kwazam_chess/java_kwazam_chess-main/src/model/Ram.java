package model;

// represents the "Ram" piece in the game.
public class Ram extends Piece {

    private boolean movingForward; // true if the Ram is moving forward, false if moving backward

    // constructor to initialize the Ram piece with color and position.
    public Ram(String color, int x, int y) {
        super(color, x, y);
        movingForward = color.equals("Red"); // red Rams move forward, blue Rams move backward
    }

    // returns the type of the piece, which is "Ram".
    @Override
    public String getType() {
        return "Ram";
    }

    // checks if the move is valid for the Ram piece.
    @Override
    public boolean isValidMove(int startX, int startY, int endX, int endY, Board board) {
        // ensure Ram moves only one step in its current direction
        int direction = movingForward ? 1 : -1;
        if (endX != startX + direction || endY != startY) return false; // move is invalid if not in the correct direction
        return true; // move is valid
    }

    // sets the position of the Ram piece and reverses direction if it reaches the board's edge.
    @Override
    public void setPosition(int x, int y) {
        super.setPosition(x, y);

        // reverse direction if Ram reaches the board's edge
        if (x == 0 || x == 7) {
            movingForward = !movingForward;
        }
    }
}
