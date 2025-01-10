package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainMenuView extends JFrame {
    private JLabel welcomeLabel;
    private JButton loginButton;
    private JButton registerButton;
    private JButton addTransactionButton;
    private JButton historyButton;

    public MainMenuView() {
        setTitle("Pratama Delivery");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(235, 200, 146));

        welcomeLabel = new JLabel("Selamat datang di Pratama Delivery!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Montserrat", Font.BOLD, 16));
        mainPanel.add(welcomeLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        buttonPanel.setBackground(new Color(235, 200, 146));
        
        loginButton = new JButton("Login");
        registerButton = new JButton("Register");
        addTransactionButton = new JButton("Add Transaction");
        historyButton = new JButton("Delivery History");

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        buttonPanel.add(addTransactionButton);
        buttonPanel.add(historyButton);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        add(mainPanel);

        addTransactionButton.setEnabled(false);
        historyButton.setEnabled(false);
    }

    public void setWelcomeMessage(String message) {
        welcomeLabel.setText(message);
    }

    public void setLoginButtonEnabled(boolean enabled) {
        loginButton.setEnabled(enabled);
    }

    public void setRegisterButtonEnabled(boolean enabled) {
        registerButton.setEnabled(enabled);
    }

    public void setTransactionButtonEnabled(boolean enabled) {
        addTransactionButton.setEnabled(enabled);
    }

    public void setHistoryButtonEnabled(boolean enabled) {
        historyButton.setEnabled(enabled);
    }

    public void addLoginListener(ActionListener listener) {
        loginButton.addActionListener(listener);
    }

    public void addRegisterListener(ActionListener listener) {
        registerButton.addActionListener(listener);
    }

    public void addTransactionListener(ActionListener listener) {
        addTransactionButton.addActionListener(listener);
    }

    public void addHistoryListener(ActionListener listener) {
        historyButton.addActionListener(listener);
    }
}