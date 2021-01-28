package com.education.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.education.mainGame.FigureSortGame;
import com.education.screens.GameScreen;
import com.education.screens.MainMenuScreen;
import com.education.screens.SpellBookScreen;
import com.education.screens.StatScreen;

public class CustomButton extends Actor {
    TextureRegion region;
    final int screen;
    final FigureSortGame game;

    public CustomButton(String path, float scale, final FigureSortGame game, final int screen) {
        this.game = game;
        this.screen = screen;
        region = new TextureRegion(new Texture(Gdx.files.internal(path)));
        setBounds(region.getRegionX(), region.getRegionY(),
                region.getRegionWidth() * scale, region.getRegionHeight() * scale);

        this.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("down");
                if(screen == 1) {
                    game.mainMenuMusic.stop();
                    game.setScreen(new GameScreen(game,game.db));
                    setVisible(false);
                } else if(screen == 2) {
                    game.setScreen(new StatScreen(game));
                    setVisible(false);
                } else if(screen == 3){
                    game.setScreen(new SpellBookScreen(game));
                    setVisible(false);
                } else {
                    game.setScreen(new MainMenuScreen(game));
                    setVisible(false);
                }
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("up");
            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(region, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(),
                getScaleX(), getScaleY(), getRotation());
    }



}
