package com.richardeh.blocdrop;

import com.badlogic.gdx.math.Vector2;
import com.richardeh.blocdrop.framework.DynamicGameObject;

import java.util.ArrayList;

public class Block extends DynamicGameObject{

    private ArrayList<Vector2> coords, prevCoords;
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
		
	public Block(float x, float y, float width, float height, Shape shape, int value){
		super(x,y,width,height);
        this.shape = shape;
        setStartCoords();
        prevCoords = new ArrayList<Vector2>();
        orientation = Orientation.One;
        this.value = value;
	}


    private void setStartCoords(){
    	// TODO: this should really be a function of the game, not the block itself
        coords = new ArrayList<Vector2>();
        switch (shape){
            case I:
                for(int i=19;i<23;i++){
                    coords.add(new Vector2(i,5));
                }
                break;
            case J:
                coords.add(new Vector2(19,4));
                for(int i=19;i<22;i++){
                    coords.add(new Vector2(i,5));
                }
                break;
            case L:
                coords.add(new Vector2(19,5));
                for(int i=19;i<22;i++){
                    coords.add(new Vector2(i,4));
                }
                break;
            case O:
                coords.add(new Vector2(19,4));
                coords.add(new Vector2(19,5));
                coords.add(new Vector2(20,4));
                coords.add(new Vector2(20,5));
                break;
            case T:
                coords.add(new Vector2(19,5));

                for(int i=4;i<7;i++){
                    coords.add(new Vector2(20,i));
                }
                break;
            case S:
                coords.add(new Vector2(19,4));
                coords.add(new Vector2(19,5));
                coords.add(new Vector2(20,5));
                coords.add(new Vector2(20,6));
                break;
            case Z:
                coords.add(new Vector2(19,5));
                coords.add(new Vector2(19,4));
                coords.add(new Vector2(20,4));
                coords.add(new Vector2(20,3));
                break;
        }

        return;
    }

    public boolean moveDown(){
    	copyCoords();

    	if(getLowest()==0){
    		return false;
    	}
    	for(Vector2 v:coords){
    		coords.set(coords.indexOf(v), new Vector2(v.x-1, v.y));
      	}
        return true;
    }

    public void moveRight(){

    	copyCoords();
    	for(Vector2 v:coords){
    		coords.set(coords.indexOf(v), new Vector2(v.x, v.y+1));
    	}
    }
    
    public void moveLeft(){
    	copyCoords();
    	
    	for(Vector2 v:coords){
 			coords.set(coords.indexOf(v), new Vector2(v.x, v.y-1));
    	}
    }
       
