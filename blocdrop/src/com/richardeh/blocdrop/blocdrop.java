package com.richardeh.blocdrop;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class blocdrop implements ApplicationListener {
	
	private static final int VIRTUAL_WIDTH = 480;
	private static final int VIRTUAL_HEIGHT = 640;
	private static final float ASPECT_RATIO = (float)VIRTUAL_WIDTH/(float)VIRTUAL_HEIGHT;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Sprite blueSprite, redSprite, greenSprite, orangeSprite,yellowSprite, whiteSprite;
	private Board board;
	private Rectangle viewport;
	Game game;
    private float deltaTime, ticks;
    public int speed;
	float w, h;
	
	@Override
	public void create() {		
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();
		Assets.load();
		camera = new OrthographicCamera(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
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
        speed = 2;
	}

	@Override
	public void dispose() {
		batch.dispose();
        Assets.dispose();
	}

	@Override
	public void render() {		
		Gdx.gl.glViewport((int)viewport.x, (int)viewport.y, (int)viewport.width, (int)viewport.height);
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
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
			}
		}
		
        ticks += System.currentTimeMillis()-deltaTime;
		if(ticks>=1*speed){
            game.updateGame();
            ticks = 0;
        } else{
        	ticks +=0.1;
        }
	}

	@Override
	public void resize(int width, int height) {
		float aspectRatio = (float)width/(float)height;
		float scale = 1f;
		Vector2 crop = new Vector2(0f, 0f);
		
		if(aspectRatio>ASPECT_RATIO){
			scale = (float)height/(float)VIRTUAL_HEIGHT;
			crop.x = (width - VIRTUAL_WIDTH*scale)/2f;
		} else if(aspectRatio < ASPECT_RATIO){
			scale = (float)width/(float)VIRTUAL_WIDTH;
			crop.y = (height-VIRTUAL_HEIGHT*scale)/2f;
		} else {
			scale = (float)width/(float)VIRTUAL_WIDTH;
		}
		
		w = (float)VIRTUAL_WIDTH * scale;
		h = (float)VIRTUAL_HEIGHT * scale;
		viewport = new Rectangle(crop.x, crop.y, w, h);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
