package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;

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

import model.Product;
import model.dao.ProductDAO;

public class ManageProductsFrame extends JFrame implements ActionListener {
    private final JPanel bottomPanel, inputsPanel, tablePanel;
    private final JLabel idLabel, nameLabel, priceLabel, categoryLabel, instructionLabel;
    private final JTextField idTextField, nameTextField, priceTextField;
    private final JComboBox<String> categoryComboBox;
    private final JButton backButton, createProductButton, updateProductButton, deleteProductButton;
    private final Dimension labelDimension = new Dimension(85, 20), inputBoxDimension = new Dimension(180, 20),
            inputPanelDimension = new Dimension((int)(labelDimension.getWidth() + inputBoxDimension.getWidth()) + 20, 0),
            tableDimension = new Dimension(690, 600), buttonsDimension = new Dimension(105, 25);
    private final Color mainColor = Color.white, inputColor = Color.black;
    private final DefaultTableModel tableModel;
    private final JTable productDataTable;
    private final JScrollPane scrollPane;
    private Object[][] productData;
    private final String[] tableColumns, categoriesArray;
    private final ProductDAO productDAO;

    ManageProductsFrame() throws SQLException{
        productDAO = new ProductDAO();

        /****************************** Frame ******************************/

        this.setTitle("Manage products");
        this.setSize(1000, 720);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());

        inputsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        inputsPanel.setPreferredSize(inputPanelDimension);
        inputsPanel.setBackground(mainColor);
        this.add(inputsPanel, BorderLayout.WEST);

        tablePanel = new JPanel(new GridBagLayout());
        tablePanel.setBackground(mainColor);
        this.add(tablePanel, BorderLayout.CENTER);

        /****************************** Frame ******************************/
        /****************************** Input ******************************/

        idLabel = new JLabel("Product ID");
        idTextField = new JTextField();
        setTextFieldDesign(idLabel, idTextField);

        nameLabel = new JLabel("Product name");
        nameTextField = new JTextField();
        setTextFieldDesign(nameLabel, nameTextField);

        priceLabel = new JLabel("Price (USD)");
        priceTextField = new JTextField();
        setTextFieldDesign(priceLabel, priceTextField);

        categoryLabel = new JLabel("Category");
        categoryLabel.setPreferredSize(labelDimension);
        categoryLabel.setFont(new Font("Calibri", Font.BOLD, 14));
        inputsPanel.add(categoryLabel);

        categoriesArray = new String[]{null, "Burger", "Drink"};

        categoryComboBox = new JComboBox<>(categoriesArray);
        categoryComboBox.setPreferredSize(inputBoxDimension);
        categoryComboBox.setFocusable(false);
        inputsPanel.add(categoryComboBox);

        /****************************** Input ******************************/
        /***************************** Buttons *****************************/

        createProductButton = new JButton("Create product");
        setButtonDesign(createProductButton);
        inputsPanel.add(createProductButton);

        updateProductButton = new JButton("Update Product");
        setButtonDesign(updateProductButton);
        inputsPanel.add(updateProductButton);

        instructionLabel = new JLabel("Select from table to delete");
        instructionLabel.setFont(new Font("Calibri", Font.BOLD, 10));
        inputsPanel.add(instructionLabel);

        deleteProductButton = new JButton("Delete Product");
        setButtonDesign(deleteProductButton);
        inputsPanel.add(deleteProductButton);

        /***************************** Buttons *****************************/
        /****************************** Table ******************************/

        productData = productDAO.readProductsTableData();
        tableColumns = new String[]{"ID", "Name", "Category", "Price"};
        tableModel = new DefaultTableModel(productData, tableColumns);

