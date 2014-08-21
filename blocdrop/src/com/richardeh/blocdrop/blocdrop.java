package com.richardeh.blocdrop;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class blocdrop implements ApplicationListener {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Sprite blueSprite, redSprite, greenSprite, orangeSprite,yellowSprite, whiteSprite;
	private Board board;
	Game game;
    private float deltaTime, ticks;
    public int speed;
	
	@Override
	public void create() {		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		Assets.load();
		camera = new OrthographicCamera(10, 15);
		batch = new SpriteBatch();
		board = new Board();
		board.start();
		game = new Game(board);
		
		whiteSprite = new Sprite(Assets.whiteRegion);
		blueSprite = new Sprite(Assets.blueRegion);
		redSprite = new Sprite(Assets.redRegion);
		greenSprite = new Sprite(Assets.greenRegion);
		orangeSprite = new Sprite(Assets.orangeRegion);
		yellowSprite = new Sprite(Assets.yellowRegion);
		deltaTime = System.currentTimeMillis();
        ticks = 0;
        speed = 1;
	}

	@Override
	public void dispose() {
		batch.dispose();
        Assets.dispose();
	}

	@Override
	public void render() {		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		ArrayList<ArrayList<Integer>> currentBoard = game.getBoard().getBoard();

		for(int x=0;x<currentBoard.size();x++){
			for(int y=0;y<currentBoard.get(x).size();y++){
				batch.begin();
				switch(currentBoard.get(x).get(y)){
					case 0:
						whiteSprite.setPosition(32*y, 32*x);
						whiteSprite.draw(batch);
						break;
					case 1:
						blueSprite.setPosition(32*y, 32*x);
						blueSprite.draw(batch);
						break;
					case 2:
						redSprite.setPosition(32*y, 32*x);
						redSprite.draw(batch);
						break;
					case 3:
						orangeSprite.setPosition(32*y,32*x);
						orangeSprite.draw(batch);
						break;
					case 4:
						yellowSprite.setPosition(32*y,32*x);
						yellowSprite.draw(batch);
						break;
					case 5:
						greenSprite.setPosition(32*y, 32*x);
						greenSprite.draw(batch);
						break;
						default:
							break;
				}
				batch.end();
				//System.out.print(currentBoard.get(x).get(y)+" ");
			}
			//System.out.println();
		}
		// TODO: slow the game down
        ticks += System.currentTimeMillis()-deltaTime;
		if(ticks>=1*speed){
            // This should only be called once a full second has elapsed
            game.updateGame(ticks);
            ticks = 0;
        } else{
        	ticks +=0.1;
        }
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
