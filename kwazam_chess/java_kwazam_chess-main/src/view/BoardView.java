package view;

import controller.BoardInteractionListener;
import controller.GameController;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import model.Board;
import model.Piece;

// represents the board view component, handling rendering and user interactions.
public class BoardView extends JPanel {
    private Board board; // the game board
    private GameController controller; // the game controller
    private List<int[]> highlightedCells = new ArrayList<>(); // list of highlighted cells
    private boolean flipped = false; // indicates if the board is flipped
    private BoardInteractionListener listener; // listener for board interactions

    // constructor to initialize the board view.
    public BoardView() {
        setBackground(Color.GRAY);

        // add mouse listener for click handling
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleMouseClick(e.getX(), e.getY()); // handle mouse click events
            }
        });
    }

    // returns the game controller.
    public GameController getController() {
        return controller;
    }

    // sets the game controller.
    public void setController(GameController controller) {
        this.controller = controller;
    }

    // sets the listener for board interactions.
    public void setListener(BoardInteractionListener listener) {
        this.listener = listener;
    }

    // updates the board view with the current board state.
    public void updateBoard(Board board) {
        this.board = board;
        repaint(); // repaint the board
    }

    // sets the highlighted cells on the board.
    public void setHighlightedCells(List<int[]> moves) {
        highlightedCells = moves != null ? moves : new ArrayList<>();
        repaint(); // repaint the board
    }

    // flips the board view.
    public void flipBoard() {
        flipped = !flipped; // toggle the flipped state
        repaint(); // repaint the board
    }

    // checks if the board is flipped.
    public boolean isFlipped() {
        return flipped;
    }

    // paints the board and pieces on the screen.
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (board == null) return;

        // calculate cell size based on the smaller dimension to maintain a 1:1 ratio
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        int cellSize = Math.min(panelWidth / 5, panelHeight / 8); // 5 columns, 8 rows

        // calculate padding to center the board if there's extra space
        int xOffset = (panelWidth - (cellSize * 5)) / 2;
        int yOffset = (panelHeight - (cellSize * 8)) / 2;

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 5; col++) {
                // adjust row and column for flipped board
                int displayRow = flipped ? 7 - row : row;
                int displayCol = flipped ? 4 - col : col;

                int x = col * cellSize + xOffset;
                int y = row * cellSize + yOffset;

                // draw cell background
                g.setColor(Color.WHITE);
                g.fillRect(x, y, cellSize, cellSize);

                // highlight valid moves (adjust for flipped board)
                if (isHighlighted(row, col)) {
                    g.setColor(new Color(255, 255, 0, 128)); // semi-transparent yellow
                    g.fillRect(x, y, cellSize, cellSize);
                }

                // draw cell border
                g.setColor(Color.BLACK);
                g.drawRect(x, y, cellSize, cellSize);

                // draw piece
                if (board != null) {
                    Piece piece = board.getPieceAt(displayRow, displayCol);
                    if (piece != null) {
                        drawPiece(g, piece, x, y, cellSize); // draw the piece
                    }
                }
            }
        }
    }

    // handles mouse click events on the board.
    private void handleMouseClick(int mouseX, int mouseY) {
        // calculate cell size based on the smaller dimension
        int cellSize = Math.min(getWidth() / 5, getHeight() / 8);
        int xOffset = (getWidth() - (cellSize * 5)) / 2;
        int yOffset = (getHeight() - (cellSize * 8)) / 2;

        // map pixel coordinates to logical board coordinates
        int clickedCol = (mouseX - xOffset) / cellSize;
        int clickedRow = (mouseY - yOffset) / cellSize;

        // adjust for flipped board
        if (flipped) {
            clickedRow = 7 - clickedRow;
            clickedCol = 4 - clickedCol;
        }

        // validate the click falls within the board boundaries
        if (clickedRow >= 0 && clickedRow < 8 && clickedCol >= 0 && clickedCol < 5) {
            if (listener != null) {
                listener.onCellClicked(clickedRow, clickedCol); // notify the listener
            }
        }
    }

    // draws a piece on the board.
    private void drawPiece(Graphics g, Piece piece, int x, int y, int cellSize) {
        String fileName = "/assets/" + (flipped ? "Flipped_" : "") + piece.getType() + "_" + piece.getColor() + ".png";
        ImageIcon icon = new ImageIcon(getClass().getResource(fileName));

        Image img = icon.getImage();
        int padding = cellSize / 10; // 10% padding
        g.drawImage(img, x + padding, y + padding, cellSize - 2 * padding, cellSize - 2 * padding, this);
    }

    // checks if a cell is highlighted.
    private boolean isHighlighted(int row, int col) {
        // adjust row and column for flipped board
        int adjustedRow = flipped ? 7 - row : row;
        int adjustedCol = flipped ? 4 - col : col;

        for (int[] cell : highlightedCells) {
            if (cell[0] == adjustedRow && cell[1] == adjustedCol) {
                return true;
            }
        }
        return false;
    }
}
