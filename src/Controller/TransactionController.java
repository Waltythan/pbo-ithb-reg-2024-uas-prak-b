package Controller;

import View.AddTransactionView;
import View.TransactionHistoryView;
import View.UpdateDeliveryDetailView;
import View.DeliveryDetailView;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class TransactionController {
    private DatabaseHandler dbHandler;
    private AddTransactionView addView;
    private TransactionHistoryView historyView;
    private DeliveryDetailView detailView;
    private int currentUserId;
    private UpdateDeliveryDetailView updateDetailView;

    public TransactionController(DatabaseHandler dbHandler) {
        this.dbHandler = dbHandler;
        this.addView = new AddTransactionView();
        this.historyView = new TransactionHistoryView();
        this.detailView = new DeliveryDetailView();
        this.updateDetailView = new UpdateDeliveryDetailView();

        addView.addSaveListener(e -> handleSaveTransaction());
        addView.addBackListener(e -> addView.dispose());
        historyView.addViewDetailListener(e -> handleViewDetail());
        historyView.addBackListener(e -> historyView.dispose());
        historyView.addUpdateDetailListener(e -> showUpdateDetailView());
        detailView.addBackListener(e -> detailView.dispose());
        updateDetailView.addSaveListener(e -> handleSaveDeliveryDetail());
        updateDetailView.addBackListener(e -> updateDetailView.dispose());

        loadDeliveryTypes();
    }

    public void showAddTransactionView(int userId) {
        this.currentUserId = userId;
        addView.setVisible(true);
    }

    public void showHistoryView(int userId) {
        this.currentUserId = userId;
        loadTransactionHistory();
        historyView.setVisible(true);
    }

    private void loadDeliveryTypes() {
        try {
            dbHandler.connect();
            String query = "SELECT name FROM delivery_category";
            ResultSet rs = dbHandler.con.createStatement().executeQuery(query);

            List<String> types = new ArrayList<>();
            while (rs.next()) {
                types.add(rs.getString("name"));
            }
            addView.setDeliveryTypes(types.toArray(new String[0]));

            rs.close();
            dbHandler.disconnect();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(addView, "Error: " + ex.getMessage());
        }
    }

    private void handleSaveTransaction() {
        try {
            if (!validateTransactionInput()) {
                return;
            }
    
            dbHandler.connect();
            
            String query = "SELECT MAX(id) as last_id FROM transaction";
            PreparedStatement psId = dbHandler.con.prepareStatement(query);
            ResultSet rs = psId.executeQuery();
            
            int nextId = 1;
            if (rs.next() && rs.getObject("last_id") != null) {
                nextId = rs.getInt("last_id") + 1;
            }
            
            int pricePerKg = getPricePerKg(addView.getSelectedDeliveryType());
            int totalCost = pricePerKg * addView.getWeight();
    
            query = "INSERT INTO transaction (id, customer_id, delivery_type, expected_weight, " +
                    "total_cost, receipt_name, receipt_address, receipt_phone) VALUES " +
                    "(?, ?, ?, ?, ?, ?, ?, ?)";
    
            PreparedStatement ps = dbHandler.con.prepareStatement(query);
            ps.setInt(1, nextId);
            ps.setInt(2, currentUserId);
            ps.setString(3, addView.getSelectedDeliveryType());
            ps.setInt(4, addView.getWeight());
            ps.setInt(5, totalCost);
            ps.setString(6, addView.getRecipientName());
            ps.setString(7, addView.getRecipientAddress());
            ps.setString(8, addView.getRecipientPhone());
            ps.executeUpdate();
    
            String detailQuery = "INSERT INTO delivery_details (transaction_id, status) VALUES (?, 'pending')";
            PreparedStatement detailPs = dbHandler.con.prepareStatement(detailQuery);
            detailPs.setInt(1, nextId);
            detailPs.executeUpdate();
                
            JOptionPane.showMessageDialog(addView, "Transaksi berhasil disimpan!");
            addView.dispose();
    
            rs.close();
            psId.close();
            detailPs.close();
            ps.close();
            dbHandler.disconnect();
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(addView, "Error: " + ex.getMessage());
        }
    }

    private boolean validateTransactionInput() {
        try {
            if (addView.getWeight() <= 0) {
                JOptionPane.showMessageDialog(addView, "Weightnya kudu lebih guede dari ndol mas!");
                return false;
            }
            if (addView.getRecipientName().trim().isEmpty() ||
                    addView.getRecipientAddress().trim().isEmpty() ||
                    addView.getRecipientPhone().trim().isEmpty()) {
                JOptionPane.showMessageDialog(addView, "Diisi dulu kocak!");
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(addView, "Masukin weightnya yang bener!");
            return false;
        }
    }

    private int getPricePerKg(String deliveryType) throws Exception {
        String query = "SELECT price_per_kg FROM delivery_category WHERE name = ?";
        PreparedStatement ps = dbHandler.con.prepareStatement(query);
        ps.setString(1, deliveryType);
        ResultSet rs = ps.executeQuery();
    
        if (rs.next()) {
            return rs.getInt("price_per_kg");
        }
        throw new Exception("Delivery Type tidak ditemukan!");
    }

    private void loadTransactionHistory() {
        try {
            dbHandler.connect();
            String query = "SELECT t.id, t.delivery_type, t.expected_weight, t.total_cost, " +
                    "t.created_at, MAX(d.date) as latest_update " +
                    "FROM transaction t " +
                    "LEFT JOIN delivery_details d ON t.id = d.transaction_id " +
                    "WHERE t.customer_id = ? " +
                    "GROUP BY t.id, t.delivery_type, t.expected_weight, t.total_cost, t.created_at " +
                    "ORDER BY t.created_at DESC";

            PreparedStatement ps = dbHandler.con.prepareStatement(query);
            ps.setInt(1, currentUserId);
            ResultSet rs = ps.executeQuery();

            List<Object[]> dataList = new ArrayList<>();
            while (rs.next()) {
                Object[] row = new Object[7];
                row[0] = rs.getString("id");
                row[1] = rs.getString("delivery_type");
                row[2] = rs.getInt("expected_weight");
                row[3] = rs.getInt("total_cost");
                row[4] = rs.getString("created_at");
                row[5] = rs.getString("latest_update");
                row[6] = "View Detail";
                dataList.add(row);
            }

            String[] columnNames = {
                    "Transaction ID", "Type", "Weight", "Cost", "Created At", "Updated At", "Action"
            };

            Object[][] data = dataList.toArray(new Object[0][]);
            historyView.setTableData(data, columnNames);

            rs.close();
            ps.close();
            dbHandler.disconnect();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(historyView, "Error: " + ex.getMessage());
        }
    }

    private void handleViewDetail() {
        String transactionId = historyView.getSelectedTransactionId();
        if (transactionId == null) {
            JOptionPane.showMessageDialog(historyView, "Pilih dulu salah satu transaksinya!");
            return;
        }

        try {
            dbHandler.connect();
            String query = "SELECT status, current_position, evidence, date, updated_by " +
                    "FROM delivery_details WHERE transaction_id = ? " +
                    "ORDER BY date DESC";

            PreparedStatement ps = dbHandler.con.prepareStatement(query);
            ps.setString(1, transactionId);
            ResultSet rs = ps.executeQuery();

            List<Object[]> dataList = new ArrayList<>();
            while (rs.next()) {
                Object[] row = new Object[5];
                row[0] = rs.getString("status");
                row[1] = rs.getString("current_position");
                row[2] = rs.getString("evidence");
                row[3] = rs.getString("date");
                row[4] = rs.getString("updated_by");
                dataList.add(row);
            }

            String[] columnNames = {
                    "Status", "Position", "Evidence", "Date", "Updated By"
            };

            Object[][] data = dataList.toArray(new Object[0][]);
            detailView.setTableData(data, columnNames);
            detailView.setVisible(true);

            rs.close();
            ps.close();
            dbHandler.disconnect();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(historyView, "Error: " + ex.getMessage());
        }
    }

    private void showUpdateDetailView() {
        String transactionId = historyView.getSelectedTransactionId();
        if (transactionId == null) {
            JOptionPane.showMessageDialog(historyView, "Pilih dulu salah satu transaksinya!");
            return;
        }
        updateDetailView.setTransactionId(transactionId);
        updateDetailView.setVisible(true);
    }

    private void handleSaveDeliveryDetail() {
        if (!validateDeliveryDetailInput()) {
            return;
        }

        try {
            dbHandler.connect();
            String query = "INSERT INTO delivery_details (transaction_id, status, current_position, " +
                    "evidence, updated_by, date) VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = dbHandler.con.prepareStatement(query);
            ps.setString(1, updateDetailView.getTransactionId());
            ps.setString(2, updateDetailView.getStatus());
            ps.setString(3, updateDetailView.getCurrentPosition());
            ps.setString(4, updateDetailView.getEvidencePath());
            ps.setString(5, updateDetailView.getUpdatedBy());
            ps.setTimestamp(6, new java.sql.Timestamp(System.currentTimeMillis()));

            int result = ps.executeUpdate();

            if (result > 0) {
                JOptionPane.showMessageDialog(updateDetailView,
                        "Detail delivery berhasil diupdate!");
                updateDetailView.dispose();
                handleViewDetail();
            } else {
                JOptionPane.showMessageDialog(updateDetailView,
                        "Gagal update detail delivery!");
            }

            ps.close();
            dbHandler.disconnect();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(updateDetailView, "Error: " + ex.getMessage());
        }
    }

    private boolean validateDeliveryDetailInput() {
        String currentPosition = updateDetailView.getCurrentPosition();
        String evidencePath = updateDetailView.getEvidencePath();
        String updatedBy = updateDetailView.getUpdatedBy();

        if (currentPosition.isEmpty() || evidencePath.isEmpty() || updatedBy.isEmpty()) {
            JOptionPane.showMessageDialog(updateDetailView,
                    "Semua field harus diisi!");
            return false;
        }

        File evidenceFile = new File(evidencePath);
        if (!evidenceFile.exists()) {
            JOptionPane.showMessageDialog(updateDetailView,
                    "Mana buktinya woy!");
            return false;
        }

        return true;
    }
}