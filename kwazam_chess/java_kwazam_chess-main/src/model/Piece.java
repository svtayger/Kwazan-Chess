package model;

import java.util.ArrayList;
import java.util.List;

// abstract class representing a game piece.
public abstract class Piece {
    protected String color; // color of the piece (Red or Blue)
    protected int currentX, currentY; // current position of the piece

    // constructor to initialize the piece with color and position.
    public Piece(String color, int x, int y) {
        this.color = color;
        this.currentX = x;
        this.currentY = y;
    }

    // returns the color of the piece.
    public String getColor() {
        return color;
    }

    // returns the current X position of the piece.
    public int getX() {
        return currentX;
    }

    // returns the current Y position of the piece.
    public int getY() {
        return currentY;
    }

    // sets the position of the piece.
    public void setPosition(int x, int y) {
        this.currentX = x;
        this.currentY = y;
    }

    // abstract method to check if a move is valid for the piece.
    public abstract boolean isValidMove(int startX, int startY, int endX, int endY, Board board);

    // abstract method to get the type of the piece.
    public abstract String getType();

    // returns a list of valid moves for the piece.
    public List<int[]> getValidMoves(Board board) {
        List<int[]> validMoves = new ArrayList<>();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 5; col++) {
                if (isValidMove(currentX, currentY, row, col, board)) {
                    validMoves.add(new int[]{row, col}); // add valid move to the list
                }
            }
        }
        return validMoves;
    }
}
