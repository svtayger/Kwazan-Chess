package model;

// factory class to create pieces dynamically.
public class PieceFactory {
    // creates a piece of the specified type, color, and position.
    public static Piece createPiece(String type, String color, int row, int col) {
        switch (type) {
            case "Ram":
                return new Ram(color, row, col);
            case "Sau":
                return new Sau(color, row, col);
            case "Biz":
                return new Biz(color, row, col);
            case "Xor":
                return new Xor(color, row, col);
            case "Tor":
                return new Tor(color, row, col);
            default:
                return null; // return null if the type is unknown
        }
    }
}
