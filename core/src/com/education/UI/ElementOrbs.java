package com.education.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Group;

import java.util.Random;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

import static com.education.Constants.ELEMENTS_NUMBER;
import static com.education.Constants.ORBS;
import static com.education.Constants.ORBS_OFFSET;

public class ElementOrbs extends Group {

    private ElementOrb[] orbs;
    Vector2 orbsOrigin;
    Monster monster;
    public ElementOrbs(Monster monster){
        this.monster = monster;
        Texture orbsTexture = new Texture(Gdx.files.internal(ORBS));
        TextureRegion[][] orbsTextureRegions = TextureRegion.split(orbsTexture,16,16);

        orbs = new ElementOrb[ELEMENTS_NUMBER];
        for (int i = 0; i < orbsTextureRegions.length; i++) {
            for (int j = 0; j < orbsTextureRegions[i].length; j++) {
                orbs[orbsTextureRegions[i].length*i +j] = new ElementOrb(orbsTextureRegions[i][j]);
                this.addActor(orbs[orbsTextureRegions[i].length*i +j]);
            }
        }
        orbsOrigin = new Vector2(monster.getX(),monster.getY());
        moveOrbs2Pos();
    }

    public void moveOrbs2Pos(){
        Random rand = new Random();
        Vector2 point =  new Vector2(getX(),getY());
        point.x = point.x - monster.getWidth()/2*(ELEMENTS_NUMBER/2);
        point.y = point.y + monster.getHeight();
        for (ElementOrb orb:orbs) {
            orb.setY(point.y);
            orb.setX(point.x);
            //System.out.println("Y set to : " + point.x + " and getY() = " + orb.getX());
            float tmp = rand.nextFloat()+0.5f * 4;
            float sign = (rand.nextInt(2) == 0) ? 1 : -1;

            //System.out.println(tmp);
            orb.addAction(sequence(
                    moveTo(point.x,point.y,2),
                    forever(
                            sequence(
                                    moveTo(orb.getX(),orb.getY() + sign * monster.getHeight()/3,tmp),
                                    moveTo(orb.getX(),orb.getY() - sign *monster.getHeight()/3,tmp)

                            )
                    )
            ));
            point.x = point.x + monster.getWidth()/2;
        }
    }

    public void moveOrbs2PosMath(){
        float maxSize = (monster.getHeight()>monster.getWidth()) ? monster.getHeight() : monster.getWidth();
        Vector2 point = new Vector2(orbsOrigin.x - (maxSize+maxSize*ORBS_OFFSET),orbsOrigin.y);
        point.set(point.x - orbsOrigin.x,point.y - orbsOrigin.y);
        float angle = -180/(ELEMENTS_NUMBER-1);
        for (int i = 0; i < orbs.length ; i++) {
            orbs[i].setX(point.x + orbsOrigin.x);
            orbs[i].setX(point.y+orbsOrigin.y);
            point.set(point.x * MathUtils.cosDeg(angle) - point.y*MathUtils.sinDeg(angle),
                    point.y * MathUtils.cosDeg(angle) + point.x*MathUtils.sinDeg(angle));
        }

    }
    @Override
    public void act(float delta) {
        super.act(delta);
    }
    public void reinit(float duration){
        for(ElementOrb orb : orbs){
            orb.reinit(3);
        }
        //moveOrbs2Pos();
    }
}