package Controller;

import Model.Customer;
import View.MainMenuView;

public class MainController {
    private DatabaseHandler dbHandler;
    private MainMenuView mainView;
    private LoginController loginController;
    private RegisterController registerController;
    private TransactionController transactionController;
    private Customer currentUser;

    public MainController() {
        dbHandler = new DatabaseHandler();
        mainView = new MainMenuView();
        loginController = new LoginController(dbHandler, this);
        registerController = new RegisterController(dbHandler);
        transactionController = new TransactionController(dbHandler);

        mainView.addLoginListener(e -> showLoginView());
        mainView.addRegisterListener(e -> showRegisterView());
        mainView.addTransactionListener(e -> showAddTransactionView());
        mainView.addHistoryListener(e -> showHistoryView());

        updateMenuState();
        mainView.setVisible(true);
    }

    public void setCurrentUser(Customer user) {
        this.currentUser = user;
        updateMenuState();
    }

    private void updateMenuState() {
        boolean isLoggedIn = (currentUser != null);
        mainView.setLoginButtonEnabled(!isLoggedIn);
        mainView.setRegisterButtonEnabled(!isLoggedIn);
        mainView.setTransactionButtonEnabled(isLoggedIn);
        mainView.setHistoryButtonEnabled(isLoggedIn);

        if (isLoggedIn) {
            mainView.setWelcomeMessage("Selamat datang, Mas/Mbak " + currentUser.getName());
        } else {
            mainView.setWelcomeMessage("Selamat datang di Pratama Delivery!");
        }
    }

    private void showLoginView() {
        loginController.showLoginView();
    }

    private void showRegisterView() {
        registerController.showRegisterView();
    }

    private void showAddTransactionView() {
        if (currentUser != null) {
            transactionController.showAddTransactionView(currentUser.getId());
        }
    }

    private void showHistoryView() {
        if (currentUser != null) {
            transactionController.showHistoryView(currentUser.getId());
        }
    }
}