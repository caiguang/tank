package cc.caiguang.tank.sprite;

import cc.caiguang.tank.Director;
import cc.caiguang.tank.sprite.Sprite;
import cc.caiguang.tank.scene.GameScene;
import cc.caiguang.tank.util.Direction;
import cc.caiguang.tank.util.Group;
import cc.caiguang.tank.util.SoundEffect;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

import java.util.List;
import java.util.Random;

public class Tank extends Role{

    Direction pdir;
    boolean keyup, keydown, keyleft, keyright;
    double oldx, oldy;
    public static Random random = new Random();

    public Tank(double x, double y, Group group, Direction dir, Direction pdir, GameScene gameScene) {
        super(x, y, 60, 60, group, dir, gameScene);
        this.pdir = pdir;
        speed = 5;
        if(group.equals(Group.green)) {
            imageMap.put("up", new Image("image/tank-green-up.png"));
            imageMap.put("down", new Image("image/tank-green-down.png"));
            imageMap.put("left", new Image("image/tank-green-left.png"));
            imageMap.put("right", new Image("image/tank-green-right.png"));
        } else {
            imageMap.put("up", new Image("image/tank-red-up.png"));
            imageMap.put("down", new Image("image/tank-red-down.png"));
            imageMap.put("left", new Image("image/tank-red-left.png"));
            imageMap.put("right", new Image("image/tank-red-right.png"));
        }

    }


    public void pressed(KeyCode keyCode) {
        switch (keyCode) {
            case UP:
                keyup = true;
                break;
            case DOWN:
                keydown = true;
                break;
            case LEFT:
                keyleft = true;
                break;
            case RIGHT:
                keyright = true;
        }
        redirect();
    }

    public void released(KeyCode keyCode) {
        switch (keyCode) {
            case F:
                openFire();
                break;
            case UP:
                keyup = false;
                break;
            case DOWN:
                keydown = false;
                break;
            case LEFT:
                keyleft = false;
                break;
            case RIGHT:
                keyright = false;
        }
        redirect();
    }

    public void redirect() {
        if(keyup && !keydown && !keyleft && !keyright) dir = Direction.up;
        else if(!keyup && keydown && !keyleft && !keyright) dir = Direction.down;
        else if(!keyup && !keydown && keyleft && !keyright) dir = Direction.left;
        else if(!keyup && !keydown && !keyleft && keyright) dir = Direction.right;
        else if(!keyup && !keydown && !keyleft && !keyright) dir = Direction.stop;
    }

    @Override
    public void move() {
        oldx = x;
        oldy = y;
        switch (dir) {
            case up:
                y -= speed;
                break;
            case down:
                y += speed;
                break;
            case left:
                x -= speed;
                break;
            case right:
                x += speed;
                break;
        }

        if(dir != Direction.stop) {
            pdir = dir;
        }

        if(x < 0) x = 0;
        if(y < 0) y = 0;
        if(x > Director.WIDTH - width - 5) x = Director.WIDTH - width - 5;
        if(y > Director.HEIGHT - height - 30) y = Director.HEIGHT - height - 30;

        if(group.equals(Group.red)) {
            int i = random.nextInt(60);
            switch (i) {
                case 15:
                    Direction d[] = Direction.values();
                    dir = d[random.nextInt(d.length)];
                    break;
                case 30:
                    openFire();
                    break;
            }
        }
    }

    @Override
    public void paint(GraphicsContext graphicsContext) {
        if(group.equals(Group.red) && !alive) {
            gameScene.tanks.remove(this);
            return;
        }
        switch (pdir) {
            case up:
                image = imageMap.get("up");
                break;
            case down:
                image = imageMap.get("down");
                break;
            case left:
                image = imageMap.get("left");
                break;
            case right:
                image = imageMap.get("right");
                break;
        }
        super.paint(graphicsContext);
        move();
    }

    public void openFire() {
        double bulletx = x;
        double bullety = y;
        switch (pdir) {
            case up:
                bulletx = x + 25;
                bullety = y;
                break;
            case down:
                bulletx = x + 25;
                bullety = y + height;
                break;
            case left:
                bulletx = x;
                bullety = y + 25;
                break;
            case right:
                bulletx = x + width;
                bullety = y + 25;

        }
        SoundEffect.play("/sound/attack.mp3");
        gameScene.bullets.add(new Bullet(bulletx, bullety, group, pdir, gameScene));
    }

    public boolean impact(Sprite sprite) {
        if(sprite != null && !sprite.equals(this) && getContour().intersects(sprite.getContour())) {
            x = oldx;
            y = oldy;
            return true;
        }
        return false;
    }

    public void impact(List<? extends Sprite> sprites) {
        for (Sprite sprite :sprites) {
            impact(sprite);
        }
    }

}
