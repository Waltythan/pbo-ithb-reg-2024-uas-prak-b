package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;

public class UpdateDeliveryDetailView extends JFrame {
    private JTextField transactionIdField;
    private JComboBox<String> statusComboBox;
    private JTextField currentPositionField;
    private JTextField evidencePathField;
    private JButton chooseFileButton;
    private JTextField updatedByField;
    private JButton saveButton;
    private JButton backButton;
    private JFileChooser fileChooser;

    public UpdateDeliveryDetailView() {
        setTitle("Update Delivery Detail");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(235, 200, 146));

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setBackground(new Color(235, 200, 146));

        formPanel.add(new JLabel("Transaction ID:"));
        transactionIdField = new JTextField();
        transactionIdField.setEditable(false);
        formPanel.add(transactionIdField);

        formPanel.add(new JLabel("Status:"));
        String[] statuses = { "pending", "in_progress", "on_delivery", "arrived" };
        statusComboBox = new JComboBox<>(statuses);
        formPanel.add(statusComboBox);

        formPanel.add(new JLabel("Current Position:"));
        currentPositionField = new JTextField();
        formPanel.add(currentPositionField);

        formPanel.add(new JLabel("Evidence:"));
        JPanel evidencePanel = new JPanel(new BorderLayout(5, 0));
        evidencePathField = new JTextField();
        evidencePathField.setEditable(false);
        chooseFileButton = new JButton("Choose File");
        evidencePanel.add(evidencePathField, BorderLayout.CENTER);
        evidencePanel.add(chooseFileButton, BorderLayout.EAST);
        formPanel.add(evidencePanel);

        formPanel.add(new JLabel("Updated By:"));
        updatedByField = new JTextField();
        formPanel.add(updatedByField);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(235, 200, 146));
        saveButton = new JButton("Save");
        backButton = new JButton("Back");
        buttonPanel.add(saveButton);
        buttonPanel.add(backButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);

        fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            public boolean accept(File f) {
                return f.getName().toLowerCase().endsWith(".jpg") ||
                        f.getName().toLowerCase().endsWith(".png") ||
                        f.getName().toLowerCase().endsWith(".jpeg") ||
                        f.isDirectory();
            }

            public String getDescription() {
                return "Image Files (*.jpg, *.png, *.jpeg)";
            }
        });

        chooseFileButton.addActionListener(e -> {
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                evidencePathField.setText(selectedFile.getAbsolutePath());
            }
        });
    }

    public String getTransactionId() {
        return transactionIdField.getText();
    }

    public String getStatus() {
        return (String) statusComboBox.getSelectedItem();
    }

    public String getCurrentPosition() {
        return currentPositionField.getText();
    }

    public String getEvidencePath() {
        return evidencePathField.getText();
    }

    public String getUpdatedBy() {
        return updatedByField.getText();
    }

    public void setTransactionId(String id) {
        transactionIdField.setText(id);
    }

    public void addSaveListener(ActionListener listener) {
        saveButton.addActionListener(listener);
    }

    public void addBackListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }
}