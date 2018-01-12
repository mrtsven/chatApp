package client.HomeUI;

import domain.Chat;
import domain.Session;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.rmi.RemoteException;
import java.util.List;

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
        tc_chatname.setCellValueFactory(new PropertyValueFactory<Chat,String>("Chatname"));
        try {
            chats = session.getServer().getChats(session.getUser().getId());
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
        }
        else
        {
            selectedChat = null;
        }
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
