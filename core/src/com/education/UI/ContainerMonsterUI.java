package com.education.UI;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import static com.education.Constants.MONSTER_ATTACK_SPEED;

public class ContainerMonsterUI extends Table {
    private Monster monster;
    private ElementOrbs orbs;
    public ContainerMonsterUI(){
        super();
        this.center();
        monster = new Monster();
        orbs = new ElementOrbs(monster);
        this.add(monster);
        this.row();
        this.add(orbs);
    }
    public void setMonster(Monster m){
        monster = m;
    }
    public void attack(float duration){
        //TODO FINISH IT
    }

    public void reinit(float duration){
        //monster.reinit(duration);
        //orbs.reinit(duration);
    }

    public void takeDamage(){
        monster.takeDamage();
    }
    public int getMonsterDifficulty(){
        return monster.getDifficulty();
    }
    public void attack(){
        monster.attack();
    }
    public void setCastCompletude(float spellComp){
        monster.setSpellCastCompletude(spellComp);
    }
}