package com.richardeh.blocdrop;

import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Game {

	public Board board;
	public Block currentBlock;
	public Block nextBlock;
	
	public Game(Board board){
		this.board = board;
		
		
		nextBlock = new Block(0,0,32,32,randomShape());
		while(isPlaying()){
			// Game Loop

			// first check if the current block can still move down
			// if it can, move it down and update the board
			// if it cannot, move the 'next block' to the current position,
			// and generate a new 'next block'. check to see if there are any full rows,
			// and if there are, score them, delete them, move the rest of the rows down,
			// then insert the current piece at the top and start it moving down.

		}
		
		
	}
	
	private Block.Shape randomShape(){
		Random rand = new Random();
		
		switch(rand.nextInt(7)){
		case 0:
			return  Block.Shape.I;
			
		case 1:
			return  Block.Shape.T;
			
		case 2:
			return  Block.Shape.J;
			
		case 3:
			return  Block.Shape.L;
			
		case 4:
			return  Block.Shape.O;
			
		case 5:
			return  Block.Shape.Z;
			
		case 6:
			return Block.Shape.S;
			
			default:
				return randomShape();
		}
	}
	
	private boolean isPlaying(){
		return true;
	}

    private boolean canInsertNewPiece(){
        for(Vector2 pos: nextBlock.getCoords()){
            if(board.getPosition((int)pos.x,(int)pos.y)!=0){
                return false;
            }
        }
        return true;
    }

    private boolean tryMoveDown(){
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
        return true;
    }
}
