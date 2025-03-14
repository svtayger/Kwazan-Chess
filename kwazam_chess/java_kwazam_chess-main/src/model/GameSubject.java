package model;

// interface for the observer pattern in the game.
// this interface defines methods to add, remove, and notify observers.
public interface GameSubject {
    void addObserver(GameObserver observer); // adds an observer to the list
    void removeObserver(GameObserver observer); // removes an observer from the list
    void notifyObservers(String winnerColor); // notifies all observers with the winner's color
}
