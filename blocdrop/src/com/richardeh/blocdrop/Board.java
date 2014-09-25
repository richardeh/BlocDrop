package com.richardeh.blocdrop;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Vector;

public class Board {

    public enum Direction{
        Left,
        Right,
        Down
    }

	private ArrayList<ArrayList<Integer>> board, previousBoard;
	private static final int BOARD_WIDTH = 10;
	private static final int BOARD_HEIGHT= 23;
	
	public Board(){

		board = previousBoard = new ArrayList<ArrayList<Integer>>();
	}
	
	public void start(){

		for(int i=0;i<BOARD_HEIGHT;i++){
			addRow();
		}
	}
	
	public ArrayList<ArrayList<Integer>> getBoard(){
		return board;
	}
	
	public void updateBoard(int row, int col, int number){
		previousBoard = board;
		board.get(row).set(col,number);
	}
	
	public int getPosition(int row, int col){
		return board.get(row).get(col);
	}

    public void insertBlock(Block block){
    	previousBoard = board;
        for(Vector2 position:block.getCoords()){
            board.get((int)position.x).set((int)position.y,block.getValue());
        }
    }
    
    public void removeBlock(Block block){
    	previousBoard = board;
    	for(Vector2 position:block.getCoords()){
    		board.get((int)position.x).set((int)position.y,0);
    	}
    }

    public void undo(){
    	board = previousBoard;
    }
    
    public int getWidth(){
    	return BOARD_WIDTH;
    }

    public void deleteRow(int row){
    	previousBoard = board;
    	for(int i=row;i<BOARD_HEIGHT-1;i++){
    		board.set(i,board.get(i+1));
    	}
    	board.remove(BOARD_HEIGHT-1);
    	addRow();
    }
    
    private void addRow(){
    	ArrayList<Integer> row = new ArrayList<Integer>();
		
		for(int j=0;j<BOARD_WIDTH;j++){
			row.add(j,0);
		}
		board.add(row);
    }

    public boolean moveBlock(Block block, Direction direction){
        // attempt to move a block
        Vector2 modifier = getModifier(direction);

        // return false if the given direction is blocked
        if(!canMove(block,modifier)) return false;

        ArrayList<Vector2> current = block.getCoords();
        for(Vector2 v:current){
            board.get((int)v.x).set((int)v.y,0);
        }
        for(Vector2 v:current){
            v.add(modifier);
        	board.get((int)v.x).set((int)v.y,block.getValue());
        }
        return true;
    }

    public boolean rotateBlock(Block block){
        // TODO: test this out
        Block checker = new Block(block.getShape(),block.getValue(), block.getOrientation(), block.getCoords());
        
        try{
        	block.rotate();
        } catch(IndexOutOfBoundsException e) {
        	block = checker;
        	moveBlock(block, Direction.Left);
        }
        block.rotate();
        return true;
    }

    private boolean canMove(Block block, Vector2 modifier){

        ArrayList<Vector2> current = block.getCoords();
        ArrayList<Vector2> future = new ArrayList<Vector2>();

        for(Vector2 v:current){
        	v.add(modifier);
        	future.add(v.cpy());
        	v.sub(modifier);
        }
        for(Vector2 v:future){
            // If the new position is out of bounds, return false
            if(v.y>BOARD_WIDTH-1||v.y<0||v.x<0) return false;

            if(!current.contains(v)){
                if(board.get((int)v.x).get((int)v.y)!=0){
                    return false;
                }
            }
        }
        return true;
    }

    private Vector2 getModifier(Direction direction){
        Vector2 modifier = new Vector2();
        switch (direction){
            case Down:
                modifier.x = -1;
                modifier.y = 0;
                break;
            case Right:
                modifier.x = 0;
                modifier.y = 1;
                break;
            case Left:
                modifier.x = 0;
                modifier.y = -1;
                break;
        }
        return modifier;
    }
}
