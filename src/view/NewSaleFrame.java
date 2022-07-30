package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import model.Sale;
import model.dao.ProductDAO;
import model.dao.SaleDAO;
import model.dao.UserDAO;

public class NewSaleFrame extends JFrame implements ActionListener {
    private final JPanel bottomPanel, inputsPanel, tablesPanel;
    private final JLabel sellerUsernameLabel, totalCostLabel, instructionLabel;
    private final JTextField totalCostTextField;
    private final JComboBox<String> sellerUsernameComboBox;
    private final JButton backButton, addItemButton, removeItemButton, saveSaleButton;
    private final Dimension labelDimension = new Dimension(65, 20), inputBoxDimension = new Dimension(180, 20),
            inputPanelDimension = new Dimension((int)(labelDimension.getWidth() + inputBoxDimension.getWidth()) + 20, 0),
            tableDimension = new Dimension(0, 310), buttonsDimension = new Dimension(100, 25);
    private final Color mainColor = Color.white, inputColor = Color.black;
    private final DefaultTableModel saleTableModel, productsTableModel;
    private final JTable saleTable, productsTable;
    private final JScrollPane saleScrollPane, productsScrollPane;
    private Object[][] saleData;
    private final Object[][] productsData;
    private final String[] attendantsArray, tableColumns = new String[]{"Id", "Name", "Category", "Price"};
    private final List<String> itemsList = new ArrayList<>();
    private int tableRowsNumber = 0;
    private final int tableColumnsNumber = 4;
    private Float totalCost = 0.0f;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private LocalDateTime now;
    private final SaleDAO saleDAO;
    private final ProductDAO productDAO;
    private final UserDAO userDAO;

    NewSaleFrame() throws SQLException{
        saleDAO = new SaleDAO();
        productDAO = new ProductDAO();
        userDAO = new UserDAO();

        /****************************** Frame ******************************/

        this.setTitle("New Sale");
        this.setSize(1000, 720);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());

        inputsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        inputsPanel.setPreferredSize(inputPanelDimension);
        inputsPanel.setBackground(mainColor);
        this.add(inputsPanel, BorderLayout.WEST);

        tablesPanel = new JPanel(new BorderLayout());
        tablesPanel.setBackground(mainColor);
        this.add(tablesPanel, BorderLayout.CENTER);

        /****************************** Frame ******************************/
        /****************************** Input ******************************/

        sellerUsernameLabel = new JLabel("Attendant");
        sellerUsernameLabel.setPreferredSize(labelDimension);
        sellerUsernameLabel.setFont(new Font("Calibri", Font.BOLD, 14));
        inputsPanel.add(sellerUsernameLabel);

        attendantsArray = userDAO.readAllAttendants();

        sellerUsernameComboBox = new JComboBox<>(attendantsArray);
        sellerUsernameComboBox.setPreferredSize(inputBoxDimension);
        sellerUsernameComboBox.setFocusable(false);
        inputsPanel.add(sellerUsernameComboBox);

        totalCostLabel = new JLabel("Total cost");
        totalCostLabel.setPreferredSize(labelDimension);
        totalCostLabel.setFont(new Font("Calibri", Font.BOLD, 14));
        inputsPanel.add(totalCostLabel);

        totalCostTextField = new JTextField(String.format("$ %.2f", totalCost));
        totalCostTextField.setPreferredSize(inputBoxDimension);
        totalCostTextField.setForeground(inputColor);
        totalCostTextField.setBorder(BorderFactory.createLineBorder(inputColor));
        inputsPanel.add(totalCostTextField);

        /****************************** Input ******************************/
        /***************************** Buttons *****************************/

        instructionLabel = new JLabel("<html>Select from tables<br>to add or remove</html>");
        instructionLabel.setFont(new Font("Calibri", Font.BOLD, 12));
        instructionLabel.setPreferredSize(buttonsDimension);
        inputsPanel.add(instructionLabel);

        addItemButton = new JButton("Add item");
        setButtonDesign(addItemButton);
        inputsPanel.add(addItemButton);

        removeItemButton = new JButton("Remove Item");
        setButtonDesign(removeItemButton);
        inputsPanel.add(removeItemButton);

        saveSaleButton = new JButton("Save sale");
        setButtonDesign(saveSaleButton);
        inputsPanel.add(saveSaleButton);

        /***************************** Buttons *****************************/
        /************************** Products Table **************************/

        productsData = productDAO.readProductsTableData();
        productsTableModel = new DefaultTableModel(productsData, tableColumns);

