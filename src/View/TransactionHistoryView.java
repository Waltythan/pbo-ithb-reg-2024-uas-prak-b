package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class TransactionHistoryView extends JFrame {
    private JTable historyTable;
    private JButton backButton;
    private DefaultTableModel tableModel;
    private ActionListener viewDetailListener;
    private JButton updateDetailButton;

    public TransactionHistoryView() {
        setTitle("Transaction History");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(235, 200, 146));

        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == getColumnCount() - 1;
            }
        };
        historyTable = new JTable(tableModel);
        historyTable.setRowHeight(35);
        
        mainPanel.add(new JScrollPane(historyTable), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        backButton = new JButton("Back");
        updateDetailButton = new JButton("Update Detail");
        buttonPanel.add(backButton);
        buttonPanel.add(updateDetailButton);
        buttonPanel.setBackground(new Color(235, 200, 146));

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);
    }

    public void setTableData(Object[][] data, String[] columnNames) {
        tableModel.setDataVector(data, columnNames);
        
        TableColumn buttonColumn = historyTable.getColumnModel().getColumn(columnNames.length - 1);
        buttonColumn.setCellRenderer(new ButtonRenderer());
        buttonColumn.setCellEditor(new ButtonEditor(new JCheckBox(), viewDetailListener));
    }

    public String getSelectedTransactionId() {
        int row = historyTable.getSelectedRow();
        return row != -1 ? historyTable.getValueAt(row, 0).toString() : null;
    }

    public void addViewDetailListener(ActionListener listener) {
        this.viewDetailListener = listener;
    }

    public void addBackListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }

    public void addUpdateDetailListener(ActionListener listener) {
        updateDetailButton.addActionListener(listener);
    }
}

class ButtonRenderer extends JButton implements TableCellRenderer {
    public ButtonRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        setText("View Detail");
        return this;
    }
}

class ButtonEditor extends DefaultCellEditor {
    private JButton button;
    private String transactionId;
    private ActionListener actionListener;

    public ButtonEditor(JCheckBox checkBox, ActionListener listener) {
        super(checkBox);
        this.actionListener = listener;
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(e -> {
            fireEditingStopped();
            if (actionListener != null) {
                actionListener.actionPerformed(e);
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        transactionId = table.getValueAt(row, 0).toString();
        button.setText("View Detail");
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        return transactionId;
    }
}