package com.richardeh.blocdrop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
	public static Texture background;
	public static TextureRegion backgroundRegion;

    public static Texture numberTexture;
    public static TextureRegion zero, one, two, three, four, five, six, seven, eight, nine;
	
	public static Texture redSquare, blueSquare, greenSquare, orangeSquare, yellowSquare, whiteSquare;
	public static TextureRegion redRegion, blueRegion, greenRegion, orangeRegion, yellowRegion, whiteRegion;
	
	public static Music music;
	
	public static void load(){
		background = new Texture(Gdx.files.internal("background.png"));
		redSquare = new Texture(Gdx.files.internal("red.png"));
		blueSquare = new Texture(Gdx.files.internal("blue.png"));
		orangeSquare = new Texture(Gdx.files.internal("orange.png"));
		greenSquare = new Texture(Gdx.files.internal("green.png"));
		yellowSquare = new Texture(Gdx.files.internal("yellow.png"));
		whiteSquare = new Texture(Gdx.files.internal("white.png"));
		numberTexture = new Texture(Gdx.files.internal("numbers.png"));
		
		redRegion = new TextureRegion(redSquare, 0, 0, 32, 32);
		blueRegion = new TextureRegion(blueSquare, 0, 0, 32, 32);
		orangeRegion = new TextureRegion(orangeSquare, 0, 0, 32, 32);
		greenRegion = new TextureRegion(greenSquare, 0, 0, 32, 32);
		yellowRegion = new TextureRegion(yellowSquare, 0, 0, 32, 32);
		whiteRegion = new TextureRegion(whiteSquare, 0, 0, 32, 32);
		backgroundRegion = new TextureRegion(background, 0, 0, 640,800);
		zero = new TextureRegion(numberTexture,0,0,16,24);
		one = new TextureRegion(numberTexture, 21,0,16,24);
		two = new TextureRegion(numberTexture, 40,0,16,24);
		three = new TextureRegion(numberTexture, 60,0,16,24);
		four = new TextureRegion(numberTexture,78,0,18,24);
		five = new TextureRegion(numberTexture, 100,0, 16,24);
		six = new TextureRegion(numberTexture, 119,0,16,24);
		seven = new TextureRegion(numberTexture,139,0,17,24);
		eight = new TextureRegion(numberTexture, 160,0,15,24);
		nine = new TextureRegion(numberTexture, 179,0,16,24);
		
		music = Gdx.audio.newMusic(Gdx.files.internal("music.ogg"));
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
        music.dispose();
    }
}
