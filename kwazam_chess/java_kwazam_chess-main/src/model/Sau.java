package model;

// represents the "Sau" piece in the game.
public class Sau extends Piece {

    // constructor to initialize the Sau piece with color and position.
    public Sau(String color, int x, int y) {
        super(color, x, y);
    }

    // returns the type of the piece, which is "Sau".
    @Override
    public String getType() {
        return "Sau";
    }

    // checks if the move is valid for the Sau piece.
    @Override
    public boolean isValidMove(int startX, int startY, int endX, int endY, Board board) {
        // check if the move is one step in any direction
        int dx = Math.abs(endX - startX);
        int dy = Math.abs(endY - startY);
        if (dx > 1 || dy > 1) return false; // move is invalid if more than one step

        return true; // move is valid
    }
}
