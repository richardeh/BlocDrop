package com.richardeh.blocdrop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
	public static Texture background;
	public static TextureRegion backgroundRegion;
	
	public static Texture leftButton, rightButton;
	public static TextureRegion leftBtnRegion, rightBtnRegion;
	
	public static Texture soundOn, soundOff;
	public static TextureRegion soundOnRegion, soundOffRegion;
	
	public static Texture pause;
	public static TextureRegion pauseRegion;
	
	public static Texture redSquare, blueSquare, greenSquare, orangeSquare, yellowSquare, whiteSquare;
	public static TextureRegion redRegion, blueRegion, greenRegion, orangeRegion, yellowRegion, whiteRegion;
	
	public static Music music;
	public static Sound rowClearSound;
	
	public static void load(){
		redSquare = new Texture(Gdx.files.internal("red.png"));
		blueSquare = new Texture(Gdx.files.internal("blue.png"));
		orangeSquare = new Texture(Gdx.files.internal("orange.png"));
		greenSquare = new Texture(Gdx.files.internal("green.png"));
		yellowSquare = new Texture(Gdx.files.internal("yellow.png"));
		whiteSquare = new Texture(Gdx.files.internal("white.png"));
		
		redRegion = new TextureRegion(redSquare, 0, 0, 32, 32);
		blueRegion = new TextureRegion(blueSquare, 0, 0, 32, 32);
		orangeRegion = new TextureRegion(orangeSquare, 0, 0, 32, 32);
		greenRegion = new TextureRegion(greenSquare, 0, 0, 32, 32);
		yellowRegion = new TextureRegion(yellowSquare, 0, 0, 32, 32);
		whiteRegion = new TextureRegion(whiteSquare, 0, 0, 32, 32);
	}
	
	public static void playSound(Sound sound){
		sound.play(1);
	}

    public static void dispose(){
        redSquare.dispose();
        blueSquare.dispose();
        greenSquare.dispose();
        yellowSquare.dispose();
        orangeSquare.dispose();
        //music.dispose();
        //rowClearSound.dispose();
    }
}
