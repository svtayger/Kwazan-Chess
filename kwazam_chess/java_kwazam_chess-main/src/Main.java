import controller.*;
import model.*;
import view.*;

public class Main {
    public static void main(String[] args) {
        // Create Model
        Game game = new Game();

        // Create View
        GameView gameView = new GameView();

        // Create Controller and link Model and View
        GameController gameController = new GameController(game, gameView);

        // Set the controller as the listener for the view
        gameView.setListener(gameController);

        // Set the controller in the GameView
        gameView.setController(gameController);

        // Start the game
        gameController.startGame();
        
        gameView.setVisible(true);
    }
}
