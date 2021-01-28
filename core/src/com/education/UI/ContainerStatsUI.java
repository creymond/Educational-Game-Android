package com.education.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import java.text.Normalizer;

public class ContainerStatsUI extends ScrollPane {
    private Table table;
    private Skin skin;
    private TextButton menuButton;
    private TextButton infosButton;

    public ContainerStatsUI(Actor widget, Skin skin) {
        super(widget, skin);
        this.layout();

        this.skin = skin;
        this.skin.getFont("font").getData().setScale(2.0f,2.0f);
        this.setActor(table);
        table = new Table();
        table.setFillParent(true);
        infosButton = new TextButton("Infos", skin);
        menuButton = new TextButton("Menu", skin);
        table.top().center();
        table.add(menuButton);
        table.add(infosButton);
        table.bottom();
        this.setActor(table);
    }

    public void addOption(String text, int mastery) {
        String strUp = text.substring(0, 1).toUpperCase() + text.substring(1);
        String str = strUp;
        str = Normalizer.normalize(str, Normalizer.Form.NFD);
        str = str.replaceAll("[^\\p{ASCII}]", "");

        Label label = new Label(str, skin);

        ProgressBar masteryProgress = new ProgressBar(0,100,1,false,skin);
        masteryProgress.setValue(mastery);

        String level = getLevelFromMastery(mastery);
        Label lvl = new Label(level,skin);
        table.row();
        table.add(label).prefWidth(label.getPrefWidth()/2);
        table.add(masteryProgress).prefWidth(Gdx.graphics.getWidth()/2);
        table.row();
        table.add(lvl);
        table.row();
        Label test = new Label("", skin);
        table.add(test);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public TextButton getMenuButton() {
        return menuButton;
    }

    public void updateMenuButton(TextButton tb) {
        menuButton = tb;
    }

    public TextButton getInfosButton() {
        return infosButton;
    }

    public void updateInfosButton(TextButton tb) {
        infosButton = tb;
    }

    public String getLevelFromMastery(int mastery) {
        String str = "";
        int nb = mastery/5;
        if(nb < 10) {
            str = "Debutant";
        } else if(nb < 12) {
            str = "Passable";
        } else if(nb < 14) {
            str = "Assez Bien";
        } else if(nb < 16) {
            str = "Bien";
        } else if(nb < 19) {
            str = "Tres bien";
        } else {
            str = "Maitrise";
        }
        return str;
    }
}
