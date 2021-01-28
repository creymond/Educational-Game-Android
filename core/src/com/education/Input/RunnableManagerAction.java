package com.education.Input;

import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.education.Gameplay.Manager;

public class RunnableManagerAction extends RunnableAction {
    private Manager m;
    public RunnableManagerAction(Manager m){
        this.m = m;
    }
}
