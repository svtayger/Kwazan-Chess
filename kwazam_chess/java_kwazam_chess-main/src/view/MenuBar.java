// package view;

// import javax.swing.*;
// import controller.GameController;

// public class MenuBar extends JMenuBar {
//     public MenuBar(GameController controller) {
//         JMenu fileMenu = new JMenu("File");
        
//         JMenuItem saveItem = new JMenuItem("Save");
//         saveItem.addActionListener(e -> controller.handleSaveGame());
//         fileMenu.add(saveItem);

//         JMenuItem loadItem = new JMenuItem("Load");
//         loadItem.addActionListener(e -> controller.handleLoadGame());
//         fileMenu.add(loadItem);

//         JMenuItem exitItem = new JMenuItem("Exit");
//         exitItem.addActionListener(e -> System.exit(0));
//         fileMenu.add(exitItem);

//         add(fileMenu);
//     }
// }
