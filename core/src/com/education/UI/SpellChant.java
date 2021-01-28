package com.education.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.education.Constants.UI_CONTAINER_BOX;

public class SpellChant extends Actor {
    int COLUMNS;
    float x, y;
    private TextureRegion[] boxRegions;
    private Texture containerBoxTexture;
    private String message;
    private GlyphLayout layout;

    public SpellChant(float x, float y, String message) {
        layout = new GlyphLayout();
        layout.setText(new BitmapFont(), message);
        float w = layout.width;

        containerBoxTexture = new Texture(Gdx.files.internal(UI_CONTAINER_BOX));
        TextureRegion[][] tmp = TextureRegion.split(containerBoxTexture, 16, 16);

        for (int i = 0; i < tmp.length; i++) {
            for (int j = 0; j < tmp[i].length; j++) {
                boxRegions[tmp[i].length * i + j] = tmp[i][j];
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}
