package com.education.mainGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.education.Database.Database;
import  com.education.screens.*;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


import static com.education.Constants.MAIN_THEME_SOUND;
import static com.education.Constants.SCALE;


public class FigureSortGame extends Game {

	public SpriteBatch batch;
	public BitmapFont font;
    public Database db;
	public Music mainMenuMusic;
    public FigureSortGame(){
    }

	public void create() {
		mainMenuMusic = Gdx.audio.newMusic(Gdx.files.internal(MAIN_THEME_SOUND));
		mainMenuMusic.setLooping(true);
		mainMenuMusic.setVolume(0.5f);
		batch = new SpriteBatch();
		//Use LibGDX's default Arial font.
		font = new BitmapFont();
		db = new Database();
		db.initDatabase();
		font.getData().setScale(SCALE-1);
		font.setColor(Color.BLACK);
		mainMenuMusic.play();
		this.setScreen(new MainMenuScreen(this));
	}

	public void render() {
		super.render(); //important!
	}

	public void dispose() {
		batch.dispose();
		font.dispose();
	}

}