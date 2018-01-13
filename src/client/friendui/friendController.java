package client.friendui;

import client.HomeUI.homeController;
import domain.Chat;
import domain.Friend;
import domain.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class friendController {
    private Session session;

    @FXML
    private Label txt_username;
    @FXML
    private TableView<Friend> tv_friends;
    @FXML
    private Button btn_accept;
    @FXML
    private Button btn_decline;
    @FXML
    private TableColumn tc_user;
    @FXML
    private TableColumn tc_status;
    private List<Friend> friends = new ArrayList<>();
    private Friend selectedFriend;
    public void setSettings(Session session)
    {
        this.session = session;
        this.txt_username.setText("Hello " + session.getUser().getUsername());
        loadFriends();
    }

    private void loadFriends() {
        tv_friends.getItems().clear();
        tc_user.setCellValueFactory(new PropertyValueFactory<Friend, String>("username"));
        tc_status.setCellValueFactory(new PropertyValueFactory<Friend,String>("status"));
        try {
            friends = session.getServer().getFriends(session.getUser().getId());
            if (!friends.isEmpty()) {
                for (Friend i : friends) {
                    tv_friends.getItems().add(i);
                }
            }
        } catch (RemoteException e) {
            errorServer();
        }
    }
    @FXML
    private void selectedFriend()
    {
        if (tv_friends.getSelectionModel().getSelectedItem() != null)
        {
            selectedFriend = tv_friends.getSelectionModel().getSelectedItem();
        }
        else
        {
            selectedFriend = null;
        }
    }
    @FXML
    public void accept()
    {
        if (selectedFriend != null)
        {

        }
    }
    @FXML
    public void decline()
    {
        if (selectedFriend != null)
        {

        }
    }
    @FXML
    public void addFriend()
    {
        if(!txt_username.getText().trim().isEmpty())
        {
            try {
                if (session.getServer().addFriend(session.getUser().getId(),txt_username.getText()))
                {
                    loadFriends();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Succes");
                    alert.setHeaderText("Friend invite");
                    alert.setContentText("Your friend invite has been send");
                    alert.show();
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Error");
                    alert.setHeaderText("Friend invite");
                    alert.setContentText("Oh no, this user does not exist");
                    alert.show();
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.NONE);
            alert.setTitle("Error");
            alert.setHeaderText("Friend invite");
            alert.setContentText("You need to enter a name!");
            alert.show();
        }

    }

    @FXML
    public void toHomeScreen()  {
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
