package Controller;

import Model.Customer;
import View.LoginView;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

public class LoginController {
    private DatabaseHandler dbHandler;
    private LoginView loginView;
    private MainController mainController;

    public LoginController(DatabaseHandler dbHandler, MainController mainController) {
        this.dbHandler = dbHandler;
        this.mainController = mainController;
        this.loginView = new LoginView();

        loginView.addLoginListener(e -> handleLogin());
        loginView.addBackListener(e -> loginView.dispose());
    }

    public void showLoginView() {
        loginView.setVisible(true);
    }

    private void handleLogin() {
        String phone = loginView.getPhone();
        String password = loginView.getPassword();

        try {
            dbHandler.connect();
            String query = "SELECT * FROM customer WHERE phone = ? AND password = ?";
            PreparedStatement ps = dbHandler.con.prepareStatement(query);
            ps.setString(1, phone);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getInt("id"));
                customer.setPhone(rs.getString("phone"));
                customer.setName(rs.getString("name"));
                customer.setAddress(rs.getString("address"));

                mainController.setCurrentUser(customer);
                loginView.dispose();
            } else {
                JOptionPane.showMessageDialog(loginView, "Kayanya ada yang salah deh!");
            }

            rs.close();
            ps.close();
            dbHandler.disconnect();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(loginView, "Error: " + ex.getMessage());
        }
    }
}