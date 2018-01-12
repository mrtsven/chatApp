package client.LoginUI;

import client.HomeUI.homeController;
import client.RegisterUI.registerController;
import domain.CryptWithMD5;
import domain.Session;
import domain.User;
import interfaces.IChatManagerServer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import java.io.IOException;
import java.math.BigInteger;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static domain.CryptWithMD5.md5;
import static javax.swing.JOptionPane.showMessageDialog;

public class loginController {
    private Registry registry;
    private Session session;
    private String ip = "127.0.0.1";
    private int port = 1099;

    @FXML
    private TextField txt_username;
    @FXML
    private PasswordField txt_password;

    public loginController() {
        try {
            System.setProperty("java.rmi.server.hostname","127.0.0.1");
            this.registry = locateRegistry();
            if (registry != null) {
                this.session = new Session((IChatManagerServer) registry.lookup("ChatAppServer"));
            }
        }
        catch (SQLException | IOException | ClassNotFoundException | NotBoundException e) {
            errorServer();
            this.session = null;
        }

    }

    @FXML
    private void login()
    {
        User user;
        if (!txt_username.getText().trim().isEmpty() && !txt_password.getText().trim().isEmpty() && session.getServer() != null) {
            try {
                user = session.getServer().login(txt_username.getText(),md5(txt_password.getText()));
                if (user != null){
                    session.setUser(user);
                    toHomeScreen(user);
                }
                else
                {
                    showMessageDialog(null, "username or password incorrect");
                }
            } catch (RemoteException e) {
                errorServer();
            }
        }
    }

    //Main screen
    private void toHomeScreen(User user)  {
        // Set the next "page" (scene) to display.
        // Note that an incorrect path will result in unexpected NullPointer exceptions!
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../HomeUI/home.fxml"));

        Parent root = null;
        try {
            root = (Parent)fxmlLoader.load();
        } catch (IOException e) {
            Logger.getGlobal().log(Level.SEVERE,"loginController",e);
        }
        homeController controller = fxmlLoader.<homeController>getController();
        controller.setSettings(session);
        // There's no additional data required by the newly opened form.
        Scene registerScreen = new Scene(root);

        Stage stage;
        stage = (Stage) txt_username.getScene().getWindow(); // Weird backwards logic trick to get the current scene window.

        stage.setScene(registerScreen);
        stage.show();
    }

    //To registerUI screen
    @FXML
    private void toRegisterScreen()  {
        // Set the next "page" (scene) to display.
        // Note that an incorrect path will result in unexpected NullPointer exceptions!
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../RegisterUI/register.fxml"));

        Parent root = null;
        try {
            root = (Parent)fxmlLoader.load();
        } catch (IOException e) {
            Logger.getGlobal().log(Level.SEVERE,"loginController",e);
        }
        registerController controller = fxmlLoader.<registerController>getController();
        controller.setSession(session);
        // There's no additional data required by the newly opened form.
        Scene registerScreen = new Scene(root);

        Stage stage;
        stage = (Stage) txt_username.getScene().getWindow(); // Weird backwards logic trick to get the current scene window.

        stage.setScene(registerScreen);
        stage.show();
    }


    private Registry locateRegistry() throws SQLException, IOException, ClassNotFoundException {
        try
        {
            return LocateRegistry.getRegistry(ip, port);
        }
        catch (RemoteException ex) {
            errorServer();
            return null;
        }
    }

    public void setSettings(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    private void errorServer()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText("No connection to Server");
        alert.setContentText("The server is unavailable at this time, try again later.");
        alert.show();
    }
}
