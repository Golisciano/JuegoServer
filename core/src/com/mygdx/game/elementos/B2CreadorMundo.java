package com.mygdx.game.elementos;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.JuegoServer;
import com.mygdx.game.pantallas.PantallaNivelUno;
import com.mygdx.game.sprites.Arquero;

public class B2CreadorMundo {
	
	private Array<Arquero> arquero;


	public B2CreadorMundo(PantallaNivelUno screen) {
		World mundo = screen.getMundo();
		TiledMap mapa = screen.getMapa();
		
		BodyDef bdef = new BodyDef();
		PolygonShape forma = new PolygonShape();
		FixtureDef fdef = new FixtureDef();
		Body cuerpo; 
		
	
		
		//crea colicion suelo
		for(MapObject objeto : mapa.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rect = ((RectangleMapObject) objeto).getRectangle();
			
			bdef.type = BodyDef.BodyType.StaticBody;
			bdef.position.set((rect.getX() + rect.getWidth() / 2) /JuegoServer.PPM, (rect.getY() + rect.getHeight() / 2)/ JuegoServer.PPM) ;
			
			cuerpo = mundo.createBody(bdef);
			
			forma.setAsBox(rect.getWidth() / 2 / JuegoServer.PPM , rect.getHeight() / 2 / JuegoServer.PPM );
			fdef.shape = forma;
			cuerpo.createFixture(fdef);
		}
		
		//crea colicion plataforma
		for(MapObject objeto : mapa.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rect = ((RectangleMapObject) objeto).getRectangle();
			
			bdef.type = BodyDef.BodyType.StaticBody;
			bdef.position.set((rect.getX() + rect.getWidth() / 2) / JuegoServer.PPM, (rect.getY() + rect.getHeight() / 2)/JuegoServer.PPM);
			
			cuerpo = mundo.createBody(bdef);
			
			forma.setAsBox(rect.getWidth() / 2  /  JuegoServer.PPM, rect.getHeight() / 2 / JuegoServer.PPM);
			fdef.shape = forma;
			fdef.filter.categoryBits = JuegoServer.OBJETO_BIT;
			cuerpo.createFixture(fdef);
		}
		
		arquero = new Array<Arquero>();
		for(MapObject objeto : mapa.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rect = ((RectangleMapObject) objeto).getRectangle();
			arquero.add(new Arquero(screen , rect.getX() / JuegoServer.PPM, rect.getY() / JuegoServer.PPM));
			}
	}
	
	public Array<Arquero> getArquero() {
		return arquero;
	}
}
