package com.richardeh.blocdrop;

import com.badlogic.gdx.math.Vector2;
import com.richardeh.blocdrop.framework.DynamicGameObject;

import java.util.ArrayList;

public class Block extends DynamicGameObject{

    private ArrayList<Vector2> coords;
	public enum Shape{
		L,
		T,
		J,
		I,
		Z,
		S,
		O
	}

    public enum Orientation{
        One,
        Two,
        Three,
        Four
    }
	
	private int value;
    private Shape shape;
    private Orientation orientation;
		
	public Block(float x, float y, float width, float height, Shape shape){
		super(x,y,width,height);
        this.shape = shape;
        coords = setStartCoords();
        orientation = Orientation.One;
	}
	
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}

    private ArrayList<Vector2> setStartCoords(){

        ArrayList<Vector2> coords = new ArrayList<Vector2>();
        Vector2 point = new Vector2(0,0);
        switch (shape){
            case I:
                for(int i=14;i<18;i++){
                    coords.add(point.set(i,5));
                }
                break;
            case J:
                coords.add(point.set(14,4));
                for(int i=14;i<17;i++){
                    coords.add(point.set(i,5));
                }
                break;
            case L:
                coords.add(point.set(14,5));
                for(int i=14;i<17;i++){
                    coords.add(point.set(i,4));
                }
                break;
            case O:
                coords.add(point.set(14,4));
                coords.add(point.set(14,5));
                coords.add(point.set(15,4));
                coords.add(point.set(15,5));
                break;
            case T:
                coords.add(point.set(14,5));

                for(int i=4;i<7;i++){
                    coords.add(point.set(15,i));
                }
                break;
            case S:
                coords.add(point.set(14,4));
                coords.add(point.set(14,5));
                coords.add(point.set(15,5));
                coords.add(point.set(15,6));
                break;
            case Z:
                coords.add(point.set(14,4));
                coords.add(point.set(14,5));
                coords.add(point.set(15,3));
                coords.add(point.set(15,4));
                break;
        }

        return coords;
    }

    private void rotate(){
        float x,y;
        switch(shape){
            case L:
                // algorithm to rotate L
                switch(orientation){
                    case One:
                    case Two:
                    case Three:
                    case Four:
                        break;
                }
                break;
            case J:
                // algorithm to rotate J
                switch(orientation){
                    case One:
                    case Two:
                    case Three:
                    case Four:
                        break;
                }
                break;
            case T:
                // algorithm to rotate T
                switch(orientation){
                    case One:
                    case Two:
                    case Three:
                    case Four:
                        break;
                }
                break;
            case Z:
                // algorithm to rotate Z
                switch(orientation){
                    case One:
                    case Two:
                        break;
                }
                break;
            case S:
                // algorithm to rotate S
                switch(orientation){
                    case One:
                    case Two:
                        break;
                }
                break;
            case I:
                // algorithm to rotate I
                switch(orientation){
                    case One:
                        // Vertical to horizontal
                        x = coords.get(0).x;
                        y = coords.get(0).y;

                        for(Vector2 v:coords){
                            v.set(x,y+coords.indexOf(v));
                        }
                        break;
                    case Two:
                        // Horizontal to vertical
                        x = coords.get(0).x;
                        y = coords.get(0).y;

                        for(Vector2 v:coords){
                            v.set(x+coords.indexOf(v),y);
                        }
                        break;
                }
                break;
        }
    }
}
