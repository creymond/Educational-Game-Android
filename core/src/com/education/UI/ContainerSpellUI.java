package com.education.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.education.Database.FigureDeStyle;
import com.education.Gameplay.Manager;

import java.text.Normalizer;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import static com.education.Constants.CLOCK;
import static com.education.Constants.CLOCK_HANDLE;

public class ContainerSpellUI extends Table {
        private Skin skin;
        private FigureDeStyle figure;
        private ProgressBar monsterHealth;
        private ProgressBar castBar;
        private Table table;
        private ScrollPane scrollpane;
        private Label figureLabel;
        private Manager manager;
        private float duration;
        public ContainerSpellUI(Skin skin, Manager m) {
            super(skin);
            duration = 30;
            manager = m;
            this.skin = skin;
            table = new Table();
            monsterHealth = new ProgressBar(0,100,1,false,skin,"health");
            monsterHealth.setValue(100);
            castBar = new ProgressBar(0,100,1,false,skin);
            castBar.setValue(100);
            scrollpane = new ScrollPane(table,skin);
            //scrollpane.setDebug(true);
            this.top();
            this.add(monsterHealth).prefWidth(Gdx.graphics.getWidth());
            this.row();
            this.bottom().left();
            this.add(scrollpane).prefWidth(Gdx.graphics.getWidth()).prefHeight(Gdx.graphics.getHeight()-monsterHealth.getPrefHeight());
            table.setFillParent(true);
            table.top().left().add(castBar).prefWidth(Gdx.graphics.getWidth());
            table.row();
            //table.setDebug(true);

        }

    public void setFigure(FigureDeStyle figure){
        this.figure = figure;
        table.clear();
        table.top().left().add(castBar).prefWidth(Gdx.graphics.getWidth());
        table.row();
        //Position on the bottom left of the screen
        table.center();
        table.left();
        String str = figure.getSentence();
        //str= Normalizer.normalize(str, Normalizer.Form.NFD);
        //str= str.replaceAll("[^\\p{ASCII}]", "");
        Label.LabelStyle style = new Label.LabelStyle(new BitmapFont(Gdx.files.internal("UI/Fonts/helveticaSmall.fnt")), Color.WHITE);
        figureLabel = new Label(str,style);
        figureLabel.getStyle().font.getData().setScale(2,2);
        //figureLabel.setFillParent(true);
        figureLabel.setWrap(true);
        table.add(figureLabel).padRight(figureLabel.getHeight()).padLeft(figureLabel.getHeight()).prefWidth(figureLabel.getWidth());
        castBar.setValue(100);
        castBar.addAction(forever(sequence(
                run(new Runnable() {
                    @Override
                    public void run() {

                        castBar.setValue(castBar.getValue()-2);
                        manager.setCastingCompletude(1 - (castBar.getValue()/castBar.getMaxValue()) );
                        if(castBar.getValue() == 0.0){
                            castBar.clearActions();
                            manager.timeIsUp(); }
                    }
                }),delay(duration*2/100)

                )
                )
        );
    }
    @Override
    public void act(float delta) {
        super.act(delta);
    }

    //TODO
    public void reinit(int i) {
        castBar.clearActions();
    }

    //TODO (Should we check if monster is dead ?)
    public void takeDamage(float damage){
            monsterHealth.setValue(monsterHealth.getValue()-damage);
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }
    public boolean isDead(){
            return monsterHealth.getValue() == 0f;
    }
}