package com.education.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;

import java.text.Normalizer;

public class ContainerBookUI extends ScrollPane {

    private Array<TextButton> optionsButtons;
    private Array<Label> optionsExplanation;
    private Table table;
    private Skin skin;
    private TextButton menuButton;
    public ContainerBookUI(Actor widget, Skin skin) {
        super(widget, skin);
        this.layout();
        optionsButtons = new Array<TextButton>();
        optionsExplanation = new Array<Label>();
        this.skin = skin;
        table = new Table();
        table.setFillParent(true);
        table.bottom();
        this.skin.getFont("font").getData().setScale(2.0f,2.0f);
        this.setActor(table);
        menuButton = new TextButton("Menu", skin);
        optionsButtons.add(menuButton);
        table.add(menuButton);
    }

    public void addOption(String label) {
        String noAcc = removeAccent(label);
        String strUp = noAcc.substring(0, 1).toUpperCase() + noAcc.substring(1);
        TextButton butt = new TextButton(strUp,skin);
        optionsButtons.add(butt);
        table.row();
        table.add(butt).padTop(20).padBottom(30).width(Gdx.graphics.getWidth()/2).height(Gdx.graphics.getHeight()/10);

    }

    public void clearOptions(){
        for(TextButton l : optionsButtons) {
            table.removeActor(l);
        }

        for(Label l : optionsExplanation) {
            table.removeActor(l);
        }
        optionsButtons.clear();
        optionsExplanation.clear();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public void updateButton(TextButton butt) {
        for(int i = 0; i < optionsButtons.size; i++) {
            TextButton t = optionsButtons.get(i);
            if(t.getText().equals(butt.getText())) {
                optionsButtons.set(i,butt);
            }
        }
    }

    public Array<TextButton> getButtons() {
        return optionsButtons;
    }

    public TextButton getButtonFromText(String text) {
        String noAcc = removeAccent(text);
        String strUp = noAcc.substring(0, 1).toUpperCase() + noAcc.substring(1);
        TextButton tb = null;
        for(TextButton t : optionsButtons) {
            String str = t.getText().toString();
            if(str.equals(strUp)) {
                tb = t;
            }
        }
        //System.out.println(tb == null);
        return tb;
    }

    public TextButton getMenuButton() {
        TextButton tb = null;
        for(TextButton t : optionsButtons) {
            String str = t.getText().toString();
            if(str.equals("Menu")) {
                tb = t;
            }
        }
        return tb;
    }

    private String removeAccent(String label) {
        String str = label;
        str = Normalizer.normalize(str, Normalizer.Form.NFD);
        str = str.replaceAll("[^\\p{ASCII}]", "");
        return str;
    }
}
