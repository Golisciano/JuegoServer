package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.pantallas.PantallaNivelUno;

public abstract class Enemigo extends Sprite{

	protected World mundo;
	protected PantallaNivelUno screen; 
	public Body b2body;
	public Vector2 velocidad;
	
	public Enemigo(PantallaNivelUno screen , float x, float y) {
		
		this.mundo = screen.getMundo();
		this.screen = screen;
		setPosition(x, y);
		defineEnemigo();
		velocidad = new Vector2(-1, -2);
		b2body.setActive(false);
	}
	
	
	protected abstract void defineEnemigo();
	public abstract void update (float dt);
	public abstract void hitEnCuerpo(Ninja ninja);


	public void reverseVelocidad(boolean x , boolean y) {
		if(x) {
			velocidad.x = -velocidad.x;
		}
		if(y) {
			velocidad.y = -velocidad.y;
		}
	}





	
	
	
}
