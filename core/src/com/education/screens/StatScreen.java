package com.education.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.education.Database.TypeFigure;
import com.education.UI.ContainerStatsUI;
import com.education.mainGame.FigureSortGame;

import static com.education.Constants.CTHULHU_UI;

public class StatScreen implements Screen {

    final FigureSortGame game;
    protected Stage stage;
    private Skin skin;
    private ContainerStatsUI csui;

    public StatScreen(final FigureSortGame game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal(CTHULHU_UI));
        csui = new ContainerStatsUI(new Widget(),skin);
    }

    @Override
    public void show() {
        csui = addMenuListenner(csui);
        Table table = new Table();
        table.setFillParent(true);

        Array<String> arrTypes = getAllTypes();
        for(String str : arrTypes) {
            int average = game.db.computeMasteryAverage(str);
            csui.addOption(str,average);
        }
        csui = addInfosListenner(csui);

        table.add(csui).width(Gdx.graphics.getWidth()).height(Gdx.graphics.getHeight());
        table.getCell(csui).size(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        stage.addActor(table);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width,height);
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

    private ContainerStatsUI addMenuListenner(ContainerStatsUI csu) {


        TextButton menuButton = csu.getMenuButton();
        menuButton.addListener(new ClickListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new MainMenuScreen(game));
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
            }
        });
        csu.updateMenuButton(menuButton);
        return csu;
    }

    private Array<String> getAllTypes() {
        Array<String> arr = new Array<String>();
        for(TypeFigure tf : TypeFigure.values()) {
            arr.add(tf.getValue());
        }
        return arr;
    }

    private ContainerStatsUI addInfosListenner(ContainerStatsUI csu) {
        Label.LabelStyle style = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("UI/Fonts/helvectica.fnt")), Color.WHITE);
        final Dialog d = new Dialog("Infos", skin);
        d.getTitleLabel().setAlignment(Align.center);
        d.getTitleLabel().setStyle(style);
        d.getTitleLabel().setPosition(2.0f, 2.0f);
        d.getTitleLabel().setFontScale(2.0f);

        Table table = new Table();
        table.setFillParent(true);
        Label row1 = new Label("Mention", skin);
        Label row1Bis = new Label("Note", skin);
        table.add(row1).padLeft(20).padRight(20);
        table.add(row1Bis).padLeft(20).padRight(20);
        table.row();

        Label test = new Label("", skin);
        table.add(test);
        table.row();

        Label row2 = new Label("Debutant", skin);
        Label row2Bis = new Label("0 - 10", skin);
        table.add(row2).padLeft(20).padRight(20);
        table.add(row2Bis).padLeft(20).padRight(20);
        table.row();

        Label row3 = new Label("Passable", skin);
        Label row3Bis = new Label("10 - 12", skin);
        table.add(row3).padLeft(20).padRight(20);
        table.add(row3Bis).padLeft(20).padRight(20);
        table.row();

        Label row4 = new Label("Assez bien", skin);
        Label row4Bis = new Label("12 - 14", skin);
        table.add(row4).padLeft(20).padRight(20);
        table.add(row4Bis).padLeft(20).padRight(20);
        table.row();

        Label row5 = new Label("Bien", skin);
        Label row5Bis = new Label("14 - 16", skin);
        table.add(row5).padLeft(20).padRight(20);
        table.add(row5Bis).padLeft(20).padRight(20);
        table.row();

        Label row6 = new Label("Tres bien", skin);
        Label row6Bis = new Label("16 - 19", skin);
        table.add(row6).padLeft(20).padRight(20);
        table.add(row6Bis).padLeft(20).padRight(20);
        table.row();

        Label row7 = new Label("Maitrise", skin);
        Label row7Bis = new Label("19 - 20", skin);
        table.add(row7).padLeft(20).padRight(20);
        table.add(row7Bis).padLeft(20).padRight(20);
        table.row();


        d.getContentTable().bottom().left().add(table);
        d.button("OK", true);
        d.key(Input.Keys.ENTER, true);

        TextButton infosButton = csu.getInfosButton();
        infosButton.addListener(new ClickListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                d.show(stage);
                d.setPosition(0, 400);
                d.setWidth(Gdx.graphics.getWidth());
                d.setHeight(Gdx.graphics.getHeight()/2);
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
            }
        });
        csu.updateInfosButton(infosButton);
        return csu;
    }
}