        productsTable = new JTable(productsTableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        productsTable.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent event) {
                productsTable.clearSelection();
            }
        });

        productsScrollPane = new JScrollPane(productsTable);
        productsScrollPane.setPreferredSize(tableDimension);
        tablesPanel.add(productsScrollPane, BorderLayout.NORTH);

        /************************** Products Table **************************/
        /**************************** Sale Table ****************************/

        saleTableModel = new DefaultTableModel(null, tableColumns);
        saleTable = new JTable(saleTableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        saleTable.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent event) {
                saleTable.clearSelection();
            }
        });

        saleScrollPane = new JScrollPane(saleTable);
        saleScrollPane.setPreferredSize(tableDimension);
        tablesPanel.add(saleScrollPane, BorderLayout.SOUTH);

        /**************************** Sale Table ****************************/
        /****************************** Frame ******************************/

        bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 30, 10));
        bottomPanel.setPreferredSize(new Dimension(0, 50));
        bottomPanel.setBackground(mainColor);
        this.add(bottomPanel, BorderLayout.SOUTH);

        backButton = new JButton("Back");
        setButtonDesign(backButton);
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
        int currentRowCount = saleTableModel.getRowCount();
        saleTableModel.setRowCount(0);
        saleTableModel.setRowCount(currentRowCount);
        saleTableModel.setDataVector(saleData, tableColumns);
    }

    private String[][] getSaleItems(){
        int aux = 0;

        String[][] items = new String[tableRowsNumber][tableColumnsNumber];
        for(int i = 0; i < tableRowsNumber; i++) {
            for(int j = 0; j < tableColumnsNumber; j++) {
                items[i][j] = itemsList.get(aux++);
            }
        }
        return items;
    }

    private void addItem() {
        if (productsTable.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "You must pick a line from the products table!",
                    "Selection error", JOptionPane.WARNING_MESSAGE);
        } else {
            tableRowsNumber++;

            totalCost += Float.parseFloat(((String) productsData[productsTable
                    .getSelectedRow()][3]).replaceAll(",", "."));
            totalCostTextField.setText(String.format("$ %.2f", totalCost));

            itemsList.add((String) productsData[productsTable.getSelectedRow()][0]);
            itemsList.add((String) productsData[productsTable.getSelectedRow()][1]);
            itemsList.add((String) productsData[productsTable.getSelectedRow()][2]);
            itemsList.add((String) productsData[productsTable.getSelectedRow()][3]);

            saleData = getSaleItems();
            updateTable();
        }
    }

    private void removeItem() {
        if (saleTable.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(null, "You must pick a line from the sale table!",
                    "Selection error", JOptionPane.WARNING_MESSAGE);
        } else {
            tableRowsNumber--;

            totalCost -= Float.parseFloat(((String) saleData[saleTable
                    .getSelectedRow()][3]).replaceAll(",", "."));
            totalCostTextField.setText(String.format("$ %.2f", totalCost));

            itemsList.remove((String) saleData[saleTable.getSelectedRow()][0]);
            itemsList.remove((String) saleData[saleTable.getSelectedRow()][1]);
            itemsList.remove((String) saleData[saleTable.getSelectedRow()][2]);
            itemsList.remove((String) saleData[saleTable.getSelectedRow()][3]);

            saleData = getSaleItems();
            updateTable();
        }
    }

    private void saveSale() {
        if (sellerUsernameComboBox.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null, "You must pick a user from the attendant box!",
                    "Selection error", JOptionPane.WARNING_MESSAGE);
        } else {
            now = LocalDateTime.now();
            Sale sale = new Sale(0,
                    totalCost,
                    (String) (sellerUsernameComboBox.getSelectedItem()),
                    dateTimeFormatter.format(now));
            try {
                if (saleDAO.createSale(sale)) {
                    JOptionPane.showMessageDialog(null, "This sale has been saved successfully!");
                    backToMenu();
                }
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
    }

    private void backToMenu(){
        try {
            saleDAO.close();
            productDAO.close();
            userDAO.close();
            new MenuFrame();
        } catch (SQLException | IOException exception) {
            exception.printStackTrace();
        }
        this.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource().equals(addItemButton)) {
            addItem();
        } else if (event.getSource().equals(removeItemButton)) {
            removeItem();
        } else if (event.getSource().equals(saveSaleButton)) {
            saveSale();
        } else if (event.getSource().equals(backButton)) {
            backToMenu();
        }
    }
}
