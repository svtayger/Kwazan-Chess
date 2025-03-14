package model;

import java.io.*;
import java.util.Scanner;

// handles saving and loading game states to/from files.
public class FileManager {

    // saves the current game state to a file.
    public static void saveGame(Board board, String filePath, boolean isFlipped, boolean isBlueTurn) {
        try {
            // create directory if it doesn't exist
            File saveDir = new File("savefile");
            if (!saveDir.exists()) {
                saveDir.mkdir();
            }

            // write game state to file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("savefile/" + filePath))) {
                // save board state
                for (int row = 0; row < 8; row++) {
                    for (int col = 0; col < 5; col++) {
                        Piece piece = board.getPieceAt(row, col);
                        if (piece != null) {
                            writer.write(piece.getClass().getSimpleName() + "," + piece.getColor() + "," + row + "," + col);
                            writer.newLine();
                        }
                    }
                }

                // save turn and board flip status
                writer.write("Turn:" + (isBlueTurn ? "Blue" : "Red"));
                writer.newLine();
                writer.write("Flipped:" + isFlipped);
                writer.newLine();
            }
            System.out.println("Game saved successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // loads a game state from a file.
    public static void loadGame(Board board, String filePath, boolean[] isFlipped, boolean[] isBlueTurn) {
        try {
            File saveFile = new File("savefile/" + filePath);
            if (!saveFile.exists()) {
                System.out.println("Save file not found.");
                return;
            }

            // clear the board
            board.clearBoard();

            try (Scanner scanner = new Scanner(saveFile)) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    if (line.startsWith("Turn:")) {
                        isBlueTurn[0] = line.split(":")[1].equals("Blue");
                    } else if (line.startsWith("Flipped:")) {
                        isFlipped[0] = Boolean.parseBoolean(line.split(":")[1]);
                    } else {
                        // parse piece information
                        String[] parts = line.split(",");
                        String pieceType = parts[0];
                        String color = parts[1];
                        int row = Integer.parseInt(parts[2]);
                        int col = Integer.parseInt(parts[3]);

                        // create piece dynamically
                        Piece piece = PieceFactory.createPiece(pieceType, color, row, col);
                        if (piece != null) {
                            board.getGrid()[row][col] = piece;
                        }
                    }
                }
            }
            System.out.println("Game loaded successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
