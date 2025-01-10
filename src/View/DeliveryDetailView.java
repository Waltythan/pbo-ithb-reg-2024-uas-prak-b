package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;

public class DeliveryDetailView extends JFrame {
    private JTable detailTable;
    private JButton backButton;
    private DefaultTableModel tableModel;

    public DeliveryDetailView() {
        setTitle("Delivery Detail");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(235, 200, 146));

        tableModel = new DefaultTableModel();
        detailTable = new JTable(tableModel);
        mainPanel.add(new JScrollPane(detailTable), BorderLayout.CENTER);

        backButton = new JButton("Back");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(backButton);
        buttonPanel.setBackground(new Color(235, 200, 146));

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);
    }

    public void setTableData(Object[][] data, String[] columnNames) {
        tableModel.setDataVector(data, columnNames);
    }

    public void addBackListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }
}