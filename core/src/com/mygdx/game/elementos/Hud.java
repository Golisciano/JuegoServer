package com.mygdx.game.elementos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.JuegoServer;
import com.mygdx.game.utiles.Config;

public class Hud implements Disposable{
	
	public Stage escena; 
	private Viewport viewport;
	
	
	private Integer timerMundo;
	private float contTiempo; 
	private static Integer puntaje;
	private float contVida;
	public static Integer vida; 
	
	private Label conttempLabel;
	private static Label vidaLabel;
	private static Label puntajeLabel;
	private Label tiempoLabel;
	private Label vidLabel;
	private Label puntLabel;
	
	
	public Hud(SpriteBatch sb) {
		
		timerMundo = 500;
		vida = 2;
		contTiempo = 0;
		puntaje = 0;
		contVida = 0;
		
		viewport = new FitViewport(Config.ANCHO, Config.ALTO, new OrthographicCamera());
		
		escena = new Stage(viewport, sb); 
		
		
		Table tabla = new Table();
		tabla.top();
		tabla.setFillParent(true);
		
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fuentes/MangaStyle.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		
		float nuevoTamaño = 1.2f;
		BitmapFont font = generator.generateFont(parameter);
		generator.dispose();
		
		
		font.getData().setScale(nuevoTamaño);
		Label.LabelStyle nuevoEstilo = new Label.LabelStyle(font, Color.WHITE);
		

		conttempLabel = new Label(String.format("%03d", timerMundo), nuevoEstilo);
		vidaLabel = new Label(String.format("%01d",vida),nuevoEstilo);
		puntajeLabel = new Label(String.format("%06d",puntaje),nuevoEstilo);
		
		tiempoLabel =  new Label("TIEMPO",nuevoEstilo);
		vidLabel =  new Label("VIDA",nuevoEstilo);
		puntLabel = new Label("PUNTAJE",nuevoEstilo);

		tabla.add(vidLabel).expandX().padTop(6);
		tabla.add(tiempoLabel).expandX().padTop(6);
		tabla.add(puntLabel).expandX().padTop(6);
		tabla.row();
		tabla.add(vidaLabel).expandX();
		tabla.add(conttempLabel).expandX();
		tabla.add(puntajeLabel).expandX();
		
		escena.addActor(tabla);
		
	}
	
	public void update(float dt) {
		contTiempo += dt;
		if(contTiempo >= 1) {
			timerMundo--;
			conttempLabel.setText(String.format("%03d", timerMundo));
			contTiempo = 0;
		}
	}
	
	public static void addPuntaje(int valor) {
		puntaje += valor;
		puntajeLabel.setText(String.format("%06d",puntaje));
	}
	
	public static void restVida(int valor) {
		vida -= valor;
		vidaLabel.setText(String.format("%01d",vida));
	}
	
	@Override
	public void dispose() {
		escena.dispose();
	}

}
