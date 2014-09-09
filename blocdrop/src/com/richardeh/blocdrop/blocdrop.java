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
	
	private static final int VIRTUAL_WIDTH = 1024;
	private static final int VIRTUAL_HEIGHT = 1536;
	private static final float ASPECT_RATIO = (float)VIRTUAL_WIDTH/(float)VIRTUAL_HEIGHT;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Sprite blueSprite, redSprite, greenSprite, orangeSprite,yellowSprite, whiteSprite;
	private Board board;
	private Rectangle viewport;
	Game game;
    private float deltaTime, maxTime;
    public float speed, speedMod;
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
		deltaTime = Gdx.graphics.getDeltaTime();
		speed = 10;
		maxTime = 10;
        Assets.music.setLooping(true);
        Assets.music.play();
        System.out.println(deltaTime);
        System.out.println(maxTime);
	}

	@Override
	public void dispose() {
		batch.dispose();
        Assets.dispose();
	}

	@Override
	public void render() {
		// TODO: Draw the scoreboard. Fix the camera so that it fills the whole screen regardless of screen size
		Gdx.gl.glViewport((int)viewport.x, (int)viewport.y, (int)viewport.width, (int)viewport.height);
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		ArrayList<ArrayList<Integer>> currentBoard = game.getBoard().getBoard();
        Sprite currSprite = new Sprite();
		batch.begin();
		batch.draw(Assets.backgroundRegion,0,0);
		for(int x=0;x<currentBoard.size()-3;x++){
			for(int y=0;y<currentBoard.get(x).size();y++){
				
				switch(currentBoard.get(x).get(y)){
					case 0:
                        currSprite = whiteSprite;
						break;
					case 1:
						currSprite = blueSprite;
						break;
					case 2:
						currSprite = orangeSprite;
						break;
					case 3:
						currSprite = orangeSprite;
						break;
					case 4:
						currSprite = yellowSprite;
						break;
					case 5:
						currSprite = greenSprite;
						break;
					case 6:
						currSprite = redSprite;
						break;
						default:
							break;
				}
				currSprite.setPosition(32*y+64, 32*x+32);
                currSprite.draw(batch);
			}
		}
        drawNext();
		batch.end();
		drawScoreBoard(game.getScore());
        deltaTime += Gdx.graphics.getDeltaTime();
        int rows = game.getRowsCleared();
        speedMod = calculateSpeedMod(rows);
		if(deltaTime>(maxTime/(speed-speedMod))){
            game.updateGame();
            deltaTime = 0;
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
		Assets.music.pause();
	}

	@Override
	public void resume() {
		Assets.music.play();
	}

    private void drawNext(){
    	// Draws the next piece in the 'next' window
        Block next = game.getNextBlock();
        Sprite currSprite = new Sprite();
        ArrayList<Vector2> positions = new ArrayList<Vector2>();

        switch(next.getShape()){
            case O:
                positions.add(new Vector2(1,1));
                positions.add(new Vector2(2,1));
                positions.add(new Vector2(1,2));
                positions.add(new Vector2(2,2));
                break;
            case I:
                positions.add(new Vector2(1.5f,0));
                positions.add(new Vector2(1.5f,1));
                positions.add(new Vector2(1.5f,2));
                positions.add(new Vector2(1.5f,3));
                break;
            case T:
            	positions.add(new Vector2(0.5f,1));
            	positions.add(new Vector2(1.5f,1));
            	positions.add(new Vector2(2.5f,1));
            	positions.add(new Vector2(1.5f,2));
                break;
            case L:
            	positions.add(new Vector2(1,0.5f));
            	positions.add(new Vector2(1,1.5f));
            	positions.add(new Vector2(1,2.5f));
            	positions.add(new Vector2(2,0.5f));
                break;
            case J:
            	positions.add(new Vector2(2,0.5f));
            	positions.add(new Vector2(2,1.5f));
            	positions.add(new Vector2(2,2.5f));
            	positions.add(new Vector2(1,0.5f));
                break;
            case S:

            	positions.add(new Vector2(0.5f,1));
            	positions.add(new Vector2(1.5f,1));
            	positions.add(new Vector2(1.5f,2));
            	positions.add(new Vector2(2.5f,2));
                break;
            case Z:
            	positions.add(new Vector2(0.5f,2));
            	positions.add(new Vector2(1.5f,2));
            	positions.add(new Vector2(1.5f,1));
            	positions.add(new Vector2(2.5f,1));
                break;
        }

        switch(next.getValue()){
            case 0:
                currSprite = whiteSprite;
                break;
            case 1:
                currSprite = blueSprite;
                break;
            case 2:
                currSprite = orangeSprite;
                break;
            case 3:
                currSprite = orangeSprite;
                break;
            case 4:
                currSprite = yellowSprite;
                break;
            case 5:
                currSprite = greenSprite;
                break;
            case 6:
            	currSprite = redSprite;
            	break;
            default:
                break;
        }

        for(Vector2 v:positions){
            currSprite.setPosition(v.x*32+480,v.y*32+513);
            currSprite.draw(batch);
        }
    }

    private float calculateSpeedMod(int rows){
        if(rows<10) return 0;
        if(rows<100) return (rows/10)%10;
        return 9;
    }

    private void drawScoreBoard(int score){
        float ones, tens, hundreds, thousands, tenKs, hundredKs;
        hundredKs = score/100000;
        tenKs = (score - (hundredKs*100000))/10000;
        thousands = (score - (hundredKs*100000)-(tenKs*10000))/1000;
        hundreds = (score - (hundredKs*100000)-(tenKs*10000)-(thousands*1000))/100;
        tens = (score - (hundredKs*100000)-(tenKs*10000)-(thousands*1000)-(hundreds*100))/10;
        ones = (score - (hundredKs*100000)-(tenKs*10000)-(thousands*1000)-(hundreds*100)-(tens*10));
    }
}
