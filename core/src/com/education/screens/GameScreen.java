package com.education.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.education.Database.Database;
import com.education.Gameplay.Manager;
import com.education.Input.MyInputProcessor;
import com.education.UI.ContainerMonsterUI;
import com.education.UI.ContainerSpellUI;
import com.education.UI.ContainerUI;
import com.education.UI.ElementOrbs;
import com.education.UI.Monster;
import com.education.mainGame.FigureSortGame;

import playGround.PlayGroundGame;
import playGround.PlayGroundScreen;

import static com.education.Constants.CTHULHU_UI;
import static com.education.Constants.SCALE;


public class GameScreen implements Screen {

    final FigureSortGame game;
    private Stage stage;
    private Table globalTable;
    private Table playerTable;
    private Table monsterTable;
    private ContainerUI playerUI;
    private ContainerSpellUI spellUI;
    private ContainerMonsterUI monsterUI;
    private Dialog dlgGameOver;
    private TextButton btnTryAgain;
    private TextButton btnMain;
    private Skin skin;
    private Manager manager;
    private Database database;
    public GameScreen(final FigureSortGame game,Database db) {
        this.game = game;
        database = db;
        create();
    }
    public void create () {
        //MODEL CREATION
        manager = new Manager(database,this);
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal(CTHULHU_UI));

        //UI INIT
        globalTable = new Table();
        monsterTable = new Table();
        playerTable = new Table();

        monsterUI = new ContainerMonsterUI();
        globalTable.setFillParent(true);
        stage.addActor(globalTable);


        //=====================================================================================================================
        globalTable.top();
        /*
         * Monster spell Container
         */
        spellUI = new ContainerSpellUI(skin,manager);
        globalTable.add(spellUI);
        globalTable.getCell(spellUI).size(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()/3);
        manager.setSpellUI(spellUI);
        //=====================================================================================================================
        globalTable.row();
        globalTable.center();
        /*
         * Monster / orb / special effects
         */
        globalTable.add(monsterUI);
        globalTable.getCell(monsterUI).size(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()/3);
        manager.setMonsterUI(monsterUI);


        //=====================================================================================================================
        globalTable.row();
        globalTable.bottom();
        /*
         * Player Container
         */
        playerUI = new ContainerUI(skin,manager);
        globalTable.add(playerUI);
        globalTable.getCell(playerUI).size(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()/3);
        //globalTable.setDebug(true); // This is optional, but enables debug lines for tables.
        manager.setPlayerUI(playerUI);
        // Add widgets to the globalTable here.
        manager.setUpForFigure(database.getRandomFigure(),database.getAdequateDifficulty());

    }

    @Override
    public void show() {

    }

    public void resize (int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    public void render (float delta) {
        Gdx.gl.glClearColor(0.1f, 0f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    public void dispose() {
        stage.dispose();
        manager.dispose();
    }
    public void gameOver(boolean victory){
        dlgGameOver = new Dialog("",skin);
        if(victory)
            dlgGameOver.text("Victoire !");
        else
            dlgGameOver.text("Tu feras mieux la prochaine fois !");
        btnMain = new TextButton("Retour", skin);
        btnMain.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                database.saveSession(database.getFigures());
                game.mainMenuMusic.play();
                game.setScreen(new MainMenuScreen(game));
                dispose();
                return  false;
            }
        });

        btnTryAgain = new TextButton("Rejouer", skin);

        btnTryAgain.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                database.saveSession(database.getFigures());
                game.setScreen(new GameScreen(game,database));
                dispose();
                return  false;
            }
        });

        dlgGameOver.button(btnTryAgain);
        dlgGameOver.button(btnMain);
        dlgGameOver.show(stage);
    }
}
