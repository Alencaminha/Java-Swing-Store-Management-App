package view;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
import model.*;
import model.dao.*;

public class NewSaleFrame extends JFrame implements ActionListener {
    private JPanel bottomPanel, inputsPanel, tablesPanel;
    private JLabel sellerUsernameLabel, totalCostLabel, instructionLabel;
    private JTextField totalCostTextField;
    private JComboBox<String> sellerUsernameComboBox;
    private JButton backButton, addItemButton, removeItemButton, saveSaleButton;
    private Dimension labelDimension = new Dimension(65, 20), inputBoxDimension = new Dimension(180, 20),
            inputPanelDimension = new Dimension((int)(labelDimension.getWidth() + inputBoxDimension.getWidth()) + 20, 0),
            tableDimension = new Dimension(0, 310), buttonsDimension = new Dimension(100, 25);
    private Color mainColor = Color.white, inputColor = Color.black;
    private DefaultTableModel saleTableModel, productsTableModel;
    private JTable saleTable, productsTable;
    private JScrollPane saleScrollPane, productsScrollPane;
    private Object[][] saleData, productsData;
    private String[] saleTableColumns, productsTableColumns, attendantsArray;
    private ArrayList<String> saleItems;
    private SaleDAO saleDAO;
    private ProductDAO productDAO;
    private UserDAO userDAO;

    NewSaleFrame() throws SQLException{
        saleDAO = new SaleDAO();
        productDAO = new ProductDAO();
        userDAO = new UserDAO();

        /****************************** Frame ******************************/

        this.setTitle("New Sale");
        this.setSize(1000, 720);
        this.setResizable(true);
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

        attendantsArray = userDAO.getAllAttendants();

        sellerUsernameComboBox = new JComboBox<String>(attendantsArray);
        sellerUsernameComboBox.setPreferredSize(inputBoxDimension);
        sellerUsernameComboBox.setFocusable(false);
        inputsPanel.add(sellerUsernameComboBox);

        totalCostLabel = new JLabel("Total cost");
        totalCostLabel.setPreferredSize(labelDimension);
        totalCostLabel.setFont(new Font("Calibri", Font.BOLD, 14));
        inputsPanel.add(totalCostLabel);

        totalCostTextField = new JTextField();
        totalCostTextField.setPreferredSize(inputBoxDimension);
        totalCostTextField.setForeground(inputColor);
        totalCostTextField.setBorder(BorderFactory.createLineBorder(inputColor));
        inputsPanel.add(totalCostTextField);

        /****************************** Input ******************************/
        /***************************** Buttons *****************************/

        instructionLabel = new JLabel("<html>Select from table<br>to add or remove</html>");
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

        productsData = productDAO.getProductsTableData();
        productsTableColumns = new String[]{"ID", "Name", "Category", "Price"};
        productsTableModel = new DefaultTableModel(productsData, productsTableColumns);

        productsTable = new JTable(productsTableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        productsScrollPane = new JScrollPane(productsTable);
        productsScrollPane.setPreferredSize(tableDimension);
        tablesPanel.add(productsScrollPane, BorderLayout.NORTH);

        /************************** Products Table **************************/
        /**************************** Sale Table ****************************/

        saleItems = new ArrayList<String>();
        saleData = getSaleItems();
        saleTableColumns = new String[]{"Name", "Category", "Price"};
        saleTableModel = new DefaultTableModel(saleData, saleTableColumns);

        saleTable = new JTable(saleTableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

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

    private void setButtonDesign(JButton button){
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
        saleTableModel.setDataVector(saleData, saleTableColumns);
    }

    private String[][] getSaleItems(){
        int rows = 1, columns = 2;



        String[][] items = new String[rows][columns];
        // Populate items

        return items;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(backButton)) {
            try {
                saleDAO.close();
                productDAO.close();
                userDAO.close();
                new MenuFrame();
            } catch (SQLException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            this.dispose();
        }
    }
}
