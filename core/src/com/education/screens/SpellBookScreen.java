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
import com.education.UI.ContainerBookUI;
import com.education.mainGame.FigureSortGame;

import static com.education.Constants.CTHULHU_UI;


public class SpellBookScreen implements Screen {

    final FigureSortGame game;

    protected Stage stage;
    private ContainerBookUI figuresUI;
    private Skin skin;

    public SpellBookScreen(final FigureSortGame game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal(CTHULHU_UI));
        figuresUI = new ContainerBookUI(new Widget(),skin);
    }

    @Override
    public void show() {

        Array<String> arrFigTypeToAdd = game.db.getFigureNameMastery();
        System.out.println("SIZE TAB IN SPELL : " + arrFigTypeToAdd.size);
        /* Table which contains scrollpane */
        Table table = new Table();
        table.setFillParent(true);

        figuresUI = addMenuListenner(figuresUI);
        // Display figures type already found
        for(String str : arrFigTypeToAdd) {
            figuresUI.addOption(str);
            figuresUI = addListennerToButtons(figuresUI,str);
        }

        table.add(figuresUI).width(Gdx.graphics.getWidth()).height(Gdx.graphics.getHeight());
        table.getCell(figuresUI).size(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
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

    private ContainerBookUI addListennerToButtons(ContainerBookUI cbu, String text) {

        Label.LabelStyle style = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("UI/Fonts/helvectica.fnt")),Color.WHITE);
        String exp = game.db.getExplanationForType(text);
        String expUp = exp.substring(0, 1).toUpperCase() + exp.substring(1);
        String titleUp = text.substring(0, 1).toUpperCase() + text.substring(1);
        final Dialog d = new Dialog(titleUp, skin);
        d.getTitleLabel().setAlignment(Align.center);
        d.getTitleLabel().setStyle(style);
        d.getTitleLabel().setPosition(2.0f, 2.0f);
        d.getTitleLabel().setFontScale(2.0f);

        Label text2 = new Label(expUp,style);
        text2.setScale(4.0f);
        text2.setFillParent(true);
        text2.setWrap(true);
        Table table = new Table();
        table.setFillParent(true);
        //table.setDebug(true);

        table.bottom().left();
        table.add(text2);
        //d.setDebug(true);

        d.getContentTable().bottom().left().add(table);
        d.button("OK", true);
        d.key(Input.Keys.ENTER, true);

        TextButton tb = cbu.getButtonFromText(text);
        if(tb != null) {
            tb.addListener(new ClickListener(){

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
                cbu.updateButton(tb);
            }
        return cbu;
    }

    private ContainerBookUI addMenuListenner(ContainerBookUI cbu) {


        TextButton menuButton = cbu.getMenuButton();
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
        cbu.updateButton(menuButton);
        return cbu;
    }
}
