package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {
    private JTextField phoneField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton backButton;
    private JLabel logoLabel;

    public LoginView() {
        setTitle("Login");
        setSize(300, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(235, 200, 146));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(new Color(235, 200, 146));
        try {
            ImageIcon logoIcon = new ImageIcon("src/Logo/delivery.png");
            Image image = logoIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            logoLabel = new JLabel(new ImageIcon(image));
            logoPanel.add(logoLabel);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            logoLabel = new JLabel("Pratama Delivery");
            logoPanel.add(logoLabel);
        }
        mainPanel.add(logoPanel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBackground(new Color(235, 200, 146));

        formPanel.add(new JLabel("Phone:"));
        phoneField = new JTextField();
        formPanel.add(phoneField);

        formPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        formPanel.add(passwordField);

        loginButton = new JButton("Login");
        backButton = new JButton("Back");

        formPanel.add(loginButton);
        formPanel.add(backButton);

        mainPanel.add(formPanel, BorderLayout.CENTER);
        add(mainPanel);
    }

    public String getPhone() {
        return phoneField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public void addLoginListener(ActionListener listener) {
        loginButton.addActionListener(listener);
    }

    public void addBackListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }
}