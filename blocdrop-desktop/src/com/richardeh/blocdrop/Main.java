package com.richardeh.blocdrop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "BlocDrop";
		cfg.width = 640;
		cfg.height = 800;
		
		new LwjglApplication(new blocdrop(), cfg);
	}
}
