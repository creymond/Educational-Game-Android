package com.education.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import static com.badlogic.gdx.utils.TimeUtils.millis;

public class Background implements Drawable {
    Texture backgroundTexture;
    public Background(String path){
        backgroundTexture = new Texture(Gdx.files.internal(path));
    }
    public void draw(SpriteBatch batch){
        //TODO Mmake it generic with the camera size
        //TODO Resolve glitch where the background jumps around
        //backgroundTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        //int mil = (int)((millis()/10)%10000);
        batch.draw(backgroundTexture,0,0,480,800);
    }

    @Override
    public void draw(Batch batch, float x, float y, float width, float height) {
        batch.draw(backgroundTexture,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
    }

    @Override
    public float getLeftWidth() {
        return 0;
    }

    @Override
    public void setLeftWidth(float leftWidth) {

    }

    @Override
    public float getRightWidth() {
        return 0;
    }

    @Override
    public void setRightWidth(float rightWidth) {

    }

    @Override
    public float getTopHeight() {
        return 0;
    }

    @Override
    public void setTopHeight(float topHeight) {

    }

    @Override
    public float getBottomHeight() {
        return 0;
    }

    @Override
    public void setBottomHeight(float bottomHeight) {

    }

    @Override
    public float getMinWidth() {
        return 0;
    }

    @Override
    public void setMinWidth(float minWidth) {

    }

    @Override
    public float getMinHeight() {
        return 0;
    }

    @Override
    public void setMinHeight(float minHeight) {

    }
}
