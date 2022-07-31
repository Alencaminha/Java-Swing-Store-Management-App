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

import model.User;
import model.dao.UserDAO;

public class ManageUsersFrame extends JFrame implements ActionListener {
    private final JPanel bottomPanel, inputsPanel, tablePanel;
    private final JLabel usernameLabel, fullNameLabel, passwordLabel, emailLabel, userAccessLevelLabel, instructionLabel;
    private final JTextField usernameTextField, fullNameTextField, passwordTextField, emailTextField;
    private final JComboBox<String> userAccessLevelComboBox;
    private final JButton backButton, createUserButton, updateUserButton, deleteUserButton;
    private final Dimension labelDimension = new Dimension(60, 20), inputBoxDimension = new Dimension(180, 20),
            inputPanelDimension = new Dimension((int)(labelDimension.getWidth() + inputBoxDimension.getWidth()) + 20, 0),
            tableDimension = new Dimension(700, 600), buttonsDimension = new Dimension(105, 25);
    private final Color mainColor = Color.white, inputColor = Color.black;
    private final DefaultTableModel tableModel;
    private final JTable userDataTable;
    private final JScrollPane scrollPane;
    private Object[][] userData;
    private final String[] tableColumns;
    private final UserDAO userDAO;

    ManageUsersFrame() throws SQLException{
        userDAO = new UserDAO();

        /****************************** Frame ******************************/

        this.setTitle("Manage users");
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

        usernameLabel = new JLabel("Username");
        usernameTextField = new JTextField();
        setTextFieldDesign(usernameLabel, usernameTextField);

        fullNameLabel = new JLabel("Full name");
        fullNameTextField = new JTextField();
        setTextFieldDesign(fullNameLabel, fullNameTextField);

        passwordLabel = new JLabel("Password");
        passwordTextField = new JTextField();
        setTextFieldDesign(passwordLabel, passwordTextField);

        emailLabel = new JLabel("E-mail");
        emailTextField = new JTextField();
        setTextFieldDesign(emailLabel, emailTextField);

        userAccessLevelLabel = new JLabel("Access");
        userAccessLevelLabel.setPreferredSize(labelDimension);
        userAccessLevelLabel.setFont(new Font("Calibri", Font.BOLD, 14));
        inputsPanel.add(userAccessLevelLabel);

        userAccessLevelComboBox = new JComboBox<>(new String[]{null, "Admin", "Manager", "Attendant"});
        userAccessLevelComboBox.setPreferredSize(inputBoxDimension);
        userAccessLevelComboBox.setFocusable(false);
        inputsPanel.add(userAccessLevelComboBox);

        /****************************** Input ******************************/
        /***************************** Buttons *****************************/

        createUserButton = new JButton("Create user");
        setButtonDesign(createUserButton);
        inputsPanel.add(createUserButton);

        updateUserButton = new JButton("Update User");
        setButtonDesign(updateUserButton);
        inputsPanel.add(updateUserButton);

        instructionLabel = new JLabel("Select from table to delete");
        instructionLabel.setFont(new Font("Calibri", Font.BOLD, 10));
        inputsPanel.add(instructionLabel);

        deleteUserButton = new JButton("Delete User");
        setButtonDesign(deleteUserButton);
        inputsPanel.add(deleteUserButton);

        /***************************** Buttons *****************************/
        /****************************** Table ******************************/

        userData = userDAO.readUsersTableData();
        tableColumns = new String[]{"Username",
                "Full name", "Password", "E-mail", "Access Level"};
        tableModel = new DefaultTableModel(userData, tableColumns);

        userDataTable = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        userDataTable.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent event){
                if (event.getClickCount() == 2 && event.getButton() == MouseEvent.BUTTON1){
                    usernameTextField.setText((String) userData[userDataTable.getSelectedRow()][0]);
                    fullNameTextField.setText((String) userData[userDataTable.getSelectedRow()][1]);
                    passwordTextField.setText((String) userData[userDataTable.getSelectedRow()][2]);
                    emailTextField.setText((String) userData[userDataTable.getSelectedRow()][3]);
                    userAccessLevelComboBox.setSelectedItem(userData[userDataTable.getSelectedRow()][4]);
                }
            }
        });

        scrollPane = new JScrollPane(userDataTable);
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
        return (fullNameTextField.getText().isBlank() || usernameTextField.getText().isBlank()
                || passwordTextField.getText().isBlank() || emailTextField.getText().isBlank()
                || userAccessLevelComboBox.getSelectedItem() == null);
    }

    private void emptyBoxes() {
        usernameTextField.setText(null);
        usernameTextField.requestFocus();
        fullNameTextField.setText(null);
        passwordTextField.setText(null);
        emailTextField.setText(null);
        userAccessLevelComboBox.setSelectedIndex(0);
    }

    private void updateTable() {
        int currentRowCount = tableModel.getRowCount();
        tableModel.setRowCount(0);
        tableModel.setRowCount(currentRowCount);
        tableModel.setDataVector(userData, tableColumns);
    }

    private void dbCreateUser(User user) {
        if (isBoxesEmpty()) {
            JOptionPane.showMessageDialog(null, "You must fill all text fields!",
                    "Input error", JOptionPane.WARNING_MESSAGE);
        } else {
            try {
                if (userDAO.createUser(user)){
                    userData = userDAO.readUsersTableData();
                    JOptionPane.showMessageDialog(null, "This user has been created successfully!",
                            "User created", JOptionPane.INFORMATION_MESSAGE);
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

    private void dbUpdateUser(User user) {
        if (isBoxesEmpty()) {
            JOptionPane.showMessageDialog(null, "You must fill all text fields!",
                    "Input error", JOptionPane.WARNING_MESSAGE);
        } else {
            try {
                if (userDAO.updateUser(user)){
                    userData = userDAO.readUsersTableData();
                    JOptionPane.showMessageDialog(null, "This user has been updated successfully!",
                            "User updated", JOptionPane.INFORMATION_MESSAGE);
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

    private void dbDeleteUser() {
        if (userDataTable.getSelectionModel().isSelectionEmpty()) {
            JOptionPane.showMessageDialog(null, "You must pick a line from the table!",
                    "Delete error", JOptionPane.WARNING_MESSAGE);
        } else {
            String[] options = {"Yes", "No"};
            int option = JOptionPane.showOptionDialog(null, "Are you sure you want to delete this user?", "Delete user",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
            if (option == 0) {
                try {
                    String selectedUser = (String) userData[userDataTable.getSelectedRow()][0];
                    if (userDAO.deleteUser(selectedUser)) {
                        userData = userDAO.readUsersTableData();
                        JOptionPane.showMessageDialog(null, "This user has been deleted successfully!",
                                "User deleted", JOptionPane.INFORMATION_MESSAGE);
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
        User user = new User(
                usernameTextField.getText(),
                fullNameTextField.getText(),
                passwordTextField.getText(),
                emailTextField.getText(),
                (String) userAccessLevelComboBox.getSelectedItem());
        if (event.getSource().equals(createUserButton)) {
            dbCreateUser(user);
        } else if (event.getSource().equals(updateUserButton)) {
            dbUpdateUser(user);
        } else if (event.getSource().equals(deleteUserButton)) {
            dbDeleteUser();
        } else if (event.getSource().equals(backButton)) {
            try {
                userDAO.close();
                new MenuFrame();
            } catch (SQLException | IOException exception) {
                exception.printStackTrace();
            }
            this.dispose();
        }
    }
}
