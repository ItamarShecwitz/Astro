package com.itamar.astro.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;
import java.util.List;

/** First screen of the application. Displayed after the application is created. */
public class FirstScreen implements Screen {
	private FitViewport viewport;
	private SpriteBatch batch;
	Texture astroidTexture;
	Texture spaceTexture;
	public List<Astroid> astroids;
	private Player player;

	public FirstScreen(SpriteBatch batch){
		this.batch = batch;

	}
	@Override
	public void show() {
		astroidTexture = new Texture("astroid.png");
		astroids = new ArrayList<>();
		viewport = new FitViewport(640, 360);
		player = new Player(this);
		spaceTexture = new Texture("space.png");
	}

	public void addAstroid(){
		astroids.add(new Astroid());
	}

	public void update(float delta){
		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
			addAstroid();
		}

		for(int i = 0; i < astroids.size(); i++) {
			astroids.get(i).update(delta);
			if(astroids.get(i).isOutOfBounds()&& !player.inSelectingMode()) {
				astroids.get(i).free();
				astroids.remove(i);
				i--;
			}
		} 

		player.update(delta);
	}
	@Override
	public void render(float delta) {
		// Draw your screen here. "delta" is the time since last render in seconds.
		update(delta);
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		viewport.apply();
		batch.setProjectionMatrix(viewport.getCamera().combined);
		batch.begin();
		//draw space
		batch.draw(spaceTexture, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
		//draw player
		player.render(batch);
		//draw astroids
		for(int i = 0; i < astroids.size(); i++){
			astroids.get(i).render(batch);
		}

		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		System.out.println(width);
		System.out.println(height);
		viewport.update(width, height, true);
		// Resize your screen here. The parameters represent the new window size.
	}

	@Override
	public void pause() {
		// Invoked when your application is paused.
	}

	@Override
	public void resume() {
		// Invoked when your application is resumed after pause.
	}

	@Override
	public void hide() {
		// This method is called when another screen replaces this one.
	}

	@Override
	public void dispose() {
		// Destroy screen's assets here.
	}
}