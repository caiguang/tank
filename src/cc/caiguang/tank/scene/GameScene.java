package cc.caiguang.tank.scene;

import cc.caiguang.tank.Director;
import cc.caiguang.tank.sprite.Background;
import cc.caiguang.tank.sprite.Bullet;
import cc.caiguang.tank.sprite.Crate;
import cc.caiguang.tank.sprite.Explode;
import cc.caiguang.tank.sprite.Rock;
import cc.caiguang.tank.sprite.Tank;
import cc.caiguang.tank.sprite.Tree;
import cc.caiguang.tank.util.Direction;
import cc.caiguang.tank.util.Group;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class GameScene {

    private Canvas canvas = new Canvas(Director.WIDTH, Director.HEIGHT);
    private GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

    private KeyProcess keyProcess = new KeyProcess();
    private Refresh refresh = new Refresh();
    private boolean running = false;

    private Background background = new Background();
    private Tank self = null;
    public List<Bullet> bullets = new ArrayList<>();
    public List<Tank> tanks = new ArrayList<>();
    public List<Explode> explodes = new ArrayList<>();
    public List<Crate>  crates = new ArrayList<>();
    public List<Rock> rocks = new ArrayList<>();
    public List<Tree> trees = new ArrayList<>();


    private void paint() {
        background.paint(graphicsContext);
        self.paint(graphicsContext);
        self.impact(tanks);
        self.impact(crates);
        self.impact(rocks);

        for (int i = 0; i < bullets.size(); i++) {
            Bullet b = bullets.get(i);
            b.paint(graphicsContext);
            b.impactCrates(crates);
            b.impactTank(tanks);
            b.impactRocks(rocks);
            b.impactTank(self);
        }

        for (int i = 0; i < tanks.size(); i++) {
            Tank tank = tanks.get(i);
            tank.paint(graphicsContext);
            tank.impact(crates);
            tank.impact(self);
            tank.impact(rocks);
            tank.impact(tanks);
        }

        for (int i = 0; i < explodes.size(); i++) {
            Explode e = explodes.get(i);
            e.paint(graphicsContext);
        }

        for (int i = 0; i < crates.size(); i++) {
            Crate crate = crates.get(i);
            crate.paint(graphicsContext);
        }

        for (int i = 0; i < rocks.size(); i++) {
            Rock rock = rocks.get(i);
            rock.paint(graphicsContext);
        }

        for (int i = 0; i < trees.size(); i++) {
            Tree tree = trees.get(i);
            tree.paint(graphicsContext);
        }

//        graphicsContext.setFill(Color.GREEN);
        graphicsContext.setFont(new Font(20));
        graphicsContext.fillText("敌军的数量：" + tanks.size(), 800, 60);
        graphicsContext.fillText("子弹的数量：" + bullets.size(), 800, 90);

        if(!self.isAlive()) {
            Director.getInstance().gameOver(false);
        } else if(tanks.isEmpty()) {
            Director.getInstance().gameOver(true);
        }
    }

    public void init(Stage stage) {
        AnchorPane root = new AnchorPane(canvas);
        stage.getScene().setRoot(root);
        stage.getScene().setOnKeyReleased(keyProcess);
        stage.getScene().setOnKeyPressed(keyProcess);
        running = true;
        self = new Tank(400, 500, Group.green, Direction.stop, Direction.up, this);
        initSprite();
        refresh.start();
    }

    private void initSprite() {
        for (int i = 0; i < 6; i++) {
            Tank tank = new Tank(200 + i * 80, 100,Group.red, Direction.stop, Direction.down, this);
            tanks.add(tank);
        }

        for (int i = 0; i < 20; i++) {
            Crate crate1 = new Crate(100 + i* 31, 200 );
            Crate crate2 = new Crate(100 + i* 31, 232 );
            crates.add(crate1);
            crates.add(crate2);
        }

        for (int i = 0; i < 5; i++) {
            Rock rock = new Rock(350 + i * 71, 300);
            rocks.add(rock);
        }

        for (int i = 0; i < 3; i++) {
            Tree tree = new Tree(450 + i * 86, 400);
            trees.add(tree);
        }
    }

    public void clear(Stage stage) {

        stage.getScene().removeEventHandler(KeyEvent.KEY_PRESSED, keyProcess);
        stage.getScene().removeEventHandler(KeyEvent.KEY_RELEASED, keyProcess);
        refresh.stop();
        self = null;
        tanks.clear();
        bullets.clear();
        crates.clear();
        explodes.clear();
        rocks.clear();
        trees.clear();
    }


    private class Refresh extends AnimationTimer {

        @Override
        public void handle(long now) {
            if(running) {
                paint();
            }
        }
    }
    private class KeyProcess implements EventHandler<KeyEvent> {

        @Override
        public void handle(KeyEvent event) {
            KeyCode keyCode = event.getCode();


            if(event.getEventType() == KeyEvent.KEY_RELEASED) {
                if(keyCode.equals(KeyCode.SPACE)) {
                    pauseOrContinue();
                }
                if(self != null) self.released(keyCode);
            }else if(event.getEventType() == KeyEvent.KEY_PRESSED) {
                if(self != null) {
                    self.pressed(keyCode);
                }
            }
        }
    }

    public void pauseOrContinue() {
        if(running) {
            running = false;
        }else {
            running = true;
        }
    }
}
