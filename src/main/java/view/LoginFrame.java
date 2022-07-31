package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import model.User;
import model.dao.UserDAO;

public class LoginFrame extends JFrame implements ActionListener, FocusListener {
    private final JPanel logoPanel, loginPanel, titleLabelPanel, inputsPanel, invisiblePanel;
    private final JLabel logoLabel, titleLabel, usernameLabel, passwordLabel, invisibleLabel1, invisibleLabel2;
    private final JTextField usernameTextField;
    private final JPasswordField passwordPasswordField;
    private final String defaultUsernameText = "Enter username...", defaultPasswordText = "Enter password...";
    private final JCheckBox showPasswordCheckBox;
    private final char defaultPasswordChar;
    private final JButton loginButton;
    private final Dimension loginBoxesDimension = new Dimension(350, 25);
    private final Color logoPanelColor = Color.cyan, loginPanelColor = Color.white, presetTextFieldColor = Color.lightGray, inputBorderColor = Color.black;
    private final ImageIcon logoIcon = new ImageIcon("images\\icons\\cash_register_icon.jpg"),
            usernameIcon = new ImageIcon("images\\icons\\username_icon.png"),
            passwordIcon = new ImageIcon("images\\icons\\password_icon.png"),
            setPasswordVisibleIcon = new ImageIcon("images\\icons\\hidden_eye_icon.png"),
            setPasswordHiddenIcon = new ImageIcon("images\\icons\\open_eye_icon.png");
    private final UserDAO userDAO;
    private User user;

    LoginFrame() throws SQLException {
        userDAO = new UserDAO();

        /**************************************** Frame ****************************************/

        this.setTitle("Login");
        this.setSize(800, 460);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());

        logoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 100));
        logoPanel.setPreferredSize(new Dimension(355, 0));
        logoPanel.setBackground(logoPanelColor);
        this.add(logoPanel, BorderLayout.EAST);

        loginPanel = new JPanel(new BorderLayout());
        loginPanel.setPreferredSize(new Dimension(395, 0));
        loginPanel.setBackground(loginPanelColor);
        this.add(loginPanel, BorderLayout.WEST);

        titleLabelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        titleLabelPanel.setPreferredSize(new Dimension(0, 60));
        titleLabelPanel.setBackground(loginPanelColor);
        loginPanel.add(titleLabelPanel, BorderLayout.NORTH);

        inputsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 30));
        inputsPanel.setPreferredSize(new Dimension(0, 280));
        inputsPanel.setBackground(loginPanelColor);
        loginPanel.add(inputsPanel, BorderLayout.SOUTH);

        invisiblePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        invisiblePanel.setBackground(loginPanelColor);
        this.add(invisiblePanel, BorderLayout.CENTER);

        /**************************************** Frame ****************************************/
        /*************************************** Extras ***************************************/

        titleLabel = new JLabel("LOGIN");
        titleLabel.setForeground(logoPanelColor);
        titleLabel.setFont(new Font("Calibri", Font.BOLD, 60));
        titleLabelPanel.add(titleLabel);

        logoLabel = new JLabel();
        logoLabel.setIcon(new ImageIcon(logoIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH)));
        logoPanel.add(logoLabel);

        /*************************************** Extras ***************************************/
        /************************************** Username **************************************/

        usernameLabel = new JLabel();
        usernameLabel.setIcon(new ImageIcon((usernameIcon).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        inputsPanel.add(usernameLabel);

        usernameTextField = new JTextField(defaultUsernameText);
        usernameTextField.setForeground(presetTextFieldColor);
        usernameTextField.setPreferredSize(loginBoxesDimension);
        usernameTextField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, inputBorderColor));
        usernameTextField.addFocusListener(this);
        usernameTextField.addKeyListener(keyAdapter);
        inputsPanel.add(usernameTextField);

        /************************************** Username **************************************/
        /************************************** Password **************************************/

        passwordLabel = new JLabel();
        passwordLabel.setIcon(new ImageIcon(passwordIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        inputsPanel.add(passwordLabel);

        passwordPasswordField = new JPasswordField(defaultPasswordText);
        passwordPasswordField.setForeground(presetTextFieldColor);
        passwordPasswordField.setPreferredSize(loginBoxesDimension);
        passwordPasswordField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, inputBorderColor));
        passwordPasswordField.addFocusListener(this);
        passwordPasswordField.addKeyListener(keyAdapter);
        inputsPanel.add(passwordPasswordField);

        /************************************** Password **************************************/
        /************************************ Show Password ************************************/

        invisibleLabel2 = new JLabel();
        invisibleLabel2.setPreferredSize(new Dimension(20, 225));
        invisiblePanel.add(invisibleLabel2);

        showPasswordCheckBox = new JCheckBox();
        showPasswordCheckBox.setSelected(true);
        showPasswordCheckBox.setFocusable(false);
        showPasswordCheckBox.addMouseListener(mouseAdapter);
        showPasswordCheckBox.addActionListener(this);
        invisiblePanel.add(showPasswordCheckBox);

        defaultPasswordChar = passwordPasswordField.getEchoChar();
        checkShowPasswordBox();

        /************************************ Show Password ************************************/
        /*************************************** Button ***************************************/

        invisibleLabel1 = new JLabel();
        invisibleLabel1.setPreferredSize(new Dimension(20, 20));
        inputsPanel.add(invisibleLabel1);

        loginButton = new JButton("Login");
        loginButton.setPreferredSize(loginBoxesDimension);
        loginButton.setFocusable(false);
        loginButton.setBorder(BorderFactory.createLineBorder(inputBorderColor));
        loginButton.setBackground(loginPanelColor);
        loginButton.setForeground(inputBorderColor);
        loginButton.addMouseListener(mouseAdapter);
        loginButton.addActionListener(this);
        inputsPanel.add(loginButton);

        /*************************************** Button ***************************************/

        this.setVisible(true);
    }

    private void checkShowPasswordBox() {
        if (showPasswordCheckBox.isSelected()) {
            passwordPasswordField.setEchoChar((char)0);
            showPasswordCheckBox.setIcon(new ImageIcon(setPasswordVisibleIcon.getImage().getScaledInstance(25, 20, Image.SCALE_SMOOTH)));
        } else {
            passwordPasswordField.setEchoChar(defaultPasswordChar);
            showPasswordCheckBox.setIcon(new ImageIcon(setPasswordHiddenIcon.getImage().getScaledInstance(25, 20, Image.SCALE_SMOOTH)));
        }
    }

    private boolean loginAuthentication() {
        try {
            user = userDAO.readUser(usernameTextField.getText());
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        if (user != null) { // A user was found through the inserted username
            return user.getPassword().equals(String.valueOf(passwordPasswordField.getPassword()));
        }
        return false;
    }

    private void loginAuthorization() throws SQLException, IOException {
        if (loginAuthentication()) {
            FileWriter fileWriter = new FileWriter("app_local_settings.txt");
            fileWriter.write("currentlyLoggedUser:" + user.getUsername());
            fileWriter.close();
            userDAO.close();
            new MenuFrame();
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Username/password is incorrect!", "Login error", JOptionPane.WARNING_MESSAGE);
            usernameTextField.setText(defaultUsernameText);
            usernameTextField.requestFocusInWindow();
            passwordPasswordField.setText(defaultPasswordText);
            passwordPasswordField.setEchoChar(defaultPasswordChar);
            showPasswordCheckBox.setSelected(true);
        }
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource().equals(showPasswordCheckBox)) {
            checkShowPasswordBox();
        } else if (event.getSource().equals(loginButton)) {
            try {
                loginAuthorization();
            } catch (SQLException | IOException exception) {
                exception.printStackTrace();
            }
        }
    }

    @Override
    public void focusGained(FocusEvent event) {
        if (event.getSource().equals(usernameTextField)) {
            if (usernameTextField.getText().equals(defaultUsernameText)) {
                usernameTextField.setText(null);
                usernameTextField.setForeground(inputBorderColor);
            }
        } else if (event.getSource().equals(passwordPasswordField)) {
            if (String.valueOf(passwordPasswordField.getPassword()).equals(defaultPasswordText)) {
                passwordPasswordField.setText(null);
                passwordPasswordField.setForeground(inputBorderColor);
                showPasswordCheckBox.setSelected(false);
                checkShowPasswordBox();
            }
        }
    }

    @Override
    public void focusLost(FocusEvent event) {
        if (event.getSource().equals(usernameTextField)) {
            if (usernameTextField.getText().isBlank()) {
                usernameTextField.setText(defaultUsernameText);
                usernameTextField.setForeground(presetTextFieldColor);
            }
        } else if (event.getSource().equals(passwordPasswordField)) {
            if (String.valueOf(passwordPasswordField.getPassword()).isBlank()) {
                passwordPasswordField.setText(defaultPasswordText);
                passwordPasswordField.setForeground(presetTextFieldColor);
                showPasswordCheckBox.setSelected(true);
                checkShowPasswordBox();
            }
        }
    }

    MouseAdapter mouseAdapter = new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent event) {
            if (event.getSource().equals(loginButton)) {
                loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            } else if (event.getSource().equals(showPasswordCheckBox)) {
                showPasswordCheckBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
        }
        @Override
        public void mouseExited(MouseEvent event) {
            if (event.getSource().equals(loginButton)) {
                loginButton.setCursor(Cursor.getDefaultCursor());
            } else if (event.getSource().equals(showPasswordCheckBox)) {
                showPasswordCheckBox.setCursor(Cursor.getDefaultCursor());
            }
        }
    };

    KeyAdapter keyAdapter = new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent event) {
            if (event.getKeyCode() == 10) { // Keyboard Enter button => Equivalent to clicking the screen login button
                try {
                    loginAuthorization();
                } catch (SQLException | IOException exception) {
                    exception.printStackTrace();
                }
            }
        }
    };
}