        productDataTable = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        productDataTable.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent event){
                if (event.getClickCount() == 2 && event.getButton() == MouseEvent.BUTTON1){
                    idTextField.setText((String) productData[productDataTable.getSelectedRow()][0]);
                    nameTextField.setText((String) productData[productDataTable.getSelectedRow()][1]);
                    categoryComboBox.setSelectedItem(productData[productDataTable.getSelectedRow()][2]);
                    priceTextField.setText((String) productData[productDataTable.getSelectedRow()][3]);
                }
            }
        });

        scrollPane = new JScrollPane(productDataTable);
        scrollPane.setPreferredSize(tableDimension);
        tablePanel.add(scrollPane, new GridBagConstraints());

        /****************************** Table ******************************/
        /****************************** Frame ******************************/

        bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 30, 5));
        bottomPanel.setPreferredSize(new Dimension(0, 50));
        bottomPanel.setBackground(mainColor);
        this.add(bottomPanel, BorderLayout.SOUTH);

        backButton = new JButton("Back");
        setButtonDesign(backButton);
        bottomPanel.add(backButton);

        this.setVisible(true);

        /****************************** Frame ******************************/
    }

    private void setTextFieldDesign(JLabel label, JTextField textField){
        label.setPreferredSize(labelDimension);
        label.setFont(new Font("Calibri", Font.BOLD, 14));
        inputsPanel.add(label);

        textField.setPreferredSize(inputBoxDimension);
        textField.setForeground(inputColor);
        textField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, inputColor));
        inputsPanel.add(textField);
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

    private boolean isBoxesEmpty() {
        return (idTextField.getText().isBlank() || nameTextField.getText().isBlank()
                || priceTextField.getText().isBlank() || categoryComboBox.getSelectedItem() == null);
    }

    private void emptyBoxes() {
        idTextField.setText(null);
        idTextField.requestFocus();
        nameTextField.setText(null);
        priceTextField.setText(null);
        categoryComboBox.setSelectedIndex(0);
    }

    private void updateTable() {
        int currentRowCount = tableModel.getRowCount();
        tableModel.setRowCount(0);
        tableModel.setRowCount(currentRowCount);
        tableModel.setDataVector(productData, tableColumns);
    }

    private void dbCreateProduct(Product product) {
        if (isBoxesEmpty()) {
            JOptionPane.showMessageDialog(null, "You must fill all text fields!",
                    "Input error", JOptionPane.WARNING_MESSAGE);
        } else {
            try {
                if (productDAO.createProduct(product)){
                    productData = productDAO.readProductsTableData();
                    JOptionPane.showMessageDialog(null, "This product has been created successfully!",
                            "Product created", JOptionPane.INFORMATION_MESSAGE);
                    emptyBoxes();
                    updateTable();
                }
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
                JOptionPane.showMessageDialog(null, "Someting went wrong!",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void dbUpdateProduct(Product product) {
        if (isBoxesEmpty()) {
            JOptionPane.showMessageDialog(null, "You must fill all text fields!",
                    "Input error", JOptionPane.WARNING_MESSAGE);
        } else {
            try {
                if (productDAO.updateProduct(product)){
                    productData = productDAO.readProductsTableData();
                    JOptionPane.showMessageDialog(null, "This product has been updated successfully!",
                            "Product updated", JOptionPane.INFORMATION_MESSAGE);
                    emptyBoxes();
                    updateTable();
                }
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
                JOptionPane.showMessageDialog(null, "Someting went wrong!",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void dbDeleteProduct() {
        if (productDataTable.getSelectionModel().isSelectionEmpty()) {
            JOptionPane.showMessageDialog(null, "You must pick a line from the table!",
                    "Delete error", JOptionPane.WARNING_MESSAGE);
        } else {
            String[] options = {"Yes", "No"};
            int option = JOptionPane.showOptionDialog(null, "Are you sure you want to delete this product?", "Delete product",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
            if (option == 0) {
                try {
                    int selectedProduct = Integer.parseInt((String) productData[productDataTable.getSelectedRow()][0]);
                    if (productDAO.deleteProduct(selectedProduct)) {
                        productData = productDAO.readProductsTableData();
                        JOptionPane.showMessageDialog(null, "This product has been deleted successfully!",
                                "Product deleted", JOptionPane.INFORMATION_MESSAGE);
                        emptyBoxes();
                        updateTable();
                    }
                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Someting went wrong!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        Product product = new Product(
                idTextField.getText().isBlank() ? 0 : Integer.parseInt(idTextField.getText()),
                nameTextField.getText(),
                (String) categoryComboBox.getSelectedItem(),
                priceTextField.getText());
        if (event.getSource().equals(createProductButton)) {
            dbCreateProduct(product);
        } else if (event.getSource().equals(updateProductButton)) {
            dbUpdateProduct(product);
        } else if (event.getSource().equals(deleteProductButton)) {
            dbDeleteProduct();
        } else if (event.getSource().equals(backButton)) {
            try {
                productDAO.close();
                new MenuFrame();
            } catch (SQLException | IOException exception) {
                exception.printStackTrace();
            }
            this.dispose();
        }
    }
}
