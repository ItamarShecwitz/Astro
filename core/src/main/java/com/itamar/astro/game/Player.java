package com.itamar.astro.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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
    private int astroidChosen;
    private FirstScreen screen;

    public Player(FirstScreen screen){
        x = 304;
        y = 164;
        width = 32;
        height = 32;
        texture = new Texture("player.png");
        this.screen = screen;
    }

    ///////////////////// HERE
    public void update (float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.X)) {
            isSelecting = true;
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
        
    }

    public void move(float delta){

    }


    /**
     * Updates to do while in selection mode
     */
    private void selectingMode() {
        if(screen.astroids.size() <= astroidChosen){
            astroidChosen = screen.astroids.size()-1;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            if(astroidChosen != screen.astroids.size()-1){
                astroidChosen++;
            }else{
                astroidChosen = 0;
            }
        } else if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            if(astroidChosen != 0){
                astroidChosen--;
            }else{
                astroidChosen = screen.astroids.size()-1;
            }
        }
        // Selection logic: left/right
//        screen.astroids.get(astroidChosen).;
        System.out.println("Selecting!!!!");

    }

    private void goToAstroid() {
        System.out.println("Going to astroid!!!");
    }
    //////////////////////// LOGIC: numbers/booleans/logic

public boolean inSelectingMode(){return isSelecting;}

    /////////////////////// RENDER: take the current state and draw it

    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y, width, height);
        if(isSelecting){
            Astroid chosen = screen.astroids.get(astroidChosen);

            batch.draw(texture, chosen.getPos().x, chosen.getPos().y, width, height);
        }
    }

    ////////////////////////




}
