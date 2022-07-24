package view;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.*;

public class SearchSaleFrame extends JFrame implements ActionListener {
    private JPanel bottomPanel;
    private JButton backButton;

    SearchSaleFrame(){
        this.setTitle("Search Sale");
        this.setSize(1000, 720);
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());

        bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 30, 15));
        bottomPanel.setPreferredSize(new Dimension(0, 50));
        bottomPanel.setBackground(Color.gray);
        this.add(bottomPanel, BorderLayout.SOUTH);

        backButton = new JButton("Back");
        backButton.setFocusable(false);
        backButton.addActionListener(this);
        bottomPanel.add(backButton);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(backButton)) {
            try {
                new MenuFrame();
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            this.dispose();
        }
    }
}
