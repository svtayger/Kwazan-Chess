package model;

// represents the "Tor" piece in the game.
public class Tor extends Piece {

    // constructor to initialize the Tor piece with color and position.
    public Tor(String color, int x, int y) {
        super(color, x, y);
    }

    // returns the type of the piece, which is "Tor".
    @Override
    public String getType() {
        return "Tor";
    }

    // checks if the move is valid for the Tor piece.
    @Override
    public boolean isValidMove(int startX, int startY, int endX, int endY, Board board) {
        if (startX == endX && startY == endY) return false; // cannot move to the same position
        if (startX != endX && startY != endY) return false; // only orthogonal movement is allowed
        if (board.isPathBlocked(startX, startY, endX, endY)) return false; // cannot skip over pieces
        return true; // move is valid
    }
}
