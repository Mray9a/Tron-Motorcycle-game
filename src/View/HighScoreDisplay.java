package View;

import Controller.HighScore;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class HighScoreDisplay extends JDialog {

    public HighScoreDisplay(JFrame parent, ArrayList<HighScore> highScores) {
        super(parent, "High Scores", true);

        // Create table model with column names
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Rank", "Player", "Score"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };

        // Add scores to table model
        for (int i = 0; i < highScores.size(); i++) {
            HighScore score = highScores.get(i);
            model.addRow(new Object[]{
                    i + 1,
                    score.name(),
                    score.score(),
            });
        }

        // Create and configure table
        JTable scoreTable = new JTable(model);
        scoreTable.setFont(new Font("Arial", Font.PLAIN, 14));
        scoreTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        scoreTable.setRowHeight(27);
        scoreTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scoreTable.setShowGrid(true);
        scoreTable.setGridColor(Color.GRAY);

        // Center align all columns
        for (int i = 0; i < scoreTable.getColumnCount(); i++) {
            scoreTable.getColumnModel().getColumn(i).setCellRenderer(
                    new javax.swing.table.DefaultTableCellRenderer() {
                        {
                            setHorizontalAlignment(JLabel.CENTER);
                        }
                    }
            );
        }

        // Add table to scroll pane
        JScrollPane scrollPane = new JScrollPane(scoreTable);
        scrollPane.setPreferredSize(new Dimension(400, 300));

        // Create main panel with title
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add title label
        JLabel titleLabel = new JLabel("TRON Light Cycles - Hall of Fame", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Add table
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Add close button
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Final dialog setup
        setContentPane(mainPanel);
        pack();
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

}