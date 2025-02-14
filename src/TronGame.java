import Controller.Controller;
import Model.Arena;
import View.PlayerSetupDialog;
import View.View;

import javax.swing.*;

public class TronGame {
    public static void main(String[] args) {
        JFrame frame = new JFrame("TRON Light Cycles");

        // Show player setup dialog
        PlayerSetupDialog setup = new PlayerSetupDialog(frame);
        setup.setVisible(true);

        if (setup.isConfirmed()) {
            // Create MVC components with player information
            Arena arena = new Arena(35, 35,
                    setup.getPlayer1Name(),
                    setup.getPlayer2Name(),
                    setup.getPlayer1Color(),
                    setup.getPlayer2Color()
            );

            View view = new View(arena.getWidth(), arena.getHeight());
            Controller controller = new Controller(arena, view);

            controller.NewGame();
        } else {
            System.exit(0);
        }
    }
}