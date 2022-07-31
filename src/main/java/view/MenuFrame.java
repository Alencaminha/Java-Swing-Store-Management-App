package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import model.User;
import model.dao.UserDAO;

public class MenuFrame extends JFrame implements ActionListener {
    private final JPanel mainPanel, logoPanel;
    private final JLabel iconLabel, helloLabel, logoLabel;
    private final String deniedUserAccessLevel = "Attendant";
    private String loggedUserName = "Unknown user", loggedUserAccessLevel = deniedUserAccessLevel, fileData;
    private final JButton newSaleButton, searchSaleButton, manageUsersButton, manageProductsButton, settingsButton, logoutButton;
    private final Dimension buttonsDimension = new Dimension(200, 25), helloDimension = new Dimension(220, 25),
            mainPanelDimension = new Dimension((int) buttonsDimension.getWidth() + 40, 0);
    private final Color mainPanelColor = Color.white, logoPanelColor = Color.cyan, buttonColor = Color.black;
    private final ImageIcon helloIcon = new ImageIcon("images\\icons\\username_icon.png"),
            logoIcon = new ImageIcon("images\\icons\\cash_register_icon.jpg"),
            newSaleIcon = new ImageIcon("images\\icons\\shopping_cart_icon.png"),
            searchSaleIcon = new ImageIcon("images\\icons\\search_icon.png"),
            manageUsersIcon = new ImageIcon("images\\icons\\user_icon.png"),
            manageProductsIcon = new ImageIcon("images\\icons\\product_icon.png"),
            settingsIcon = new ImageIcon("images\\icons\\settings_icon.png"),
            logoutIcon = new ImageIcon("images\\icons\\logout_icon.png");
    private final User currentlyLoggedUser;
    private final UserDAO userDAO;
    private final File settingsFile;
    private FileReader fileReader;
    private final HashMap<String, String> settingsMap = new HashMap<>();

    MenuFrame() throws SQLException, IOException {
        /*************************************** Setup ****************************************/
        userDAO = new UserDAO();
        fileData = "";
        StringBuilder stringBuilder = new StringBuilder(fileData);
        settingsFile = new File("app_local_settings.txt");
        if (settingsFile.exists()) {
            if (settingsFile.isFile()) {
                fileReader = new FileReader(settingsFile);
                int data = fileReader.read();
                while(data != -1) {
                    stringBuilder.append((char)data);
                    data = fileReader.read();
                }
                fileData = stringBuilder.toString();
                fileReader.close();
            }
        }
        String[] settingsStrings = fileData.split(",");
        for(String part : settingsStrings) {
            String[] partArray = part.split(":");
            settingsMap.put(partArray[0].trim(), partArray[1].trim());
        }
        currentlyLoggedUser = userDAO.readUser(settingsMap.get("currentlyLoggedUser"));
        if (currentlyLoggedUser != null) {
            loggedUserName = currentlyLoggedUser.getFullName();
            loggedUserAccessLevel = currentlyLoggedUser.getAccessLevel();
        }
        /*************************************** Setup ****************************************/
        /*************************************** Frame ***************************************/

        this.setTitle("Menu");
        this.setSize(1000, 720);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());

        mainPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 30));
        mainPanel.setPreferredSize(mainPanelDimension);
        mainPanel.setBackground(mainPanelColor);
        this.add(mainPanel, BorderLayout.WEST);

        logoPanel = new JPanel(new GridBagLayout());
        logoPanel.setBackground(logoPanelColor);
        this.add(logoPanel, BorderLayout.CENTER);

        /*************************************** Frame ***************************************/
        /*************************************** Icon ****************************************/

        iconLabel = new JLabel();
        iconLabel.setIcon(new ImageIcon(helloIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
        mainPanel.add(iconLabel);

        helloLabel = new JLabel("Hello, " + loggedUserName, SwingConstants.CENTER);
        helloLabel.setPreferredSize(helloDimension);
        helloLabel.setFont(new Font("Calibri", Font.BOLD, 13));
        mainPanel.add(helloLabel);

        /*************************************** Icon ****************************************/
        /*************************************** Logo ****************************************/

        logoLabel = new JLabel();
        logoLabel.setIcon(new ImageIcon(logoIcon.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH)));
        logoPanel.add(logoLabel, new GridBagConstraints());

        /*************************************** Logo ****************************************/
        /************************************* Buttons **************************************/

        newSaleButton = new JButton("New sale");
        setLineDesign(newSaleIcon, newSaleButton);

        searchSaleButton = new JButton("Search sale");
        setLineDesign(searchSaleIcon, searchSaleButton);

        manageUsersButton = new JButton("Manage users");
        setLineDesign(manageUsersIcon, manageUsersButton);

        manageProductsButton = new JButton("Manage products");
        setLineDesign(manageProductsIcon, manageProductsButton);

        settingsButton = new JButton("Settings");
        setLineDesign(settingsIcon, settingsButton);

        logoutButton = new JButton("Logout");
        setLineDesign(logoutIcon, logoutButton);

        /************************************* Buttons **************************************/

        this.setVisible(true);
    }

    private void setLineDesign(ImageIcon icon, JButton button) {
        JLabel label = new JLabel();
        label.setIcon(new ImageIcon(icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        mainPanel.add(label);

        button.setPreferredSize(buttonsDimension);
        button.setFocusable(false);
        button.setBorder(BorderFactory.createLineBorder(buttonColor));
        button.setBackground(mainPanelColor);
        button.setForeground(buttonColor);
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
        mainPanel.add(button);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        try {
            if (event.getSource().equals(newSaleButton)) {
                new NewSaleFrame();
                this.dispose();
            } else if (event.getSource().equals(searchSaleButton)) {
                new SearchSaleFrame();
                this.dispose();
            } else if (event.getSource().equals(manageUsersButton)) {
                if (loggedUserAccessLevel.equals(deniedUserAccessLevel)) {
                    JOptionPane.showMessageDialog(null, "You do not have access to this page!",
                            "Invalid access", JOptionPane.WARNING_MESSAGE);
                } else {
                    new ManageUsersFrame();
                    this.dispose();
                }
            } else if (event.getSource().equals(manageProductsButton)) {
                if (loggedUserAccessLevel.equals(deniedUserAccessLevel)) {
                    JOptionPane.showMessageDialog(null, "You do not have access to this page!",
                            "Invalid access", JOptionPane.WARNING_MESSAGE);
                } else {
                    new ManageProductsFrame();
                    this.dispose();
                }
            } else if (event.getSource().equals(settingsButton)) {
                // TODO Add the settings functionality.
            } else if (event.getSource().equals(logoutButton)) {
                String[] options = {"Yes", "No"};
                int option = JOptionPane.showOptionDialog(null, "Are you sure you want to logout?", "Exit",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
                if (option == 0) {
                    new LoginFrame();
                    this.dispose();
                }
            }
        } catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
}
