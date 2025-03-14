package model;

import java.util.ArrayList;
import java.util.List;

// handles movement validation and valid move calculation for pieces.
public class MovementHandler {

    // checks if a move is valid for a given piece.
    public boolean isValidMove(Piece piece, int startRow, int startCol, int endRow, int endCol, Board board) {
        // general validation logic
        if (!board.isInsideBoard(endRow, endCol)) return false; // move is invalid if outside the board
        if (board.isOccupiedBySameColor(endRow, endCol, piece.getColor())) return false; // move is invalid if the destination is occupied by a piece of the same color

        // delegate to the specific piece's logic
        return piece.isValidMove(startRow, startCol, endRow, endCol, board);
    }

    // returns a list of valid moves for a given piece.
    public List<int[]> getValidMoves(Piece piece, Board board) {
        List<int[]> validMoves = new ArrayList<>();
        int startRow = piece.getX();
        int startCol = piece.getY();

        // iterate through all possible cells on the board
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 5; col++) {
                if (isValidMove(piece, startRow, startCol, row, col, board)) {
                    validMoves.add(new int[]{row, col}); // add valid move to the list
                }
            }
        }
        return validMoves;
    }
}
