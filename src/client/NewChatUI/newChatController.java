package client.NewChatUI;

import client.HomeUI.homeController;
import domain.Session;
import domain.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class newChatController {
    private Session session;

    @FXML
    private Label txt_username;
    @FXML
    private TextField txt_chatname;

    public newChatController() {
    }

    public void setSettings(Session session)
    {
        this.session = session;
        this.txt_username.setText("Hello " + session.getUser().getUsername());
    }

    @FXML
    private void createChat()
    {
        if (!txt_chatname.getText().trim().isEmpty())
        {
            try {
                session.getServer().createChat(txt_chatname.getText(),session.getUser().getId());
                toHomeScreen();
            } catch (RemoteException e) {
                errorServer();
            }

        }
    }

    private void toHomeScreen()  {
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
    private void errorServer()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText("No connection to Server");
        alert.setContentText("The server is unavailable at this time, try again later.");
        alert.show();
    }
}
