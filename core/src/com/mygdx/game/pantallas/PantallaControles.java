package com.mygdx.game.pantallas;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.elementos.Imagen;
import com.mygdx.game.elementos.Texto;
import com.mygdx.game.utiles.Config;
import com.mygdx.game.utiles.Recursos;
import com.mygdx.game.utiles.Render;

public class PantallaControles  implements Screen {
	
	SpriteBatch b;
	Imagen fondo;
	boolean fadeInTerminado = false, termina = false;
	float a = 0;
	float contTiempo = 0, tiempoEspera = 5;
	float contTiempoTermina = 0, tiempoTermina= 5;

	Texto opciones[] = new Texto[4]; 
	String textos[] = {"W arriba", "S abajo", "D derecha", "A izquierda" };
	
	@Override
	public void show() {
		fondo = new Imagen(Recursos.FONDOMENU); 
		fondo.setSize(Config.ANCHO, Config.ALTO);
		b = Render.batch;
		
		int avance = 50;
		
		for (int i = 0; i < opciones.length; i++) {
			opciones[i] = new Texto(Recursos.FUENTELETRAS, 70, Color.BLACK, true);
			opciones[i].setTexto(textos[i]);
			
			opciones[i].setPosition(100, 600);
			opciones[i].setPosition((Config.ANCHO/2)-(opciones[i].getAncho()/2),(Config.ALTO/2)+(opciones[0].getAlto()/2)-((opciones[i].getAlto()*i)+(avance*i)) );
		}
		
		fondo.setTransparencia(a);
		
	}

	@Override
	public void render(float delta) {

		
		b.begin();
		fondo.dibujar();
		for (int i = 0; i < opciones.length; i++) {
			opciones[i].dibujar();
		}
		b.end();
		
		procesarFade();
	}

	
	private void procesarFade() {
		if (! fadeInTerminado) {
			a+=0.01f;
			if(a > 1) {
				a = 1;
				fadeInTerminado = true;
			}
		} else {
			contTiempo += 0.05f;
			if(contTiempo>tiempoEspera) {
				a -= 0.01f;
				if(a < 0) {
					a = 0;
					termina = true;
				}
			}
		}
		fondo.setTransparencia(a);
		
		if(termina) {
			contTiempoTermina+=0.1f;
			if(contTiempoTermina>tiempoTermina) {
//				Render.app.setScreen(new PantallaNivelUno(this));
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
		
	}

}
