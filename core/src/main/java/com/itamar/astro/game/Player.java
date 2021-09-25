package com.itamar.astro.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * if x is pressed, enter "selecting mode".
 * if x is not pressed
 *
 *
 */

public class Player {
    private final float width;
    private final float height;
    private Texture texture;
    private float x;
    private float y;
    private boolean isSelecting;
    private int asteroidChosen;
    private FirstScreen screen;
    private Vector2 speed;
    private List<Astroid> sortedAstroids;
    private float currentX;
    private float currentY;
    private Animation<Texture> animationIdle;
    private float animationTime;
    private int frames;

    public Player(FirstScreen screen){
        x = 304;
        y = 164;

        width = 24;
        height = 29;

        texture = new Texture("player.png");
        this.screen = screen;
        speed = new Vector2();
        sortedAstroids = new ArrayList<>();
        animationIdle = new Animation<Texture>(0.2f,
                new Texture("beer_idle_1.png"),
                new Texture("beer_idle_2.png"),
                new Texture("beer_idle_3.png"),
                new Texture("beer_idle_4.png"));
        animationIdle.setPlayMode(Animation.PlayMode.LOOP);
    }

    ///////////////////// HERE
    public void update (float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.X)) {
            startSelectionMode();
        }
        if(screen.astroids.size() == 0) {
            isSelecting = false;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.X) && isSelecting) {
            selectingMode();
        } else if (isSelecting) {
            goToAstroid();
            isSelecting = false;
        }
        move(delta);
        animationTime += delta;
    }

    private void startSelectionMode() {
        isSelecting = true;
         currentX = x;
         currentY = y;
    }

    public void move(float delta){
        x += speed.x*delta;
        y += speed.y*delta;
    }


    /**
     * Updates to do while in selection mode
     */
    private void selectingMode() {

        sortAstroids();

        if(sortedAstroids.size() <= asteroidChosen){
            asteroidChosen = sortedAstroids.size()-1;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            if(asteroidChosen != sortedAstroids.size()-1){
                asteroidChosen++;
            }else{
                asteroidChosen = 0;
            }
        } else if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            if(asteroidChosen != 0){
                asteroidChosen--;
            }else{
                asteroidChosen = sortedAstroids.size()-1;
            }
        }

        System.out.println("Selecting!!!!");
    }

    private void sortAstroids() {
        // Put all astroids from screen.astroids into sortedAstroids, and *sort them* by angle (hint: trig)
        sortedAstroids.clear();
        sortedAstroids.addAll(screen.astroids);
        sortedAstroids.sort(new Comparator<Astroid>() {
            @Override
            public int compare(Astroid o1, Astroid o2) {
                // Check angle of o1, o2
                float o1Angle = MathUtils.atan2(o1.getPos().y-currentY, o1.getPos().x-currentX);
                float o2Angle = MathUtils.atan2(o2.getPos().y-currentY, o2.getPos().x-currentX);

                if(o1Angle<o2Angle){
                    return -1;
                } else if(o1Angle>o2Angle){
                    return 1;
                }
                return 0;
            }
        });
    }

    private void goToAstroid() {
        System.out.println("Going to astroid!!!");
        speed.set(sortedAstroids.get(asteroidChosen).getPos());
        speed.sub(x, y);
        speed.scl(1);
    }
    //////////////////////// LOGIC: numbers/booleans/logic

    public boolean inSelectingMode(){return isSelecting;}

    /////////////////////// RENDER: take the current state and draw it24

    public void render(SpriteBatch batch) {

        Texture keyFrame = animationIdle.getKeyFrame(animationTime);
        batch.draw(keyFrame, x, y, keyFrame.getWidth(), keyFrame.getHeight(), 0, 0, keyFrame.getWidth(), keyFrame.getHeight(),speed.x<0 ,false);


        if(isSelecting){
            Astroid chosen = sortedAstroids.get(asteroidChosen);
            batch.draw(texture, chosen.getPos().x, chosen.getPos().y, width, height);
        }
    }

    ////////////////////////




}
