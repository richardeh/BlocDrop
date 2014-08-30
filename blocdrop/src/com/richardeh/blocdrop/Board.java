package com.richardeh.blocdrop;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Board {

	private ArrayList<ArrayList<Integer>> board, previousBoard;
	private static final int BOARD_WIDTH = 10;
	private static final int BOARD_HEIGHT= 18;
	
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

}
