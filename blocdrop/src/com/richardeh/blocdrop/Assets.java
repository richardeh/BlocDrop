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
	
	public static Texture redSquare, blueSquare, greenSquare, orangeSquare, yellowSquare;
	public static TextureRegion redRegion, blueRegion, greenRegion, orangeRegion, yellowRegion;
	
	public static Music music;
	public static Sound rowClearSound;
	
	public static void load(){
		redSquare = new Texture(Gdx.files.internal("red.png"));
		blueSquare = new Texture(Gdx.files.internal("blue.png"));
		orangeSquare = new Texture(Gdx.files.internal("orange.png"));
		greenSquare = new Texture(Gdx.files.internal("green.png"));
		yellowSquare = new Texture(Gdx.files.internal("yellow.png"));
		
		redRegion = new TextureRegion(redSquare);
		blueRegion = new TextureRegion(blueSquare);
		orangeRegion = new TextureRegion(orangeSquare);
		greenRegion = new TextureRegion(greenSquare);
		yellowRegion = new TextureRegion(yellowSquare);
	}
	
	public static void playSound(Sound sound){
		sound.play(1);
	}
}
