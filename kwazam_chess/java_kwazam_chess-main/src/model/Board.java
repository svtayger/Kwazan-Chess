package model;

import java.util.ArrayList;
import java.util.List;

// represents the game board, which implements the GameSubject interface for the observer pattern.
public class Board implements GameSubject {
    private Piece[][] grid; // 2D array representing the board grid
    private List<GameObserver> observers = new ArrayList<>(); // list of observers

    // constructor to initialize the board grid.
    public Board() {
        this.grid = new Piece[8][5]; // 5x8 board
    }

    // initializes the board with pieces for both players.
    public void initializeBoard() {
        // place pieces for both players
        clearBoard();
        grid[0][0] = new Tor("Red", 0, 0);
        grid[0][1] = new Biz("Red", 0, 1);
        grid[0][2] = new Sau("Red", 0, 2);
        grid[0][3] = new Biz("Red", 0, 3);
        grid[0][4] = new Xor("Red", 0, 4);

        grid[1][0] = new Ram("Red", 1, 0);
        grid[1][1] = new Ram("Red", 1, 1);
        grid[1][2] = new Ram("Red", 1, 2);
        grid[1][3] = new Ram("Red", 1, 3);
        grid[1][4] = new Ram("Red", 1, 4);

        grid[7][0] = new Tor("Blue", 7, 0);
        grid[7][1] = new Biz("Blue", 7, 1);
        grid[7][2] = new Sau("Blue", 7, 2);
        grid[7][3] = new Biz("Blue", 7, 3);
        grid[7][4] = new Xor("Blue", 7, 4);

        grid[6][0] = new Ram("Blue", 6, 0);
        grid[6][1] = new Ram("Blue", 6, 1);
        grid[6][2] = new Ram("Blue", 6, 2);
        grid[6][3] = new Ram("Blue", 6, 3);
        grid[6][4] = new Ram("Blue", 6, 4);
    }

    // returns the piece at the given coordinates, or null if empty.
    public Piece getPieceAt(int x, int y) {
        if (isInsideBoard(x, y)) {
            return grid[x][y];
        }
        return null;
    }

    // returns the board grid.
    public Piece[][] getGrid() {
        return grid;
    }

    // checks if the cell at (x, y) is occupied.
    public boolean isOccupied(int x, int y) {
        return getPieceAt(x, y) != null;
    }

    // checks if the cell at (x, y) is occupied by a piece of the same color.
    public boolean isOccupiedBySameColor(int x, int y, String color) {
        Piece piece = getPieceAt(x, y);
        return piece != null && piece.getColor().equals(color);
    }

    // moves a piece from (startX, startY) to (endX, endY) if the move is valid.
    public void movePiece(int startX, int startY, int endX, int endY) {
        Piece piece = getPieceAt(startX, startY);
        if (piece != null && piece.isValidMove(startX, startY, endX, endY, this)) {
            // check if the destination cell is occupied by an opponent's piece
            Piece targetPiece = getPieceAt(endX, endY);
            if (targetPiece != null && !targetPiece.getColor().equals(piece.getColor())) {
                // check if the captured piece is a Sau
                if (targetPiece instanceof Sau) {
                    // notify observers if a Sau piece is taken
                    grid[endX][endY] = null; // remove capturing piece
                    notifyObservers(piece.getColor()); // notify the winner's color
                    return;
                }
                // remove the opponent's piece (capture)
                grid[endX][endY] = null;
            }

            // move the piece to the new position
            grid[endX][endY] = piece;
            grid[startX][startY] = null; // clear the old position
            piece.setPosition(endX, endY);
        }
    }

    // checks if a path is blocked for a piece moving in a straight line.
    public boolean isPathBlocked(int startX, int startY, int endX, int endY) {
        int dx = Integer.compare(endX, startX); // direction of movement on x-axis
        int dy = Integer.compare(endY, startY); // direction of movement on y-axis

        int x = startX + dx, y = startY + dy;
        while (x != endX || y != endY) {
            if (isOccupied(x, y)) return true; // path is blocked
            x += dx;
            y += dy;
        }
        return false; // path is clear
    }

    // checks if given coordinates are within the board boundaries.
    public boolean isInsideBoard(int x, int y) {
        return x >= 0 && x < 8 && y >= 0 && y < 5;
    }

    // returns the valid moves for a given piece.
    public List<int[]> getValidMoves(Piece piece) {
        return piece.getValidMoves(this);
    }

    // handles transformation of Tor <-> Xor pieces.
    public void transformPieces() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 5; j++) {
                Piece piece = grid[i][j];
                if (piece instanceof Tor) {
                    grid[i][j] = new Xor(piece.getColor(), i, j);
                } else if (piece instanceof Xor) {
                    grid[i][j] = new Tor(piece.getColor(), i, j);
                }
            }
        }
    }

    // adds an observer to the list.
    @Override
    public void addObserver(GameObserver observer) {
        observers.add(observer);
    }

    // removes an observer from the list.
    @Override
    public void removeObserver(GameObserver observer) {
        observers.remove(observer);
    }

    // notifies all observers with the winner's color.
    @Override
    public void notifyObservers(String winnerColor) {
        for (GameObserver observer : observers) {
            observer.onSauTaken(winnerColor);
        }
    }

    // clears the board by removing all pieces.
    public void clearBoard() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 5; col++) {
                grid[row][col] = null; // remove each piece from the grid
            }
        }
    }
}
