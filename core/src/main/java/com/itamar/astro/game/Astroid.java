package com.itamar.astro.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Astroid {
    private Vector2 pos;
    private final float width;
    private final float height;
    private Texture texture;
    private final float speed = 100;
    private Vector2 vMover;

    public Astroid(float x, float y){
        pos = new Vector2(x ,y);
        vMover = new Vector2();
        width = 32;
        height = 32;
        texture = new Texture("astroid.png");
    }

    public Astroid() {
        int direction = MathUtils.random(1, 4);
        Vector2 dest = new Vector2();
        pos = new Vector2();
        if (direction == 1) {
            pos.set(MathUtils.random(0, 640), 392);
            dest.set(MathUtils.random(50, 590), 180);
        } else if (direction == 2) {
            pos.set(MathUtils.random(0, 640), -32);
            dest.set(MathUtils.random(50, 590), 180);
        } else if (direction == 3) {
            pos.set(672, MathUtils.random(0, 360));
            dest.set(320, MathUtils.random(30, 330));
        } else if (direction == 4) {
            pos.set(-32, MathUtils.random(0, 360));
            dest.set(320, MathUtils.random(30, 330));
        }
        vMover = new Vector2();
        vMover.set(dest).sub(pos);
        vMover.nor();

        width = 32;
        height = 32;
        texture = new Texture("astroid.png");
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, pos.x, pos.y, width, height);
    }

    public boolean isOutOfBounds() {
        return this.pos.x <-32 || this.pos.x > 672 || this.pos.y < -32 || this.pos.y > 352;

    }

    public void free() {
        texture.dispose();
    }

    public void update(float delta) {
        pos.mulAdd(vMover, delta*speed);
    }
    public Vector2 getPos(){
        return pos;
    }

}