    public void rotate(){
        float x,y;
        copyCoords();

    	x = this.coords.get(0).x;
    	y = this.coords.get(0).y;
        coords.clear();
        switch(shape){
        	case O:
        		// rotate O
        		break;
        	
            case L:
                // rotate L
                switch(orientation){
                    case One:
                        // 3 x x    x x x
                        // 2 x x -> 1 2 3
                        // 1 0 x    0 x x
                        coords.add(new Vector2(x,y-1));
                        coords.add(new Vector2(x + 1, y-1));
                        coords.add(new Vector2(x + 1, y));
                        coords.add(new Vector2(x + 1, y + 1));
                        this.orientation = Orientation.Two;
                   	break;
                    case Two:
                        // x x x     0 1 x
                        // 1 2 3 ->  x 2 x
                        // 0 x x     x 3 x
                        coords.add(new Vector2(x + 2, y));
                        coords.add(new Vector2(x + 2, y + 1));
                        coords.add(new Vector2(x + 1, y + 1));
                        coords.add(new Vector2(x, y+1));
                        this.orientation = Orientation.Three;
                    	break;
                    case Three:
                        // 0 1 x    x x x
                        // x 2 x -> x x 0
                        // x 3 x    3 2 1
                        coords.add(new Vector2(x-1, y+2));
                        coords.add(new Vector2(x - 2, y + 2));
                        coords.add(new Vector2(x-2,y+1));
                        coords.add(new Vector2(x - 2, y));
                        this.orientation = Orientation.Four;
                    	break;
                    case Four:
                        // x x x    3 x x
                        // x x 0 -> 2 x x
                        // 3 2 1    1 0 x
                        coords.add(new Vector2(x - 1, y - 1));
                        coords.add(new Vector2(x-1,y-2));
                        coords.add(new Vector2(x, y - 2));
                        coords.add(new Vector2(x + 1, y - 2));
                        this.orientation = Orientation.One;
                        break;
                }
                break;
            case T:
                // rotate T
                switch(orientation){
                    case One:
                    	//  x x x     x 1 x
                    	//  1 2 3 ->  0 2 x
                    	//  x 0 x     x 3 x
                    	
                    	coords.add(new Vector2(x+1,y-1));
                    	coords.add(new Vector2(x+2,y));
                    	coords.add(new Vector2(x + 1, y));
                        coords.add(new Vector2(x,y));
                        this.orientation = Orientation.Two;
                    	break;
                    	
                    case Two:
                    	// x 1 x     x 0 x
                    	// 0 2 x ->  3 2 1
                    	// x 3 x     x x x

                        coords.add(new Vector2(x + 1, y + 1));
                        coords.add(new Vector2(x, y + 2));
                        coords.add(new Vector2(x, y+1));
                        coords.add(new Vector2(x, y));
                        this.orientation = Orientation.Three;
                    	break;
                    	
                    case Three:
                    	// x 0 x    3 x x
                    	// 3 2 1 -> 2 0 x
                    	// x x x    1 x x

                        coords.add(new Vector2(x - 1, y));
                        coords.add(new Vector2(x - 2, y - 1));
                        coords.add(new Vector2(x - 1, y-1));
                        coords.add(new Vector2(x, y - 1));
                        this.orientation = Orientation.Four;
                    	break;
                    	
                    case Four:
                    	// 3 x x    x x x
                    	// 2 0 x -> 1 2 3
                    	// 1 x x    x 0 x

                        coords.add(new Vector2(x - 1, y));
                        coords.add(new Vector2(x, y - 1));
                        coords.add(new Vector2(x , y));
                        coords.add(new Vector2(x, y + 1));
                        this.orientation = Orientation.One;
                        break;
                }
                break;
            case J:
                // rotate J
                switch(orientation){
                    case One:
                        //  x 3 x    x x x
                        //  x 2 x -> 0 x x
                        //  0 1 x    1 2 3
                        coords.add(new Vector2(x + 1, y));
                        coords.add(new Vector2(x, y));
                        coords.add(new Vector2(x, y + 1));
                        coords.add(new Vector2(x, y + 2));

                        this.orientation = Orientation.Two;
                    	break;
                    case Two:
                        // x x x    1 0 x
                        // 0 x x -> 2 x x
                        // 1 2 3    3 x x
                        coords.add(new Vector2(x+1,y+1));
                        coords.add(new Vector2(x+1,y));
                        coords.add(new Vector2(x, y));
                        coords.add(new Vector2(x - 1, y));
                        this.orientation = Orientation.Three;
                    	break;
                    case Three:
                        // 1 0 x     x x x
                        // 2 x x  -> 3 2 1
                        // 3 x x     x x 0
                        coords.add(new Vector2(x-2, y+1));
                        coords.add(new Vector2(x - 1, y + 1));
                        coords.add(new Vector2(x - 1, y));
                        coords.add(new Vector2(x - 1, y - 1));
                        this.orientation = Orientation.Four;
                    	break;
                    case Four:
                        // x x x    x 3 x
                        // 3 2 1 -> x 2 x
                        // x x 0    0 1 x
                        coords.add(new Vector2(x,y-2));
                        coords.add(new Vector2(x,y-1));
                        coords.add(new Vector2(x + 1, y - 1));
                        coords.add(new Vector2(x + 2, y - 1));
                        this.orientation = Orientation.One;
                        break;
                }
                break;
            case Z:
                // rotate Z
                switch(orientation){
                    case One:
                    	//  x x x     x 3 x
                    	//  3 2 x  -> 1 2 x
                    	//  x 1 0     0 x x
                    	coords.add(new Vector2(x, y - 2));
                    	coords.add(new Vector2(x + 1, y - 2));
                    	coords.add(new Vector2(x + 1, y - 1));
                        coords.add(new Vector2(x + 2, y - 1));
                        this.orientation = Orientation.Two;
                    	break;
                    case Two:
                    	//  x 3 x      x x x
                    	//  1 2 x  ->  3 2 x
                    	//  0 x x      x 1 0
                    	
                    	coords.add(new Vector2(x, y + 2));
                    	coords.add(new Vector2(x, y + 1));
                    	coords.add(new Vector2(x+1, y+1));
                        coords.add(new Vector2(x + 1, y));
                        this.orientation = Orientation.One;
                        break;
				case Four:
					break;
				case Three:
					break;
				default:
					break;
                }
                break;
            case S:
                // rotate S
                switch(orientation){
                    case One:
                    	//  x x x     3 x x
                    	//  x 2 3 ->  2 1 x
                    	//  0 1 x     x 0 x
                    	
                    	coords.add(new Vector2(x, y + 1));
                    	coords.add(new Vector2(x + 1, y + 1));
                    	coords.add(new Vector2(x + 1,y));
                        coords.add(new Vector2(x + 2,y));
                        this.orientation = Orientation.Two;
                    	break;
                    case Two:
                    	// 3 x x   x x x
                    	// 2 1 x-> x 2 3
                    	// x 0 x   0 1 x
                    	
                    	coords.add(new Vector2(x, y-1));
                    	coords.add(new Vector2(x, y));
                    	coords.add(new Vector2(x+1,y));
                        coords.add(new Vector2(x+1,y+1));
                        this.orientation = Orientation.One;
                        break;
				case Four:
					break;
				case Three:
					break;
				default:
					break;
                }
                break;
            case I:
                // rotate I
                switch(orientation){
                    case One:
                    	// 3 x x x     x x x x
                    	// 2 x x x     x x x x
                    	// 1 x x x     x x x x
                        // 0 x x x  -> 0 1 2 3

                        for(int i=(int)y;i<y+4;i++){
                            coords.add(new Vector2(x, i));
                        }

                        this.orientation = Orientation.Two;
                        break;
                    case Two:
                    	//             3
                    	//             2
                    	//             1
                        // 0 1 2 3  -> 0

                        for(int i=(int)x;i<x+4;i++){
                            coords.add(new Vector2(i, y));
                        }
                        this.orientation = Orientation.One;
                        break;
				case Four:
					break;
				case Three:
					break;
				default:
					break;
                }
                break;
        }
    }

    public void undo(){
        coords = getPrevCoords();
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public ArrayList<Vector2> getCoords(){
        return coords;
    }

    public ArrayList<Vector2> getPrevCoords(){
        return prevCoords;
    }
    
    public Shape getShape(){
    	return shape;
    }
    public float getRightEdge(){
    	// returns the right-most Y value
    	float rightY = 0f;
    	for(Vector2 v:coords){
    		if (v.y>rightY){
    			rightY = v.y;
    		}
    	}
    	return rightY;
    }
    
    public float getLeftEdge(){
    	// returns the left-most X value
    	float leftY = 99f;
    	for(Vector2 v:coords){
    		if(v.y<leftY){
    			leftY = v.y;
    		}
    	}
    	return leftY;
    }
    
    public float getLowest(){
    	// returns the lowest Y value
    	float bottomX = 99f;
    	for(Vector2 v:coords){
    		if(v.x<bottomX){
    			bottomX = v.x;
    		}
    	}
    	return bottomX;
    }
    
    private void copyCoords(){
    	prevCoords.clear();
    	
    	for(Vector2 v:coords){
    		prevCoords.add(v);
    	}
    }
}
