package com.mygdx.game.elementos;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.JuegoServer;
import com.mygdx.game.pantallas.PantallaFin;
import com.mygdx.game.sprites.Enemigo;
import com.mygdx.game.sprites.Ninja;
import com.mygdx.game.utiles.Render;


public class MundoContactListener implements ContactListener{

	@Override
	public void beginContact(Contact contact) {

		Fixture fixA = contact.getFixtureA();
		Fixture fixB = contact.getFixtureB();

		int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
		
		switch (cDef) {
			case JuegoServer.ENEMIGO_CUERPO_BIT | JuegoServer.NINJA_BIT :
				if(fixA.getFilterData().categoryBits == JuegoServer.ENEMIGO_CUERPO_BIT) {
					((Enemigo)fixA.getUserData()).hitEnCuerpo((Ninja) fixB.getUserData());
				}else {
					((Enemigo)fixB.getUserData()).hitEnCuerpo((Ninja) fixA.getUserData());
				}
				break;

			case JuegoServer.ENEMIGO_BIT | JuegoServer.NINJA_ESPADA_BIT :
				if(fixA.getFilterData().categoryBits == JuegoServer.ENEMIGO_CUERPO_BIT) {
					((Enemigo)fixA.getUserData()).hitEnCuerpo((Ninja) fixB.getUserData());
				}else {
					((Enemigo)fixB.getUserData()).hitEnCuerpo((Ninja) fixA.getUserData());
				}
				break;

				
			case JuegoServer.ENEMIGO_BIT | JuegoServer.OBJETO_BIT :
				if(fixA.getFilterData().categoryBits == JuegoServer.ENEMIGO_BIT) {
					((Enemigo)fixA.getUserData()).reverseVelocidad(true, false);
				}else {
					((Enemigo)fixB.getUserData()).reverseVelocidad(true, false);
				}
				break;
				
			
			case JuegoServer.ENEMIGO_BIT | JuegoServer.ENEMIGO_BIT :
				((Enemigo)fixA.getUserData()).reverseVelocidad(true, false);
				((Enemigo)fixB.getUserData()).reverseVelocidad(true, false);
				break;
				
			case JuegoServer.NINJA_BIT | JuegoServer.ENEMIGO_BIT :
				Hud.restVida(2);

				if(Hud.vida == 0) {
					Render.app.setScreen(new PantallaFin());
				}
				
				break;
				
			case JuegoServer.NADA_BIT | JuegoServer.NINJA_BIT:
				Hud.restVida(2);

				if(Hud.vida == 0) {
					Render.app.setScreen(new PantallaFin());
				}
			break;
		}
	} 



	@Override
	public void endContact(Contact contact) {

		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		
		
	}

}
