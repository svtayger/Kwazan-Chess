package controller;

import java.util.List;
import model.*;
import view.*;

// controls the game logic and handles user interactions.
public class GameController implements BoardInteractionListener, GameObserver {
    private Game game; // the game model
    private GameView view; // the game view
    private MovementHandler movementHandler; // handles piece movement validation
    private Piece selectedPiece; // currently selected piece
    private boolean isFlipped; // indicates if the board is flipped

    // constructor to initialize the controller with the game and view.
    public GameController(Game game, GameView view) {
        this.game = game;
        this.view = view;
        this.isFlipped = false;
        this.movementHandler = new MovementHandler();
        view.setListener(this); // set as listener for BoardView
        game.getBoard().addObserver(this); // register this controller as an observer of the board
    }

    // starts the game by initializing the board and updating the view.
    public void startGame() {
        game.startGame();
        view.updateBoard(game.getBoard());
    }

    // handles saving the game state to a file.
    public void handleSaveGame() {
        FileManager.saveGame(game.getBoard(), "savefile.txt", isFlipped, game.isBlueTurn());
    }

    // handles loading a game state from a file.
    public void handleLoadGame() {
        boolean[] flippedStatus = new boolean[1];
        boolean[] turnStatus = new boolean[1];

        FileManager.loadGame(game.getBoard(), "savefile.txt", flippedStatus, turnStatus);

        // update the game state
        isFlipped = flippedStatus[0];
        game.setBlueTurn(turnStatus[0]); // set the turn based on the loaded game state
        // view.synchronizeFlipState(isFlipped);
        view.flipBoardIfNeeded(isFlipped); // update the visual flip state
        view.updateBoard(game.getBoard()); // refresh the board view
    }

    // handles cell click events on the board.
    @Override
    public void onCellClicked(int row, int col) {
        Board board = game.getBoard();
        Piece clickedPiece = board.getPieceAt(row, col);

        if (selectedPiece == null) {
            // select a piece
            if (clickedPiece != null && isTurnPiece(clickedPiece)) {
                selectedPiece = clickedPiece;
                List<int[]> validMoves = movementHandler.getValidMoves(selectedPiece, board);
                view.highlightMoves(validMoves); // highlight valid moves
            }
        } else {
            // attempt to move piece
            if (movementHandler.isValidMove(selectedPiece, selectedPiece.getX(), selectedPiece.getY(), row, col, board)) {
                board.movePiece(selectedPiece.getX(), selectedPiece.getY(), row, col); // move the piece
                game.switchTurn(); // switch turns
                view.flipBoard(); // flip the board
                view.updateBoard(board); // update the board view
                view.clearHighlights(); // clear highlights
                selectedPiece = null; // deselect the piece
            } else {
                // invalid move
                selectedPiece = null;
                view.clearHighlights(); // clear highlights
            }
        }
    }

    // checks if the clicked piece belongs to the current player.
    private boolean isTurnPiece(Piece piece) {
        return (game.isBlueTurn() && piece.getColor().equals("Blue")) ||
               (!game.isBlueTurn() && piece.getColor().equals("Red"));
    }

    // handles the event when the Sau piece is taken.
    @Override
    public void onSauTaken(String winnerColor) {
        // display a message and end the game
        view.showWinnerMessage(winnerColor + " wins! The Sau piece has been taken.", () -> restartGame());
    }

    // restarts the game by clearing the board and resetting the game state.
    private void restartGame() {
        selectedPiece = null;
        game.getBoard().clearBoard(); // clear the board
        game.reset(); // reset the game
        view.flipBoardIfNeeded(!isFlipped); // flip the board if needed
        view.updateBoard(game.getBoard()); // update the board view
    }
}
