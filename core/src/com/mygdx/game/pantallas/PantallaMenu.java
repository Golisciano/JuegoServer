package com.mygdx.game.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.elementos.Imagen;
import com.mygdx.game.elementos.Texto;
import com.mygdx.game.io.EntradasMenu;
import com.mygdx.game.utiles.Config;
import com.mygdx.game.utiles.Recursos;
import com.mygdx.game.utiles.Render;

public class PantallaMenu implements Screen {

	Imagen fondo;
	SpriteBatch b;
	

	Texto opciones[] = new Texto[3]; 
	String textos[] = {"Nueva partida", "Online", "Salir"};
	
	int opc= 1;
	public float tiempo = 0;
	
	EntradasMenu entradas = new EntradasMenu(this);  
	
	
	
	@Override
	public void show() {
		
		fondo = new Imagen(Recursos.FONDOMENU); 
		fondo.setSize(Config.ANCHO, Config.ALTO);
		b = Render.batch;

		Gdx.input.setInputProcessor(entradas);
		
		int avance = 60;
		
		for (int i = 0; i < opciones.length; i++) {
			opciones[i] = new Texto(Recursos.FUENTELETRAS, 80, Color.BLACK, true);
			opciones[i].setTexto(textos[i]);
			
			opciones[i].setPosition((Config.ANCHO/2)-(opciones[i].getAncho()/2),(Config.ALTO/2)+(opciones[0].getAlto()/2)-((opciones[i].getAlto()*i)+(avance*i)) );
		}
		
	}

	@Override
	public void render(float delta) {
		
		
		b.begin();
			fondo.dibujar();
			for (int i = 0; i < opciones.length; i++) {
				opciones[i].dibujar();
			}
		b.end();
		
		
		tiempo+=delta;
		
		if(entradas.isAbajo()) {
			if(tiempo > 0.01f) {
				opc++;
				if(opc>3) {
					opc=1;
				}
			}
		}
		
		if(entradas.isArriba()) {
			if(tiempo > 0.1f) {
				opc--;
				if(opc<1) {
					opc=3;
				}
			}
		}
		
		
		for (int i = 0; i < opciones.length; i++) {
			if(i==(opc-1)) {
				opciones[i].setColor(Color.RED);
			} else {
				opciones[i].setColor(Color.BLACK);
			}			
		}
		
		if(entradas.isEnter()) {
			if(opc==1) {
				Render.app.setScreen(new PantallaControles());
			}
		}
			if(entradas.isEnter()) {
				if(opc==2) {
					Render.app.setScreen(new PantallaControles());
				}
			}
			
			if(entradas.isEnter()) {
				if(opc==3) {
					Gdx.app.exit();;
				}
			}

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
		Render.batch.dispose();
	}

}
