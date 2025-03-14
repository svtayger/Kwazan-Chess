package model;

// represents the "Biz" piece in the game.
public class Biz extends Piece {

    // constructor to initialize the Biz piece with color and position.
    public Biz(String color, int x, int y) {
        super(color, x, y);
    }

    // returns the type of the piece, which is "Biz".
    @Override
    public String getType() {
        return "Biz";
    }

    // checks if the move is valid for the Biz piece.
    @Override
    public boolean isValidMove(int startX, int startY, int endX, int endY, Board board) {
        int dx = Math.abs(endX - startX);
        int dy = Math.abs(endY - startY);
        if (!((dx == 2 && dy == 1) || (dx == 1 && dy == 2))) return false; // only L-shape movement is allowed
        return true; // can skip over pieces
    }
}
