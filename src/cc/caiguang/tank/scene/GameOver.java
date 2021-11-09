package cc.caiguang.tank.scene;

import cc.caiguang.tank.controller.OverController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;

public class GameOver {

    public static void load(Stage stage, boolean success) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Index.class.getResource("/fxml/gameOver.fxml"));
            Parent root = fxmlLoader.load();
            OverController overController = fxmlLoader.getController();
            if(success) overController.flagSuccess();
            stage.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
