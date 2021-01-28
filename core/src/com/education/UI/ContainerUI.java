package com.education.UI;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.education.Database.FigureDeStyle;
import com.education.Gameplay.Manager;

import java.text.Normalizer;

import javax.xml.soap.Text;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.color;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import static com.education.Constants.CTHULHU_UI;
import static com.education.Constants.SCALE;

public class ContainerUI extends Table {

    private Array<String> optionsStrings;
    private Array<FigureDeStyle> optionsFigure;
    private Array<Button> options;
    private Table table;
    private ScrollPane scrollpane;
    private ProgressBar playerHealth;
    private Manager manager;
    public ContainerUI(Skin skin, Manager m) {
        super(skin);
        manager = m;
        optionsStrings = new Array<String>();
        optionsFigure = new Array<FigureDeStyle>();
        options =  new Array<Button>();
        table = new Table();
        table.setFillParent(true);
        table.bottom();

        playerHealth = new ProgressBar(0,100,5,false,skin,"mana");
        playerHealth.setValue(100);
        scrollpane = new ScrollPane(table, skin);
        scrollpane.setFillParent(true);

        this.bottom().left();
        this.add(scrollpane).prefWidth(Gdx.graphics.getWidth()).prefHeight(Gdx.graphics.getHeight()-playerHealth.getPrefHeight());
        this.row();
        this.add(playerHealth).prefWidth(Gdx.graphics.getWidth());

        /*this.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                if(event.getTarget() instanceof TextButton && event instanceof  InputEvent) {
                    TextButton txtBt= (TextButton)event.getTarget();
                    InputEvent in = (InputEvent) event;
                    System.out.println("Handled input for value :  " + txtBt.getText() + " and " + in.getClass().toString());
                }
                return false;
            }
        });*/
        table.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                //System.out.println("Handled an Event TABLE");
                return false;
            }
        });
    }

    public void addOption(String opt){
        optionsStrings.add(opt);
        opt = Normalizer.normalize(opt, Normalizer.Form.NFD);
        opt = opt.replaceAll("[^\\p{ASCII}]", "");
        TextButton tmp = new TextButton(opt,new Skin(Gdx.files.internal(CTHULHU_UI)));
        tmp.getLabel().setWrap(true);

        tmp.addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                if(event.getTarget() instanceof TextButton)
                    manager.checkAnswerType(( ((TextButton) event.getTarget()).getText().toString()));
                if(event.getTarget() instanceof Label)
                    manager.checkAnswerType(( ((Label) event.getTarget()).getText().toString()));
                return false;
            }
        });
        options.add(tmp);
        table.row();
        table.add(tmp).padTop(tmp.getHeight()/2).width(Gdx.graphics.getWidth()*0.8f);
    }
    public void addOptiontest(int x){
        for (int i = 0; i < x; i++) {
            addOption("Test "+i);
        }
    }
    public void clearOptions(){
        optionsStrings.clear();
        options.clear();
        table.clear();
        table.top();
    }
    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public void setUpForFigure(FigureDeStyle fig, Array<FigureDeStyle> falseAns) {
        System.out.println("Setting up for Figure");
        clearOptions();
        table.bottom();
        for(FigureDeStyle figure : falseAns){
            System.out.println("Adding an option");
            addOption(figure.getType());
        }
        addOption(fig.getType());
    }
    public void setUpForType(FigureDeStyle fig,Array<FigureDeStyle> falseAns) {
        clearOptions();
        for(FigureDeStyle figure : falseAns){
            addOption(figure.getType());
        }
        addOption(fig.getType());
    }

    //TODO
    public void reinit(int i) {
    }

    public void takeDamage(float damage) {
        playerHealth.setValue(playerHealth.getValue() - damage);
        float flip = 0.05f;
        this.clearActions();
        this.addAction(sequence(
                color(new Color(getColor().r,getColor().g,getColor().b,0),flip),
                color(new Color(getColor().r,getColor().g,getColor().b,1),flip),
                color(new Color(getColor().r,getColor().g,getColor().b,0),flip),
                color(new Color(getColor().r,getColor().g,getColor().b,1),flip),
                color(new Color(getColor().r,getColor().g,getColor().b,0),flip),
                color(new Color(getColor().r,getColor().g,getColor().b,1),flip),
                color(new Color(getColor().r,getColor().g,getColor().b,0),flip),
                color(new Color(getColor().r,getColor().g,getColor().b,1),flip),
                color(new Color(getColor().r,getColor().g,getColor().b,0),flip),
                color(new Color(getColor().r,getColor().g,getColor().b,1),flip),
                color(new Color(getColor().r,getColor().g,getColor().b,0),flip),
                color(new Color(getColor().r,getColor().g,getColor().b,1),flip)
        ));
    }

    public float getCurrentHealth(){
        return playerHealth.getValue();
    }
    public boolean isDead(){
        return playerHealth.getValue() == 0f;
    }
}