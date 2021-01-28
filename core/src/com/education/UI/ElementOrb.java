package com.education.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.TimeUtils;


import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import static com.education.Constants.SCALE;

public class ElementOrb extends Actor {
    Vector2 base;
    private TextureRegion orbTexture;
    public ElementOrb(TextureRegion texture){
        orbTexture = texture;
        //speed = (float)Math.random()*ORBS_SPEED;
        //direction = (float)Math.pow(-1,(int) Math.random()*50.0f);
        setBounds(texture.getRegionX(),texture.getRegionY(),texture.getRegionWidth()*(SCALE+1),texture.getRegionHeight()*(SCALE+1));
    }

    public void centerSelf(Vector2 center){
        this.clearActions();
        this.addAction(parallel(
                moveTo(center.x,center.y,3),
                scaleTo(getScaleX()*2,getScaleY()*2,2)
        ));
    }

    public void slideAway(){
        this.clearActions();
        this.addAction(parallel(
                moveTo(this.getX() + 100 * (this.getX() > Gdx.graphics.getWidth()/2 ? -1:1),
                        this.getY(),3),
                scaleTo(getScaleX(),getScaleY(),2),
                color(Color.DARK_GRAY,2)
        ));
    }

    public void reinit(float duration){
        //this.clearActions();
        this.addAction(parallel(
                moveTo(this.getX(),
                        this.getY(),duration),
                scaleTo(getScaleX(),getScaleY(),duration),
                color(getColor(),duration)
        ));
    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a);
        batch.draw(orbTexture,getX()- orbTexture.getRegionWidth()/2,
                getY() - orbTexture.getRegionHeight()/2,
                getWidth(),
                getHeight());
    }
    /*@Override
    public void draw(Batch batch, float parentAlpha) {
        yoffset =  (float) Math.sin(TimeUtils.millis() * 0.001* speed);
        yoffset *= 2*SCALE*orbTexture.getRegionWidth()/2;
        batch.draw(orbTexture,x-2*SCALE*orbTexture.getRegionWidth()/2
                ,y+ yoffset - 2*SCALE*orbTexture.getRegionHeight()/2
                ,2*SCALE*orbTexture.getRegionWidth(),
                2*SCALE*orbTexture.getRegionHeight());
    }*/

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public void setBase(Vector2 base) {
        this.base = base;
    }
}
