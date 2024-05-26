package com.mygdx.game.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.JuegoServer;
import com.mygdx.game.elementos.B2CreadorMundo;
import com.mygdx.game.elementos.Hud;
import com.mygdx.game.elementos.MundoContactListener;
import com.mygdx.game.io.EntradasNivelUno;
import com.mygdx.game.red.HiloServidor;
import com.mygdx.game.sprites.Enemigo;
import com.mygdx.game.sprites.Ninja;
import com.mygdx.game.utiles.Recursos;
import com.mygdx.game.utiles.Render;

public class PantallaNivelUno implements Screen {
	

	private JuegoServer game;
	
	SpriteBatch b;
	
	private TiledMap mapa;
	private OrthogonalTiledMapRenderer renderer;
	
	private TextureAtlas atlas;
	private OrthographicCamera cam;
	private Viewport portJuego;
	
	

	public float tiempo = 0; 

	private Hud hud;
	
	
	private World mundo;
	private Box2DDebugRenderer b2dr;
	private B2CreadorMundo creador;
	

	private Ninja player1;
	private int nroPlayer = 0;
	private Ninja player2;
	

	protected Fixture fixture;

	private HiloServidor hs;
	
	public boolean isArriba1 = false,  isDerecha1 = false, isIzquierda1 = false;
	public boolean isArriba2 = false,  isDerecha2 = false, isIzquierda2 = false;
	
	public PantallaNivelUno(JuegoServer juego) {
	
		atlas = new TextureAtlas("personajes/Persj.pack");
		this.game = juego;
		b = Render.batch;
		
	
		
		cam = new OrthographicCamera();
		
		portJuego = new FitViewport(JuegoServer.V_WITDH / JuegoServer.PPM, JuegoServer.V_HEIGHT/ JuegoServer.PPM, cam);
		
		hud = new Hud(b);
		
		mapa = new TmxMapLoader().load(Recursos.FONDONIVELDOS);
		renderer = new OrthogonalTiledMapRenderer(mapa, 1 / JuegoServer.PPM);
		
		
		cam.position.set(portJuego.getWorldWidth() / 2 , portJuego.getWorldHeight() / 2, 0);
		
		
		mundo = new World(new Vector2(0, -10), true);
		
		b2dr = new Box2DDebugRenderer();
		

		creador = new B2CreadorMundo(this);
		
		player1 = new Ninja(1,this);
		player2 = new Ninja(2, this);
		
		mundo.setContactListener(new MundoContactListener());
		
		hs = new HiloServidor();
		hs.start();

	}
	
	@Override
	public void show() {
		
	}

	public void handleInput(float dt) {
		

		//mueve al personaje segun la tecla tocada
			if(isArriba1) {
				player1.b2body.applyLinearImpulse(new Vector2(0 , 2f ), player1.b2body.getWorldCenter(), true);
			} else if(isArriba2) {
				player2.b2body.applyLinearImpulse(new Vector2(0 , 2f ), player1.b2body.getWorldCenter(), true);
			}
			

			if(isDerecha1 ) {
				player1.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player1.b2body.getWorldCenter(), true);
			} else if(isDerecha2) {
				player2.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player1.b2body.getWorldCenter(), true);
			}
			
			if(isIzquierda1 ) {
				player1.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player1.b2body.getWorldCenter(), true);
			} else if(isIzquierda2) {
				player1.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player1.b2body.getWorldCenter(), true);
			}
			
			
		
	}
	
	
	
	public TextureAtlas getAtlas() {
		return atlas;
	}

	// realiza el cambio de lo que sucede en pantalla
	public void update(float dt) {
		
		
		handleInput(dt);
		
		mundo.step(1/60f, 6 ,4);
		
		player1.update(dt);
		player2.update(dt);
		
		for (Enemigo enemigo : creador.getArquero()) {
			enemigo.update(dt);
		}

		hud.update(dt);
		
		
		
		cam.position.x = player1.b2body.getPosition().x;
		cam.position.x = player2.b2body.getPosition().x;
		
		cam.update();
		
		renderer.setView(cam);

	
	}
	
	//renderiza la actualizacion del update
	@Override
	public void render(float delta) {
		
			update(delta);
			
			Render.limpiarPantalla(0, 0, 0);
			
			renderer.render();
			
			b.setProjectionMatrix(hud.escena.getCamera().combined);
				hud.escena.draw();
				cam.update();
			b.setProjectionMatrix(cam.combined);
			
		
			renderer.setView(cam);

			

			
			b2dr.render(mundo, cam.combined);
			
			
			b.setProjectionMatrix(cam.combined);
			b.begin();
				player1.draw(b);
				player2.draw(b);
				for (Enemigo enemigo : creador.getArquero()) {
					enemigo.draw(b);
					if(enemigo.getX() < player1.getX() + 224 / JuegoServer.PPM) {
						enemigo.b2body.setActive(true);
					}
				}
			b.end();
		}
		


	@Override
	public void resize(int width, int height) {
		portJuego.update(width, height);
	}


	public TiledMap getMapa() {
		return mapa;
	}
	
	public World getMundo() {
		return mundo;
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

		mapa.dispose();
		renderer.dispose();
		mundo.dispose();
		b2dr.dispose();
		hud.dispose();
	}

    public Hud getHud(){ 
    		return hud; 
    	}
}

