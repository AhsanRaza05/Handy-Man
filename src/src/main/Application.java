package main;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.fxml.FXMLLoader.load;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {

        //FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));

        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Login.fxml"));
        //Scene scene = new Scene(fxmlLoader.load(), 575, 400);

        Parent root = load((getClass().getResource("/view/Login.fxml")));
        Scene scene = new Scene(root,575, 400);
        stage.setTitle("Handy-man");
        stage.getIcons().add(new Image("https://www.pinclipart.com/picdir/big/54-547972_handyman-bill-can-clipart.png"));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}


