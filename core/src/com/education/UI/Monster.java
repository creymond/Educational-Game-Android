package com.education.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;

import java.util.Random;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import static com.education.Constants.SCALE;
import static com.education.Constants.WISP_BLUE;
import static com.education.Constants.WISP_DARK;
import static com.education.Constants.WISP_GREEN;
import static com.education.Constants.WISP_RED;

public class Monster extends Actor {
    int ACTIVE = 0;
    int HURT = 1;
    int CURRENT_STATE = ACTIVE;
    Action attackAction;
    Action hurtAction;
    //Coordinates made so that the monster
    int difficulty = 1;
    float spellCastCompletude = 0; // 0 to 1 (0% to 100%)
    private TextureRegion[][] monsterStates;
    private Texture monsterTexture;
    public Monster(){
        Random rand = new Random();
        switch (rand.nextInt(4)){
            case 0:
                monsterTexture = new Texture(Gdx.files.internal(WISP_RED));
                difficulty = 1;
                break;
            case 1:
                monsterTexture = new Texture(Gdx.files.internal(WISP_BLUE));
                difficulty = 2;
                break;
            case 2 :
                monsterTexture = new Texture(Gdx.files.internal(WISP_GREEN));
                difficulty = 3;
                break;
            case 3 :
                monsterTexture = new Texture(Gdx.files.internal(WISP_DARK));
                difficulty = 4;
                break;
        }
        //monsterTexture = new Texture(Gdx.files.internal(WISP_DARK));
        monsterStates = TextureRegion.split(monsterTexture,32,48);
        setBounds(monsterStates[0][0].getRegionX(), monsterStates[0][0].getRegionY(),
                monsterStates[0][0].getRegionWidth()*SCALE, monsterStates[0][0].getRegionHeight()*SCALE);
        this.setOrigin(Align.center);

    }



    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(monsterStates[0][CURRENT_STATE],
                getX()- monsterStates[0][CURRENT_STATE].getRegionWidth(),
                getY()- monsterStates[0][CURRENT_STATE].getRegionHeight(),
                getOriginX(),
                getOriginY(),getWidth(),getHeight(),getScaleX(),getScaleY(),getRotation());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
    public void reinit(float duration){
        //this.clearActions();
        CURRENT_STATE = ACTIVE;
    }

    public void takeDamage(){
        spellCastCompletude = 0;
        this.clearActions();
        CURRENT_STATE = HURT;
        float flip = 0.05f;
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
                color(new Color(getColor().r,getColor().g,getColor().b,1),flip),
                run(new Runnable() {
                    @Override
                    public void run() {
                        CURRENT_STATE = ACTIVE;
                    }
                })
        ));
    }
    public void attack(){
        //TODO BUG IN WHICH THE MONSTER STAY HURT
        if( this.getActions().size == 2){
            this.removeAction(attackAction);
        }
        attackAction =sequence(
                scaleTo(1.2f,1.2f,-spellCastCompletude+1),
                scaleTo(0.8f,0.8f,-spellCastCompletude+1),
                run(new Runnable() {
                        @Override
                        public void run() {
                            attack();
                        }
                    }
                )
        );
        this.addAction(attackAction);
    }

    public int getDifficulty() {
        return difficulty;
    }
    public void setSpellCastCompletude(float s){
        spellCastCompletude = s;
    }
}
