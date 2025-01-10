package Controller;

import View.RegisterView;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

public class RegisterController {
    private DatabaseHandler dbHandler;
    private RegisterView registerView;

    public RegisterController(DatabaseHandler dbHandler) {
        this.dbHandler = dbHandler;
        this.registerView = new RegisterView();

        registerView.addRegisterListener(e -> handleRegister());
        registerView.addBackListener(e -> registerView.dispose());
    }

    public void showRegisterView() {
        registerView.setVisible(true);
    }

    private boolean validateFields() {
        String phone = registerView.getPhone();
        String name = registerView.getName();
        String address = registerView.getAddress();
        String password = registerView.getPassword();

        if (phone.isEmpty() || name.isEmpty() || address.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(registerView, 
                "Semua field harus diisi ya kang!", 
                "Validasi Error", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }

        return true;
    }

    private void handleRegister() {
        if (!validateFields()) {
            return;
        }

        String phone = registerView.getPhone();
        String name = registerView.getName();
        String address = registerView.getAddress();
        String password = registerView.getPassword();

        try {
            dbHandler.connect();
            
            String checkQuery = "SELECT * FROM customer WHERE phone = ?";
            PreparedStatement checkPs = dbHandler.con.prepareStatement(checkQuery);
            checkPs.setString(1, phone);
            ResultSet rs = checkPs.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(registerView, "No. Teleponnya udah kedaftar kocak!");
                return;
            }

            String query = "INSERT INTO customer (phone, name, address, password) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = dbHandler.con.prepareStatement(query);
            ps.setString(1, phone);
            ps.setString(2, name);
            ps.setString(3, address);
            ps.setString(4, password);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(registerView, "Gokil, Registrasi berhasil kang!");
            registerView.dispose();

            ps.close();
            dbHandler.disconnect();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(registerView, "Error: " + ex.getMessage());
        }
    }
}