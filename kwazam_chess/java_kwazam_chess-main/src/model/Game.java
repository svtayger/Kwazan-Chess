package model;

// represents the game logic and state.
public class Game {
    private Board board; // the game board
    private boolean isBlueTurn; // indicates if it's Blue's turn
    private int turnCounter; // counts the number of turns

    // constructor to initialize the game.
    public Game() {
        board = new Board();
        isBlueTurn = true; // Blue starts first
        turnCounter = 0;
    }

    // starts the game by initializing the board.
    public void startGame() {
        board.initializeBoard();
    }

    // switches the turn between players and handles Tor/Xor transformation every 4 turns.
    public void switchTurn() {
        isBlueTurn = !isBlueTurn;
        turnCounter++;
        if (turnCounter % 4 == 0) {
            board.transformPieces(); // transform Tor <-> Xor pieces
        }
    }

    // sets the turn to Blue or Red.
    public void setBlueTurn(boolean turn) {
        isBlueTurn = turn;
    }

    // returns the game board.
    public Board getBoard() {
        return board;
    }

    // checks if it's Blue's turn.
    public boolean isBlueTurn() {
        return isBlueTurn;
    }

    // resets the game to its initial state.
    public void reset() {
        this.isBlueTurn = false; // reset turn counter to Blue
        startGame(); // reinitialize the game
    }
}
