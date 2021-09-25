package com.itamar.astro.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
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
	private float selectionTime;


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

		float centerX = viewport.getWorldWidth() / 2f;
		float centerY = viewport.getWorldHeight() / 2f;

		for(int i = 0; i < 360; i += 36) {
			astroids.add(new Astroid(centerX + MathUtils.sinDeg(i) * 100, centerY + MathUtils.cosDeg(i) * 100));
		}
	}

	public void addAstroid(){
		astroids.add(new Astroid());
	}

	public void update(float delta){

		if(player.inSelectingMode()){
			selectionTime += delta;
			if(selectionTime > 1) {
				selectionTime = 1;
			}
			delta /= 10;
		} else {
			selectionTime -= delta * 5;
			if(selectionTime < 0) selectionTime = 0;
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
			addAstroid();
		}

		((OrthographicCamera)viewport.getCamera()).zoom = Interpolation.exp10Out.apply(1, 0.8f, selectionTime);

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