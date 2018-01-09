package client.LoginUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class loginController {

    //FXML loads
    @FXML
    private TextField txt_username;
    @FXML
    private PasswordField txt_password;
    @FXML
    private Button btn_login;
    @FXML
    private Button btn_register;


    //LoginController // needs to connect

    //To register screen
    @FXML
    private void toRegisterScreen()  {
        // Set the next "page" (scene) to display.
        // Note that an incorrect path will result in unexpected NullPointer exceptions!
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../Register/Register.fxml"));

        Parent root = null;
        try {
            root = (Parent)fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        RegisterController controller = fxmlLoader.<RegisterController>getController();
        controller.setSession(session);
        // There's no additional data required by the newly opened form.
        Scene registerScreen = new Scene(root);

        Stage stage;
        stage = (Stage) txt_username.getScene().getWindow(); // Weird backwards logic trick to get the current scene window.

        stage.setScene(registerScreen);
        stage.show();
    }


}
