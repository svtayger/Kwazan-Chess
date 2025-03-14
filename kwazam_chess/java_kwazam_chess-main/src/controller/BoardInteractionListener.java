package controller;

// interface for handling board interaction events.
public interface BoardInteractionListener {
    void onCellClicked(int row, int col); // called when a cell on the board is clicked
}
