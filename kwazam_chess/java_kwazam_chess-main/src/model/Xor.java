package model;

// represents the "Xor" piece in the game.
public class Xor extends Piece {

    // constructor to initialize the Xor piece with color and position.
    public Xor(String color, int x, int y) {
        super(color, x, y);
    }

    // returns the type of the piece, which is "Xor".
    @Override
    public String getType() {
        return "Xor";
    }

    // checks if the move is valid for the Xor piece.
    @Override
    public boolean isValidMove(int startX, int startY, int endX, int endY, Board board) {
        if (startX == endX && startY == endY) return false; // cannot move to the same position
        if (Math.abs(endX - startX) != Math.abs(endY - startY)) return false; // only diagonal movement is allowed
        if (board.isPathBlocked(startX, startY, endX, endY)) return false; // cannot skip over pieces
        return true; // move is valid
    }
}
