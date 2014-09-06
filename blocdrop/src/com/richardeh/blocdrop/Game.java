package com.richardeh.blocdrop;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;

public class Game implements GestureListener, InputProcessor {

	private static final int ROW_SCORE = 10;
	public Board board;
	public Block currentBlock;
	public Block nextBlock;

	private boolean hasMoved;
    private boolean isOver;
    private boolean isPaused;
    private int score;
    
    InputMultiplexer im;

    Random rand = new Random();

	public Game(Board board){
		this.board = board;
        isOver = isPaused = false;
        currentBlock = randomBlock();
		nextBlock = randomBlock();
		score = 0;

		im = new InputMultiplexer();
		GestureDetector gd = new GestureDetector(this);
		im.addProcessor(gd);
		im.addProcessor(this);
		Gdx.input.setInputProcessor(im);
	}
	
	public void updateGame(){
		List<Integer> rowsToScore = new ArrayList<Integer>();
		if(isPlaying()){
			// Game Loop

			if(!tryMoveDown()){
                // Current block cannot move down
                if(!hasMoved){
                    // Current block cannot move, and did not move
                    gameOver();
                } else {
                    // Block did move, but has now stopped

                    // update the score
                	rowsToScore.clear();
                    for(Vector2 v:currentBlock.getCoords()){
                        if(!rowsToScore.contains((int)v.x))
                        	rowsToScore.add((int)v.x);
                    }
                    updateScore(rowsToScore);
                    

                    if(!canInsertNewPiece()) {
                        // the top is full, game is over
                        gameOver();
                    }
                    // move the "next" block to the current block
                    // and generate a new "next" block
                    currentBlock = nextBlock;
                    nextBlock = randomBlock();
                    hasMoved = false;
                }
            }
		}
	}
	
	private Block randomBlock(){
        Block.Shape shape = null;

		switch(rand.nextInt(7)){
		case 0:
			shape = Block.Shape.I;
			break;
		case 1:
            shape = Block.Shape.T;
			break;
		case 2:
            shape =  Block.Shape.J;
			break;
		case 3:
            shape = Block.Shape.L;
			break;
		case 4:
            shape = Block.Shape.O;
			break;
		case 5:
            shape = Block.Shape.Z;
			break;
		case 6:
            shape = Block.Shape.S;
			break;
			default:
				return randomBlock();
		}
        return new Block(0,0,64,64,shape,rand.nextInt(6)+1);
	}
	
	private boolean isPlaying(){
		return !isPaused && !isOver;
	}

    private boolean canInsertNewPiece(){
        // check to see if the top of the board is full
        for(Vector2 pos: nextBlock.getCoords()){
            if(board.getPosition((int)pos.x,(int)pos.y)!=0){
                return false;
            }
        }

        // if not, insert the next block
        board.insertBlock(nextBlock);
        return true;
    }
    
    private boolean checkMove(){
    	// Make sure there's nothing in the way
    	
    	// create lists of the new and old coordinates
        ArrayList<Vector2> newCoords =currentBlock.getCoords();
    	ArrayList<Vector2> oldCoords = currentBlock.getPrevCoords();
    	ArrayList<Vector2> coordsToCheck = new ArrayList<Vector2>();
    	
        for(Vector2 pos:newCoords){
        	// If the new position is also in the old list, it doesn't need checked
        	if(oldCoords.contains(pos))
        		continue;
        	else
        		coordsToCheck.add(pos);
        }
        
        for(Vector2 pos: coordsToCheck){
        	// if any of the positions are occupied, we don't move
        	if(board.getPosition((int)pos.x, (int)pos.y)!=0){
        		return false;
        	}
        }
    	
    	return true;
    }

    private boolean tryMoveDown(){
    	// Attempt to move a piece down the board

    	board.removeBlock(currentBlock);
    	if(!currentBlock.moveDown()) {
    		currentBlock.undo();
    		board.insertBlock(currentBlock);
    		return false;
    	}
    	if(checkMove()){
    		hasMoved = true;
    		board.insertBlock(currentBlock);
            return true;
    	} else {
    		currentBlock.undo();
    		board.insertBlock(currentBlock);
    		return false;
    	}
        
    }

    private void gameOver(){
        // TODO: method stub
    	
        isOver = true;
        System.out.println(score);
    }

    private int updateScore(List<Integer> rows){
        // Checks to see if a row has filled, score it, and remove it
    	int scoreMultiplier = 0;
    	int newScore = 0;
    	List<Integer> rowsToDelete = new ArrayList<Integer>();
    	for(int row:rows){
    		if(board.getBoard().get(row).contains(0)){
    			continue;
    		} else {
    			scoreMultiplier++; 
    			rowsToDelete.add(row);
    		}
    	}
    	
    	for(int row:rowsToDelete){
    		board.deleteRow(row-rowsToDelete.indexOf(row));
    	}
    	rowsToDelete.clear();
    	newScore = ROW_SCORE * scoreMultiplier;
    	score += newScore;
    	
    	return newScore;
    }

