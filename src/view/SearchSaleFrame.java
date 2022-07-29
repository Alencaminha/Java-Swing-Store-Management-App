package view;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.table.*;

import model.dao.*;

public class SearchSaleFrame extends JFrame implements ActionListener {
    private final JPanel bottomPanel, topPanel, tablePanel;
    private final JLabel startDateLabel, endDateLabel;
    private final JTextField startDateTextField, endDateTextField;
    private final JButton backButton, searchButton, generateCSVButton;
    private final Dimension labelDimension = new Dimension(65, 20), inputBoxDimension = new Dimension(100, 20),
            tableDimension = new Dimension(800, 500), buttonsDimension = new Dimension(100, 25);;
    private final Color mainColor = Color.white, inputColor = Color.black;
    private final JTable table;
    private final DefaultTableModel tableModel;
    private final JScrollPane scrollPane;
    private Object[][] tableData;
    private final String[] tableColumns = {"ID", "Total cost", "Attendant", "Date"};
    private final SaleDAO saleDAO;
    private JFileChooser fileChooser;

    SearchSaleFrame() throws SQLException {
        saleDAO = new SaleDAO();

        /****************************** Frame ******************************/

        this.setTitle("Search Sale");
        this.setSize(1000, 720);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());

        tablePanel = new JPanel(new GridBagLayout());
        tablePanel.setBackground(mainColor);
        this.add(tablePanel, BorderLayout.CENTER);

        topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        topPanel.setPreferredSize(new Dimension(0, 50));
        topPanel.setBackground(mainColor);
        this.add(topPanel, BorderLayout.NORTH);

        /****************************** Frame ******************************/
        /****************************** Input ******************************/

        startDateLabel = new JLabel("Start date");
        startDateLabel.setPreferredSize(labelDimension);
        startDateLabel.setFont(new Font("Calibri", Font.BOLD, 14));
        topPanel.add(startDateLabel);

        startDateTextField = new JTextField();
        startDateTextField.setPreferredSize(inputBoxDimension);
        startDateTextField.setForeground(inputColor);
        startDateTextField.setBorder(BorderFactory.createLineBorder(inputColor));
        topPanel.add(startDateTextField);

        endDateLabel = new JLabel("End date");
        endDateLabel.setPreferredSize(labelDimension);
        endDateLabel.setFont(new Font("Calibri", Font.BOLD, 14));
        topPanel.add(endDateLabel);

        endDateTextField = new JTextField();
        endDateTextField.setPreferredSize(inputBoxDimension);
        endDateTextField.setForeground(inputColor);
        endDateTextField.setBorder(BorderFactory.createLineBorder(inputColor));
        topPanel.add(endDateTextField);

        /****************************** Input ******************************/
        /***************************** Buttons *****************************/

        searchButton = new JButton("Search sale");
        setButtonDesign(searchButton);
        topPanel.add(searchButton);

        generateCSVButton = new JButton("Generate CSV");
        setButtonDesign(generateCSVButton);
        topPanel.add(generateCSVButton);

        /***************************** Buttons *****************************/
        /**************************** Sale Table ****************************/

        tableData = saleDAO.getSalesTableData();
        tableModel = new DefaultTableModel(tableData, tableColumns);
        table = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(tableDimension);
        tablePanel.add(scrollPane, new GridBagConstraints());

        /**************************** Sale Table ****************************/
        /****************************** Frame ******************************/

        bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 30, 15));
        bottomPanel.setPreferredSize(new Dimension(0, 50));
        bottomPanel.setBackground(mainColor);
        this.add(bottomPanel, BorderLayout.SOUTH);

        backButton = new JButton("Back");
        backButton.setFocusable(false);
        backButton.addActionListener(this);
        bottomPanel.add(backButton);

        this.setVisible(true);

        /****************************** Frame ******************************/
    }

    private void setButtonDesign(JButton button) {
        button.setPreferredSize(buttonsDimension);
        button.setFocusable(false);
        button.setBorder(BorderFactory.createLineBorder(inputColor));
        button.setBackground(mainColor);
        button.setForeground(inputColor);
        button.addActionListener(this);
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent event) {
                button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent event) {
                button.setCursor(Cursor.getDefaultCursor());
            }
        });
    }

    private void updateTable() {
        int currentRowCount = tableModel.getRowCount();
        tableModel.setRowCount(0);
        tableModel.setRowCount(currentRowCount);
        tableModel.setDataVector(tableData, tableColumns);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(backButton)) {
            try {
                new MenuFrame();
            } catch (IOException | SQLException e1) {
                e1.printStackTrace();
            }
            this.dispose();
        }
    }
}
