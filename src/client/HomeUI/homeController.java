package client.HomeUI;

import client.ChatUI.chatController;
import client.NewChatUI.newChatController;
import client.friendui.friendController;
import domain.Chat;
import domain.Friend;
import domain.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class homeController {
    private Session session;
    private List<Chat> chats;
    private Chat selectedChat;

    @FXML
    private Label txt_username;
    @FXML
    private TableView<Chat> tv_chats;
    @FXML
    private TableColumn tc_user;
    @FXML
    private TableColumn tc_chatname;

    public homeController() {
        // Empty constructor with declared exceptions, thus allowing form-to-form navigation.
          }

    public void setSettings(Session session)
    {
        this.session = session;
        this.txt_username.setText("Hello " + session.getUser().getUsername());
        loadChats();
    }

    @FXML
    private void loadChats()
    {
        tv_chats.getItems().clear();
        tc_user.setCellValueFactory(new PropertyValueFactory<Chat, String>("user_Name"));
        tc_chatname.setCellValueFactory(new PropertyValueFactory<Chat,String>("name"));
        try {
            chats = session.getServer().getChats();
            if (!chats.isEmpty()) {
                for (Chat i : chats) {
                    tv_chats.getItems().add(i);
                }
            }
        } catch (RemoteException e) {
            errorServer();
        }
    }

    @FXML
    private void selectedChat()
    {
        if (tv_chats.getSelectionModel().getSelectedItem() != null)
        {
            selectedChat = tv_chats.getSelectionModel().getSelectedItem();
            System.out.println(selectedChat.getUser_Name());
        }
        else
        {
            selectedChat = null;
        }
    }

    @FXML
    private void joinChat(){
        if (selectedChat != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../ChatUI/chat.fxml"));

            Parent root = null;
            try {
                root = (Parent)fxmlLoader.load();
            } catch (IOException e) {
                Logger.getGlobal().log(Level.SEVERE,"homeController",e);
            }
            chatController controller = fxmlLoader.<chatController>getController();
            controller.setup(session, selectedChat);
            // There's no additional data required by the newly opened form.
            Scene registerScreen = new Scene(root);
            Stage stage;
            stage = (Stage) txt_username.getScene().getWindow(); // Weird backwards logic trick to get the current scene window.
            stage.setScene(registerScreen);
            stage.show();
        }
    }

    @FXML
    private void toNewChat()  {
        // Set the next "page" (scene) to display.
        // Note that an incorrect path will result in unexpected NullPointer exceptions!
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../NewChatUI/newChat.fxml"));

        Parent root = null;
        try {
            root = (Parent)fxmlLoader.load();
        } catch (IOException e) {
            Logger.getGlobal().log(Level.SEVERE,"homeController",e);
        }
        newChatController controller = fxmlLoader.<newChatController>getController();
        controller.setSettings(session);
        // There's no additional data required by the newly opened form.
        Scene registerScreen = new Scene(root);

        Stage stage;
        stage = (Stage) txt_username.getScene().getWindow(); // Weird backwards logic trick to get the current scene window.

        stage.setScene(registerScreen);
        stage.show();
    }
    @FXML
    private void toFriends()  {
        // Set the next "page" (scene) to display.
        // Note that an incorrect path will result in unexpected NullPointer exceptions!
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../friendui/friend.fxml"));

        Parent root = null;
        try {
            root = (Parent)fxmlLoader.load();
        } catch (IOException e) {
            Logger.getGlobal().log(Level.SEVERE,"homeController",e);
        }
        friendController controller = fxmlLoader.<friendController>getController();
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
