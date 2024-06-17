package com.mygdx.game;


import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygdx.game.utiles.Config;


public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setWindowedMode(Config.ANCHO, Config.ALTO);
		config.setResizable(true);  
		config.setTitle("game");
		new Lwjgl3Application(new JuegoServer(), config);
		System.exit(0);
	}
}