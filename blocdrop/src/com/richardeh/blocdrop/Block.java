package com.richardeh.blocdrop;

import com.richardeh.blocdrop.framework.DynamicGameObject;

public class Block extends DynamicGameObject{
	
	public static final int ZERO = 0;
	public static final int NINETY = 90;
	public static final int ONE_EIGHTY = 180;
	public static final int TWO_SEVENTY = 270;
	
	public enum Style{
		L,
		T,
		J,
		I,
		Z,
		S,
		O
	}
	
	private int value;
	private int rotation = Block.ZERO;
		
	public Block(float x, float y, float width, float height){
		super(x,y,width,height);
	}
	
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}

	public int getRotation() {
		return rotation;
	}

	public void setRotation(int rotation) {
		this.rotation = rotation;
	}
	
}
