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
                coords.add(point.set(14,5));
                coords.add(point.set(14,4));
                coords.add(point.set(15,4));
                coords.add(point.set(15,3));
                break;
        }

        return coords;
    }

    public void moveDown(){
    	for(Vector2 v:coords){
    		v.x-=1;
    	}
    }

    public void moveRight(){
    	for(Vector2 v:coords){
    		v.y+=1;
    	}
    }
    
    public void moveLeft(){
    	for(Vector2 v:coords){
    		v.y-=1;
    	}
    }
       
    public void rotate(){
        float x,y;

    	x = coords.get(0).x;
    	y = coords.get(0).y;
        switch(shape){
            case L:
                // rotate L
                switch(orientation){
                    case One:
                    	break;
                    case Two:
                    	break;
                    case Three:
                    	break;
                    case Four:
                        break;
                }
                break;
            case T:
                // rotate T
                switch(orientation){
                    case One:
                    	//              1
                    	//  1 2 3 ->  0 2 
                    	//    0         3
                    	
                    	coords.set(3,coords.get(0));
                    	coords.set(0, coords.get(1));
                    	coords.set(1, new Vector2(x, y+2));
                    	break;
                    	
                    case Two:
                    	//   1        0
                    	// 0 2  ->  3 2 1
                    	//   3
                    	
                    	coords.set(0, coords.get(1));
                    	coords.set(3, new Vector2(x, y));
                    	coords.set(1, new Vector2(x+2,y));
                    	break;
                    	
                    case Three:
                    	//   0      3
                    	// 3 2 1 -> 2 0
                    	//          1
                    	
                    	coords.set(3,coords.get(0));
                    	coords.set(0, coords.get(1));
                    	coords.set(1, new Vector2(x, y-2));
                    	break;
                    	
                    case Four:
                    	// 3
                    	// 2 0 -> 1 2 3
                    	// 1        0
                    	
                    	coords.set(3, coords.get(0));
                    	coords.set(0, coords.get(1));
                    	coords.set(1, new Vector2(x-2,y));
                        break;
                }
                break;
            case J:
                // rotate J
                switch(orientation){
                    case One:
                    	break;
                    case Two:
                    	break;
                    case Three:
                    	break;
                    case Four:
                        break;
                }
                break;
            case Z:
                // rotate Z
                switch(orientation){
                    case One:
                    	//              3  
                    	//  3 2   ->  1 2
                    	//    1 0     0
                    	
                    	coords.set(1, new Vector2(x,y+1));
                    	coords.set(2, new Vector2(x+1, y+1));
                    	coords.set(3, new Vector2(x+1, y+2));
                    	break;
                    case Two:
                    	//    3          
                    	//  1 2   ->  3 2
                    	//  0           1 0
                    	
                    	coords.set(1, new Vector2(x-1,y));
                    	coords.set(2, new Vector2(x-1, y+1));
                    	coords.set(3, new Vector2(x-2, y+1));
                        break;
                }
                break;
            case S:
                // rotate S
                switch(orientation){
                    case One:
                    	//            3  
                    	//    2 3 ->  2 1
                    	//  0 1         0
                    	
                    	coords.set(1, new Vector2(x,y+1));
                    	coords.set(2, new Vector2(x-1,y+1));
                    	coords.set(3, new Vector2(x-1,y+2));
                    	break;
                    case Two:
                    	// 3
                    	// 2 1 ->    2  3
                    	//   0    0  1
                    	
                    	coords.set(1, new Vector2(x+1, y));
                    	coords.set(2, new Vector2(x+1,y+1));
                    	coords.set(3, new Vector2(x+1,y+2));
                        break;
                }
                break;
            case I:
                // rotate I
                switch(orientation){
                    case One:
                    	// 3
                    	// 2
                    	// 1
                        // 0  -> 0 1 2 3

                        for(Vector2 v:coords){
                            v.set(x,y+coords.indexOf(v));
                        }
                        break;
                    case Two:
                    	//             3
                    	//             2
                    	//             1
                        // 0 1 2 3  -> 0

                        for(Vector2 v:coords){
                            v.set(x+coords.indexOf(v),y);
                        }
                        break;
                }
                break;
        }
    }
}
