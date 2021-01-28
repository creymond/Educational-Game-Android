package playGround;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.education.Database.Database;

import static com.education.Constants.SCALE;


public class PlayGroundGame extends Game {

    public SpriteBatch batch;
    public BitmapFont font;
    public Database db;
    public void create() {
        batch = new SpriteBatch();
        //Use LibGDX's default Arial font.
        font = new BitmapFont();
        font.getData().setScale(SCALE-1);
        font.setColor(Color.BLACK);
        db = new Database();
        db.initDatabase();
        this.setScreen(new PlayGroundScreen(this,db));
    }

    public void render() {
        super.render(); //important!
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
    }

}
