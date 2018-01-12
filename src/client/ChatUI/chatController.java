package client.ChatUI;

import client.HomeUI.homeController;
import domain.Chat;
import domain.Message;
import domain.Session;
import domain.User;
import interfaces.IListener;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class chatController extends UnicastRemoteObject implements IListener{
    @FXML
    private Label txt_username;
    @FXML
    private ListView lv_messages;
    @FXML
    private TextArea txt_message;

    private Session session;
    private Chat chat;
    private List<Message> messages = new ArrayList<>();
    private AnimationTimer messageTimer;
    boolean autoScroll = false;

    public chatController() throws RemoteException {
        messageTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateListViewMessages();
            }
        };
        messageTimer.start();
    }

    private void updateListViewMessages() {
        lv_messages.setCellFactory(t -> new CustomListCell(chat.getUser_Name(),lv_messages.getWidth()));
        lv_messages.getItems().clear();
        for (Message message:messages
                ) {
            lv_messages.getItems().add(message);
        }
        if (autoScroll) {
            Platform.runLater(() -> lv_messages.scrollTo(lv_messages.getItems().size() - 1));
        }
    }

    public void setup(Session session, Chat chat)
    {
        this.session = session;
        this.chat = chat;
        this.txt_username.setText(chat.getUser().getUsername());
        this.txt_message.setText(chat.getName());
        try {
            session.getServer().addListener(this);
        } catch (RemoteException e) {
            errorServer();
        }
    }
    @Override
    public void setChatMessages(List<Message> messages) throws RemoteException {
        this.messages = messages;
    }

    @Override
    public int getChatId() throws RemoteException {
        return this.chat.getId();
    }

    @Override
    public int getUserId() throws RemoteException {
        return this.session.getUser().getId();
    }

    @Override
    public List<Message> getchatMessages() throws RemoteException {
        return this.messages;
    }

    @Override
    public void addMessage(Message message) throws RemoteException {
        this.messages.add(message);
    }

    @FXML
    private void toHomeScreen(User user)  {
        try {
            session.getServer().removeListener(this);
        } catch (RemoteException e) {
            errorServer();
        }
        messageTimer.stop();
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
