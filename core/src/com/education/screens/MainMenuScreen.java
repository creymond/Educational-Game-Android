package com.education.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.education.Input.MyInputProcessor;
import com.education.UI.Background;
import com.education.UI.CustomButton;
import com.education.mainGame.FigureSortGame;
import com.badlogic.gdx.math.Rectangle;


public class MainMenuScreen implements Screen {

    public static final int WORLD_WIDTH = 480;
    public static final int WORLD_HEIGHT = 800;

    final FigureSortGame game;

    private SpriteBatch batch;
    protected Stage stage;
    private Viewport viewport;
    private OrthographicCamera camera;

    private CustomButton startCustomButton;
    private CustomButton statsCustomButton;
    private CustomButton spellBookCustomButton;
    private Texture title;
    private Rectangle titleImg;

    private Background background;
    GlyphLayout layout; //Used to compute font's size

    public MainMenuScreen(final FigureSortGame game) {
        Gdx.input.setInputProcessor(new MyInputProcessor());
        this.game = game;

        background = new Background("Backgrounds/MenuBackground4.png");
        layout = new GlyphLayout();

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        viewport.apply();
        startCustomButton = new CustomButton("buttons/startCustomButton.png", 1.6f, this.game, 1);
        statsCustomButton = new CustomButton("buttons/statsCustomButton.png", 1.6f, this.game, 2);
        spellBookCustomButton = new CustomButton("buttons/spellBookCustomButton.png", 1.6f, this.game, 3);
        title = new Texture(Gdx.files.internal("buttons/title.png"));
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
        titleImg = new Rectangle();
        
        titleImg.set(camera.viewportWidth/2 - 192,camera.viewportHeight - 256, 400, 200);

        stage = new Stage(viewport, batch);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        //Create Table
        Table mainTable = new Table();
        //Set table to fill stage
        mainTable.setFillParent(true);
        //Set alignment of contents in the table.

        mainTable.center().bottom().pad(100);
        mainTable.add(startCustomButton).expandX().center().pad(20);
        mainTable.row();
        mainTable.add(spellBookCustomButton).expandX().center().pad(20);
        mainTable.row();
        mainTable.add(statsCustomButton).expandX().center().pad(20);


        //mainTable.debugTable();
        stage.addActor(mainTable);
    }

    @Override
    public void render(float delta) {
        // clear the screen with a dark blue color. The
        // arguments to glClearColor are the red, green
        // blue and alpha component in the range [0,1]
        // of the color to be used to clear the screen.
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // tell the camera to update its matrices.
        camera.update();

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        game.batch.setProjectionMatrix(camera.combined);


        game.batch.begin();
        background.draw(game.batch);
        game.batch.draw(title, titleImg.x, titleImg.y, titleImg.width, titleImg.height);

        game.batch.end();


        //game.render();
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width,height);
        stage.getCamera().position.set(WORLD_WIDTH/2,WORLD_HEIGHT/2,0);
        stage.getCamera().update();
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

    @Override
    public void dispose() {

    }

}