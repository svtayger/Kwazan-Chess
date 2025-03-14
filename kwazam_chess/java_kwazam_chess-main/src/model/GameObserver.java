package model;

// interface for the observer pattern in the game.
// this interface defines a method to handle the event when the Sau piece is taken.
public interface GameObserver {
    void onSauTaken(String winnerColor); // called when the Sau piece is taken
}
