package cc.caiguang.tank.sprite;

import cc.caiguang.tank.scene.GameScene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Explode extends Sprite {
    private int count = 0;
    private static Image[] images = new Image[] {
            new Image("image/explode1.png"),
            new Image("image/explode2.png"),
            new Image("image/explode3.png"),
            new Image("image/explode4.png"),
            new Image("image/explode5.png"),
            new Image("image/explode6.png"),
            new Image("image/explode7.png"),
            new Image("image/explode8.png"),
            new Image("image/explode9.png"),
    };

    public Explode( double x, double y, GameScene gameScene) {
        super(null, x, y, 0, 0, gameScene);
    }

    @Override
    public void paint(GraphicsContext graphicsContext) {
        if(count >= images.length) {
            gameScene.explodes.remove(this);
            return;
        }
        image = images[count];
        double ex = x - image.getWidth()/2;
        double ey = y - image.getHeight()/2;
        graphicsContext.drawImage(image, ex, ey);
        count ++;
    }
}
