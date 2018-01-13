package client.registerui;

import client.loginui.loginController;
import domain.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;

import static domain.CryptWithMD5.md5;
import static javax.swing.JOptionPane.showMessageDialog;

public class registerController {

    private Session session;

    @FXML
    private TextField txt_username;

    @FXML
    private PasswordField txt_password;

    public registerController() throws SQLException, IOException, ClassNotFoundException {
        // Empty constructor with declared exceptions, thus allowing form-to-form navigation.
    }


    @FXML
    private void confirmRegister() {
        if (txt_username.getText() == null || txt_username.getText().trim().isEmpty()) {
            showMessageDialog(null, "Username is a required field.");
            System.out.println("Username is a required field.");
        } else if (txt_username.getText().matches("[0-9]+")) {
            showMessageDialog(null, "Username cannot consist of numbers exclusively.");
            System.out.println("Username cannot consist of numbers exclusively.");
        } else if (txt_password.getText() == null || txt_password.getText().trim().isEmpty()) {
            showMessageDialog(null, "Password is a required field.");
            System.out.println("Password is a required field.");
        } else {
            try {
                if (session.getServer().register(txt_username.getText(), md5(txt_password.getText()))) {
                    backToLogin();
                } else {
                    showMessageDialog(null, "Username is already taken.");
                }
            } catch (RemoteException | NullPointerException e) {
                errorServer();
            }
        }
    }

    @FXML
    private void cancelRegister() {
        backToLogin();
    }

    private void backToLogin() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../loginui/login.fxml"));
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            errorServer();
        }
        loginController controller = fxmlLoader.<loginController>getController();
        Scene loginScreen = new Scene(root);
        Stage stage;
        stage = (Stage) txt_username.getScene().getWindow(); // Weird backwards logic trick to get the current scene window.
        stage.setScene(loginScreen);
        stage.show();
    }

    public void setSession(Session session) {
        this.session = session;
    }

    private void errorServer() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText("No connection to Server");
        alert.setContentText("The server is unavailable at this time, try again later.");
        alert.show();
    }

}
