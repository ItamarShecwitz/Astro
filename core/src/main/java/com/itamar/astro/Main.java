package com.itamar.astro;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.itamar.astro.game.FirstScreen;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
	private SpriteBatch batch;
	@Override
	public void create() {
		batch = new SpriteBatch();
		setScreen(new FirstScreen(batch));
	}
}