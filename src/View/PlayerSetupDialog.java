package View;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class PlayerSetupDialog extends JDialog {
    private String player1Name = "";
    private String player2Name = "";
    private Color player1Color = Color.BLUE;
    private Color player2Color = Color.RED;
    private boolean isConfirmed = false;

    private final JTextField player1Field = new JTextField(15);
    private final JTextField player2Field = new JTextField(15);
    private final JComboBox<String> color1Box;
    private final JComboBox<String> color2Box;

    public PlayerSetupDialog(JFrame parent) {
        super(parent, "Player Setup", true);

        // Available colors
        String[] colorNames = {"Blue", "Red", "Green", "Orange", "Pink", "Cyan", "Magenta"};
        color1Box = new JComboBox<>(colorNames);
        color2Box = new JComboBox<>(colorNames);
        color2Box.setSelectedIndex(1); // Default to Red for player 2

        // Create layout
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Player 1 setup
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Player 1 Name:"), gbc);
        gbc.gridx = 1;
        panel.add(player1Field, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Player 1 Color:"), gbc);
        gbc.gridx = 1;
        panel.add(color1Box, gbc);

        // Player 2 setup
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Player 2 Name:"), gbc);
        gbc.gridx = 1;
        panel.add(player2Field, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Player 2 Color:"), gbc);
        gbc.gridx = 1;
        panel.add(color2Box, gbc);

        // Start button
        JButton startButton = new JButton("Start Game");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(startButton, gbc);

        startButton.addActionListener(e -> {
            if (validateInput()) {
                player1Name = player1Field.getText().trim();
                player2Name = player2Field.getText().trim();
                player1Color = getColorFromString((String) Objects.requireNonNull(color1Box.getSelectedItem()));
                player2Color = getColorFromString((String) Objects.requireNonNull(color2Box.getSelectedItem()));
                isConfirmed = true;
                dispose();
            }
        });

        // Set up dialog
        setContentPane(panel);
        pack();
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private boolean validateInput() {
        if (player1Field.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Player 1's name");
            return false;
        }
        if (player2Field.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Player 2's name");
            return false;
        }
        if (Objects.equals(color1Box.getSelectedItem(), color2Box.getSelectedItem())) {
            JOptionPane.showMessageDialog(this, "Players must choose different colors");
            return false;
        }
        if (player1Field.getText().trim().equals(player2Field.getText().trim())) {
            JOptionPane.showMessageDialog(this, "Players must have different names");
            return false;
        }
        return true;
    }

    private Color getColorFromString(String colorName) {
        return switch (colorName) {
            case "Blue" -> Color.BLUE;
            case "Red" -> Color.RED;
            case "Green" -> Color.GREEN;
            case "Orange" -> Color.ORANGE;
            case "Pink" -> Color.PINK;
            case "Cyan" -> Color.CYAN;
            case "Magenta" -> Color.MAGENTA;
            default -> Color.BLACK;
        };
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public String getPlayer1Name() {
        return player1Name;
    }

    public String getPlayer2Name() {
        return player2Name;
    }

    public Color getPlayer1Color() {
        return player1Color;
    }

    public Color getPlayer2Color() {
        return player2Color;
    }
}