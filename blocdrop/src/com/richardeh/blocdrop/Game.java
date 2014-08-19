package com.richardeh.blocdrop;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game implements GestureListener, InputProcessor {

	public Board board;
	public Block currentBlock;
	public Block nextBlock;

	private boolean hasMoved;
    private boolean isOver;
    private boolean isPaused;
    private List<Integer> rowsToScore;

    Random rand = new Random();

	public Game(Board board){
		this.board = board;
		rowsToScore = new ArrayList<Integer>();
        isOver = isPaused = false;
        currentBlock = randomBlock();
		nextBlock = randomBlock();
		updateGame();
	}
	
	public void updateGame(){
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
                    for(Vector2 v:currentBlock.getCoords()){
                        rowsToScore.add((int)v.x);
                    }
                    updateScoreAndBoard();

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
        return new Block(0,0,64,64,shape,rand.nextInt(5)+1);
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

    private boolean tryMoveDown(){
    	// TODO: clean this up. should check first to make sure we're not about to go out of bounds
    	// then make sure we're not about to move into another block
    	// then if we're not going to hit a snag, move down and update the board
    	
    	for(Vector2 pos:currentBlock.getCoords()){
    		if(pos.x <=1){
    			return false;
    		}
    	}
        currentBlock.moveDown();
        for(Vector2 pos:currentBlock.getCoords()){

            for(Vector2 prevPos:currentBlock.getPrevCoords()){
                if(pos.x == prevPos.x && pos.y == prevPos.y){
                    continue;
                } else if(board.getPosition((int)pos.x,(int)pos.y)!=0){
                    currentBlock.undo();
                    return false;
                }
            }

        }
        
        for(Vector2 prevPos:currentBlock.getPrevCoords()){
        	board.updateBoard((int)prevPos.x, (int)prevPos.y, 0);
        }
        
        for(Vector2 pos:currentBlock.getCoords()){
        	board.updateBoard((int)pos.x, (int)pos.y, currentBlock.getValue());
        }
        hasMoved = true;
        return true;
    }

    private void gameOver(){
        // TODO: method stub
        isOver = true;
    }

    private void updateScoreAndBoard(){
        // TODO: check the rows the block stopped in to see if they are full
        // if yes, update the score and move the rows above down
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
        // TODO: determine which direction is which when flinging
        // TODO: fill in moveRight and moveLeft as appropriate,
        //        and be sure to check for open spaces before rotating
        // TODO: consider: fling up = rotate, fling down = drop to bottom?
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
        // TODO: fill in event handling here to rotate, move, and drop pieces

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
}