    @Override
    public boolean touchDown(float v, float v2, int i, int i2) {
        return false;
    }

    @Override
    public boolean tap(float v, float v2, int i, int i2) {
        return false;
    }

    @Override
    public boolean longPress(float v, float v2) {
        return false;
    }

    @Override
    public boolean fling(float vX, float vY, int i) {

        if(isOver){
            return false;
        }
    	if(vX>500){
    		// Swipe right
    		board.removeBlock(currentBlock);
    		currentBlock.moveRight();
            if(checkMove()){
                hasMoved = true;
                board.insertBlock(currentBlock);
                return true;
            } else {
                currentBlock.undo();
                board.insertBlock(currentBlock);
                return false;
            }
    	}
    	if(vX<-500){
    		// Swipe Left
    		for(Vector2 v:currentBlock.getCoords()){
        		if (v.y==0) return false;
        		if (board.getPosition((int)v.x, (int)v.y-1)!=0) return false;
        	}
    		board.removeBlock(currentBlock);
    		currentBlock.moveLeft();
            if(checkMove()){
                hasMoved = true;
                board.insertBlock(currentBlock);
                return true;
            } else {
                currentBlock.undo();
                board.insertBlock(currentBlock);
                return false;
            }
    	}
    	if(vY>500){
    		// TODO: check to make sure the rotate won't cause an error
    		if(currentBlock.getShape() == Block.Shape.O) return false;
    		board.removeBlock(currentBlock);
    		currentBlock.rotate();
            if(checkMove()){
                hasMoved = true;
                board.insertBlock(currentBlock);
                return true;
            } else {
                currentBlock.undo();
                board.insertBlock(currentBlock);
                return false;
            }
    	}
        if(vY<-500){
            // swipe down
            tryMoveDown();
        }
        return false;
    }

    @Override
    public boolean pan(float v, float v2, float v3, float v4) {
        return false;
    }

    @Override
    public boolean panStop(float v, float v2, int i, int i2) {
        return false;
    }

    @Override
    public boolean zoom(float v, float v2) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 vector2, Vector2 vector22, Vector2 vector23, Vector2 vector24) {
        return false;
    }

    @Override
    public boolean keyDown(int i) {
        return false;
    }

    @Override
    public boolean keyUp(int i) {

        if(isOver) return false;

    	if(i == Keys.SPACE) isPaused = !isPaused;
    	if(i == Keys.UP) {
    		// TODO: check to make sure the rotate won't cause an error

    		if(currentBlock.getShape() == Block.Shape.O) return false;
    		board.removeBlock(currentBlock);
    		currentBlock.rotate();
    		if(checkMove()){
        		hasMoved = true;
        		board.insertBlock(currentBlock);
                return true;
        	} else {
        		currentBlock.undo();
        		board.insertBlock(currentBlock);
        		return false;
        	}
    	}
    	if(i == Keys.RIGHT) {
    		for(Vector2 v:currentBlock.getCoords()){
        		if (v.y==board.getWidth()-1) return false;
        		
        	}
    		
    		board.removeBlock(currentBlock);
    		
    		currentBlock.moveRight();
    		if(checkMove()){
        		hasMoved = true;
        		board.insertBlock(currentBlock);
                return true;
        	} else {
        		currentBlock.undo();
        		board.insertBlock(currentBlock);
        		return false;
        	}
    	}
    	if(i == Keys.LEFT) {
    		for(Vector2 v:currentBlock.getCoords()){
        		if (v.y==0) return false;
        	}
    		board.removeBlock(currentBlock);
    		currentBlock.moveLeft();
    		if(checkMove()){
        		hasMoved = true;
        		board.insertBlock(currentBlock);
                return true;
        	} else {
        		currentBlock.undo();
        		board.insertBlock(currentBlock);
        		return false;
        	}
    	}
    	if(i == Keys.DOWN){
    		// TODO: fix disappearing glitch
    		tryMoveDown();
    	}
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int i, int i2, int i3, int i4) {
        return false;
    }

    @Override
    public boolean touchUp(int i, int i2, int i3, int i4) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i2, int i3) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i2) {
        return false;
    }

    @Override
    public boolean scrolled(int i) {
        return false;
    }
    
    public Board getBoard(){
    	return board;
    }
    
    public int getScore(){
    	return score;
    }

    public Block getNextBlock(){
        return nextBlock;
    }
}
