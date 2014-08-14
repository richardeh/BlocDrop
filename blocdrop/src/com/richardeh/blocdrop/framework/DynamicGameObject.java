package com.richardeh.blocdrop.framework;

import com.badlogic.gdx.math.Vector2;

public class DynamicGameObject extends GameObject {
	public final Vector2 velocity;
	public final Vector2 acceleration;
	
	public DynamicGameObject(float x, float y, float width, float height){
		super(x,y,width,height);
		this.velocity = new Vector2();
		this.acceleration = new Vector2();
	}
}
