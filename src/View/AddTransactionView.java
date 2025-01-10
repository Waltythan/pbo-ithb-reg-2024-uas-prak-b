package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AddTransactionView extends JFrame {
    private JTextField recipientNameField;
    private JTextArea recipientAddressField;
    private JTextField recipientPhoneField;
    private JTextField weightField;
    private JComboBox<String> deliveryTypeCombo;
    private JButton saveButton;
    private JButton backButton;

    public AddTransactionView() {
        setTitle("Add Transaction");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(235, 200, 146));

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBackground(new Color(235, 200, 146));

        formPanel.add(new JLabel("Nama Penerima:"));
        recipientNameField = new JTextField();
        formPanel.add(recipientNameField);

        formPanel.add(new JLabel("Alamat Penerima:"));
        recipientAddressField = new JTextArea();
        formPanel.add(new JScrollPane(recipientAddressField));

        formPanel.add(new JLabel("No. Telepon Penerima:"));
        recipientPhoneField = new JTextField();
        formPanel.add(recipientPhoneField);

        formPanel.add(new JLabel("Weight (kg):"));
        weightField = new JTextField();
        formPanel.add(weightField);

        formPanel.add(new JLabel("Delivery Type:"));
        deliveryTypeCombo = new JComboBox<>();
        formPanel.add(deliveryTypeCombo);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        saveButton = new JButton("Save");
        backButton = new JButton("Back");
        buttonPanel.add(saveButton);
        buttonPanel.add(backButton);
        buttonPanel.setBackground(new Color(235, 200, 146));

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);
    }

    public void setDeliveryTypes(String[] types) {
        deliveryTypeCombo.removeAllItems();
        for (String type : types) {
            deliveryTypeCombo.addItem(type);
        }
    }

    public String getRecipientName() {
        return recipientNameField.getText();
    }

    public String getRecipientAddress() {
        return recipientAddressField.getText();
    }

    public String getRecipientPhone() {
        return recipientPhoneField.getText();
    }

    public String getSelectedDeliveryType() {
        return (String) deliveryTypeCombo.getSelectedItem();
    }

    public int getWeight() {
        return Integer.parseInt(weightField.getText());
    }

    public void addSaveListener(ActionListener listener) {
        saveButton.addActionListener(listener);
    }

    public void addBackListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }
}