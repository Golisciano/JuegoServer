package com.mygdx.game.pantallas;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.JuegoServer;
import com.mygdx.game.io.EntradasFin;
import com.mygdx.game.utiles.Render;

public class PantallaFin implements Screen {

	private Viewport port;
	private Stage stage;
	
	SpriteBatch b;
	
	private Game juego;
	
	EntradasFin entradas = new EntradasFin(this); 
	
	
	public PantallaFin() {
		b = Render.batch;
		this.juego = juego;
		port = new FitViewport(JuegoServer.V_WITDH, JuegoServer.V_HEIGHT, new OrthographicCamera()); 
		stage = new Stage(port , b);
		
		Table tabla = new Table();
		tabla.top();
		tabla.setFillParent(true);
		
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fuentes/TokyoTaiyaki.otf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		
		float nuevoTamaño = 1.7f;
		BitmapFont font = generator.generateFont(parameter);
		generator.dispose();
		
		
		font.getData().setScale(nuevoTamaño);
		Label.LabelStyle nuevoEstilo = new Label.LabelStyle(font, Color.WHITE);
		
		Label finJuegoLabel = new Label("FIN DEL JUEGO", nuevoEstilo);
		
		
		tabla.add(finJuegoLabel).expandX().padTop(90f);
		tabla.row();
		
		
		stage.addActor(tabla);
	}
	
	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {

	}

}
