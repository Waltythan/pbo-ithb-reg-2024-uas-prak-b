package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class RegisterView extends JFrame {
    private JTextField phoneField;
    private JTextField nameField;
    private JTextArea addressField;
    private JPasswordField passwordField;
    private JButton registerButton;
    private JButton backButton;

    public RegisterView() {
        setTitle("Register");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(235, 200, 146));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBackground(new Color(235, 200, 146));

        formPanel.add(new JLabel("No. Telepon:"));
        phoneField = new JTextField();
        formPanel.add(phoneField);

        formPanel.add(new JLabel("Nama:"));
        nameField = new JTextField();
        formPanel.add(nameField);

        formPanel.add(new JLabel("Alamat:"));
        addressField = new JTextArea();
        addressField.setLineWrap(true);
        formPanel.add(new JScrollPane(addressField));

        formPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        formPanel.add(passwordField);

        registerButton = new JButton("Register");
        backButton = new JButton("Back");

        formPanel.add(registerButton);
        formPanel.add(backButton);

        mainPanel.add(formPanel, BorderLayout.CENTER);
        add(mainPanel);
    }

    public String getPhone() {
        return phoneField.getText();
    }

    public String getName() {
        return nameField.getText();
    }

    public String getAddress() {
        return addressField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public void addRegisterListener(ActionListener listener) {
        registerButton.addActionListener(listener);
    }

    public void addBackListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }
}