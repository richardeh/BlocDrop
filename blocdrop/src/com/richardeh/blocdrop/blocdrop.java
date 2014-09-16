package com.richardeh.blocdrop;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class blocdrop implements ApplicationListener {
	
	private static final int VIRTUAL_WIDTH = 640;
	private static final int VIRTUAL_HEIGHT = 800;
	private static final float ASPECT_RATIO = (float)VIRTUAL_WIDTH/(float)VIRTUAL_HEIGHT;
	
	private Camera camera;
	private SpriteBatch batch;
	private Sprite blueSprite, redSprite, greenSprite, orangeSprite,yellowSprite, whiteSprite;
	private Sprite zeroSprite, oneSprite, twoSprite, threeSprite,fourSprite,fiveSprite,sixSprite,sevenSprite,eightSprite,nineSprite;
	Board board;
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
		
		zeroSprite = new Sprite(Assets.zero);
		oneSprite = new Sprite(Assets.one);
		twoSprite = new Sprite(Assets.two);
		threeSprite = new Sprite(Assets.three);
		fourSprite = new Sprite(Assets.four);
		fiveSprite = new Sprite(Assets.five);
		sixSprite = new Sprite(Assets.six);
		sevenSprite = new Sprite(Assets.seven);
		eightSprite = new Sprite(Assets.eight);
		nineSprite = new Sprite(Assets.nine);
		
		deltaTime = Gdx.graphics.getDeltaTime();
		speed = 10;
		maxTime = 10;
        Assets.music.setLooping(true);
        Assets.music.play();
	}
	@Override
	public void dispose() {
		batch.dispose();
        Assets.dispose();
	}

	@Override
	public void render() {
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		
		camera.update();

		Gdx.gl.glViewport((int)viewport.x, (int)viewport.y, (int)viewport.width, (int)viewport.height);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		ArrayList<ArrayList<Integer>> currentBoard = game.getBoard().getBoard();
        Sprite currSprite = new Sprite();
		batch.begin();
		
		batch.draw(Assets.background,0,0,viewport.width,viewport.height);
		w=viewport.width/Assets.backgroundRegion.getRegionWidth();
        h=viewport.height/Assets.backgroundRegion.getRegionHeight();

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
				// TODO: test and adjust
				currSprite.setScale(w,h);
				currSprite.setPosition((32*y+64)*w, (32*x+32)*w);
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
		float scale;
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
        // This is probably the ugliest way to get this done.

        Block next = game.getNextBlock();
        Sprite currSprite = new Sprite();
        ArrayList<Vector2> positions = new ArrayList<Vector2>();

        w=viewport.width/Assets.backgroundRegion.getRegionWidth();
        h=viewport.height/Assets.backgroundRegion.getRegionHeight();

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
        	currSprite.setScale(w,h);
            currSprite.setPosition((v.x*32+480)*w,(v.y*32+513)*h);
            currSprite.draw(batch);
        }
    }

    private float calculateSpeedMod(int rows){
        if(rows<10) return 0;
        if(rows<100) return (rows/10)%10;
        return 9;
    }

    private void drawScoreBoard(int score){
        // Draw the scoreboard
        Sprite numSprite;

        w=viewport.width/Assets.backgroundRegion.getRegionWidth();
        h=viewport.height/Assets.backgroundRegion.getRegionHeight();
        // Determine the digits of the score
        int[] digits = new int[4];
        digits[0] = ((score %10000)/1000);
        digits[1] = ((score %1000)/100);
        digits[2] = ((score %100)/10);
        digits[3] = ((score %10));
        batch.begin();
        for(int i=0;i<digits.length;i++){
            // paint the appropriate digit
        	
            switch(digits[i]){
                case 0:
                	numSprite = zeroSprite;
                    break;
                case 1:
                	numSprite = oneSprite;
                    break;
                case 2:
                	numSprite = twoSprite;
                    break;
                case 3:
                	numSprite = threeSprite;
                    break;
                case 4:
                	numSprite = fourSprite;
                    break;
                case 5:
                	numSprite = fiveSprite;
                    break;
                case 6:
                	numSprite = sixSprite;
                    break;
                case 7:
                	numSprite = sevenSprite;
                    break;
                case 8:
                	numSprite = eightSprite;
                    break;
                case 9:
                	numSprite = nineSprite;
                    break;
                default:
                	numSprite = zeroSprite;
                    break;
            }
            numSprite.setScale(w,h);
            numSprite.setPosition((480+i*32)*w,(325*h));
            numSprite.draw(batch);
        }
        batch.end();
    }
}
