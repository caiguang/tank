package cc.caiguang.tank;

import cc.caiguang.tank.scene.GameOver;
import cc.caiguang.tank.scene.GameScene;
import cc.caiguang.tank.scene.Index;
import cc.caiguang.tank.util.SoundEffect;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Director {

    public static final double WIDTH = 960, HEIGHT = 640;

    private static Director instance = new Director();
    private Stage stage;
    private GameScene gameScene = new GameScene();

    private Director() {}

    public static Director getInstance() {
        return instance;
    }

    public void init(Stage stage) {
        AnchorPane root = new AnchorPane();
        Scene scene = new Scene(root , WIDTH, HEIGHT);
        stage.setTitle("坦克");
        stage.getIcons().add(new Image("image/logo.png"));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setWidth(WIDTH);
        stage.setHeight(HEIGHT);
        this.stage = stage;
        toIndex();
        stage.show();
    }

    public void toIndex() {
        Index.load(stage);
    }

    public void gameOver(boolean success) {
        String sound = success? "/sound/success.wav" : "/sound/aiyouwodemaya.mp3";
        SoundEffect.play(sound);
        gameScene.clear(stage);
        GameOver.load(stage, success);
    }

    public void gameStart() {
        gameScene.init(stage);
    }
}
