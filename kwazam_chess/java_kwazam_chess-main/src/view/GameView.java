package view;

import controller.BoardInteractionListener; // Fix for BoardInteractionListener
import controller.GameController;
import java.awt.*; // Fix for Board
import java.util.List;
import javax.swing.*;
import model.Board;


public class GameView extends JFrame {
    private BoardView boardView;
    private GameController controller;

    public GameView() {
        setTitle("Kwazam Chess");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        boardView = new BoardView();
        add(boardView, BorderLayout.CENTER);
        
        setupMenu();

        setSize(600, 800); // Initial size
        setMinimumSize(new Dimension(400, 600)); // Minimum size
    }

    public void setController(GameController controller) {
        this.controller = controller;
        boardView.setController(controller);
    }

    public void setListener(BoardInteractionListener listener) {
        boardView.setListener(listener);
    }

    public void updateBoard(Board board) {
        boardView.updateBoard(board);
    }

    public void highlightMoves(List<int[]> moves) {
        boardView.setHighlightedCells(moves);
    }

    public void clearHighlights() {
        boardView.setHighlightedCells(null);
    }

    public void flipBoard() {
        boardView.flipBoard(); // Delegate to BoardView
    }

    public void flipBoardIfNeeded(boolean isFlipped) {
        if (boardView.isFlipped() != isFlipped) {
            boardView.flipBoard();
        }
    }

    public void synchronizeFlipState(boolean isFlipped) {
        if (boardView.isFlipped() != isFlipped) {
            boardView.flipBoard(); // Flip the board visually if the states don't match
        }
    }

    public void showWinnerMessage(String message, Runnable onRestart) {
        JLabel label = new JLabel(message, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 20));

        int choice = JOptionPane.showOptionDialog(
            this,
            label,
            "Game Over",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            new String[]{"Restart Game"},
            "Restart Game"
        );

        // Restart the game if the user chooses to
        if (choice == 0) {
            onRestart.run();
        }
    }

    private void setupMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");

        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.addActionListener(e -> boardView.getController().handleSaveGame());
        fileMenu.add(saveItem);

        JMenuItem loadItem = new JMenuItem("Load");
        loadItem.addActionListener(e -> boardView.getController().handleLoadGame());
        fileMenu.add(loadItem);

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);

        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
    }

    }
