package com.education.Gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Array;
import com.education.Database.Database;
import com.education.Database.FigureDeStyle;
import com.education.UI.ContainerMonsterUI;
import com.education.UI.ContainerSpellUI;
import com.education.UI.ContainerUI;
import com.education.screens.GameScreen;


import java.text.Normalizer;

import playGround.PlayGroundGame;
import playGround.PlayGroundScreen;

import static com.education.Constants.BATTLE_THEME_SOUND;
import static com.education.Constants.DEATH_THEME_SOUND;
import static com.education.Constants.EXPLOSION_SOUND;
import static com.education.Constants.HURT_SOUND;
import static com.education.Constants.SELECT_SOUND;
import static com.education.Constants.WIN_THEME_SOUND;


public class Manager {
    private Database database;
    private ContainerMonsterUI monsterUI;
    private ContainerSpellUI spellUI;
    private ContainerUI playerUI;
    private GameScreen playScreen;
    private String currentWeakness;
    private FigureDeStyle currentFigure;
    private Sound selectSound;
    private Sound hurtSound;
    private Sound explosionSound;
    private Sound deathSound;
    private Music battleMusic;
    private Music winMusic;
    private boolean attackPhase;
    //  True Means we are in the attacking phase;
    //  False means we are in the defending phase;
    /**
     * DEFENSE PHASE :
     *    - GRAPHIC
     *      - RESET EVERYTHING
     *      - THE ORB WITH THE CORRESPONDING ELEMENT WILL CENTER ITSELF ABOVE THE MONSTER AND [SCALE UP/DOWN|GLOW/DIM]
     *      - THE OTHER ORBS WILL GO TRANSLUCENT AND SLIDE AWAY
     *      - THE MONSTER WILL [SCALE UP/DOWN|SHAKE] AS IF GATHERING POWER (MORE INTENSE AS TIME GOES ON)
     *      - THE FIGURE WILL SHOW ON THE TOP PANE
     *      - THE CLOCK WILL SHOW ON THE TOP PANE
     *      - THE OPTIONS WILL BE REGENERATED FOR THE BOTTOM PANE(Including correct response)
     *      - THE CLOCK'S HANDLES WILL BEGIN ROTATING : ONE FULL ROTATION MEANS TIME IS UP
     *    - MODEL
     *      - WHEN THE PLAYER CLICK ON AN OPTION, THEN MANAGER RECEIVE THE OPTION VALUE, COMPARE IT TO THE ACTUAL RESPONSE
     *          - GOOD RESPONSE :   - DEAL DAMAGE TO MONSTER [CHANGE MONSTER TEXTURE + SHAKING ACTION]
     *          - BAD RESPONSE :    - DEAL DAMAGE TO PLAYER     [PLAYER UI SHAKING ?]
     *          - CHECK IF THE BATTLE FINISHED : - WIN/LOSE POPUP SCREEN
     *                                           - SWITCH TO NEXT PHASE
     * ATTACK PHASE :
     *      - GRAPHIC
     *          - RESET EVERYTHING
     *          - THE ORB WITH THE CORRESPONDING ELEMENT WILL CENTER ITSELF ABOVE THE MONSTER AND [SCALE UP/DOWN|GLOW/DIM]
     *          - THE OTHER ORBS WILL GO TRANSLUCENT AND SLIDE AWAY
     *          - MONSTER [GLOW IN THE CORRESPONDING COLOR] ?
     *      - MODEL
     *          - WHEN THE PLAYER CLICK ON AN OPTION, THEN MANAGER RECEIVE THE OPTION VALUE, COMPARE IT TO THE ACTUAL RESPONSE
     *      *          - GOOD RESPONSE :   - DEAL DAMAGE TO MONSTER [CHANGE MONSTER TEXTURE + SHAKING ACTION]
     *      *          - BAD RESPONSE :    - DEAL DAMAGE TO PLAYER     [PLAYER UI SHAKING ?]
     *      *          - CHECK IF THE BATTLE FINISHED : - WIN/LOSE POPUP SCREEN
     *      *                                           - SWITCH TO NEXT PHASE
      */
    // TODO : Change with actual gameplay Screen Class when Implementing
    private PlayGroundScreen pg;
    public Manager(Database db,GameScreen pg){
        //db.saveSession(db.getFigures());
        database = db; playScreen = pg;
        selectSound = Gdx.audio.newSound(Gdx.files.internal(SELECT_SOUND));
        explosionSound  = Gdx.audio.newSound(Gdx.files.internal(EXPLOSION_SOUND));
        battleMusic = Gdx.audio.newMusic(Gdx.files.internal(BATTLE_THEME_SOUND));
        hurtSound = Gdx.audio.newSound(Gdx.files.internal(HURT_SOUND));
        deathSound = Gdx.audio.newSound(Gdx.files.internal(DEATH_THEME_SOUND));
        winMusic = Gdx.audio.newMusic(Gdx.files.internal(WIN_THEME_SOUND));
        battleMusic.setLooping(true);
        battleMusic.setVolume(0.5f);
        battleMusic.play();
    }
    public Manager(Database db,ContainerMonsterUI monsterUI,ContainerSpellUI spellUI,ContainerUI playerUI){
        database = db;
        this.monsterUI = monsterUI;
        this.spellUI = spellUI;
        this.playerUI = playerUI;
        selectSound = Gdx.audio.newSound(Gdx.files.internal(SELECT_SOUND));
        explosionSound  = Gdx.audio.newSound(Gdx.files.internal(EXPLOSION_SOUND));
        hurtSound = Gdx.audio.newSound(Gdx.files.internal(HURT_SOUND));
        deathSound = Gdx.audio.newSound(Gdx.files.internal(DEATH_THEME_SOUND));
        winMusic = Gdx.audio.newMusic(Gdx.files.internal(WIN_THEME_SOUND));
        battleMusic.play();
        battleMusic.setLooping(true);
    }
    //Managing functions
    public void setUpForFigure(FigureDeStyle fig, int adequateDifficulty){
        reinitScene();
        currentFigure = fig;
        spellUI.setFigure(fig);
        Array<FigureDeStyle> arrayFig = new Array<FigureDeStyle>();
        while(arrayFig.size < adequateDifficulty*2){
            FigureDeStyle rand = database.getRandomFigure();
            if(!rand.getType().equals(fig.getType()))
                arrayFig.add(database.getRandomFigure());

        }
        /*for (int i = 0; i < adequateDifficulty*2; i++) {
            arrayFig.add(database.getRandomFigure());
        }*/
        playerUI.setUpForFigure(fig,arrayFig);
        monsterUI.attack();
    }
    public void setUpForType(FigureDeStyle fig){
        reinitScene();
        currentFigure = fig;
        spellUI.setFigure(fig);
        monsterUI.attack(2);
        Array<FigureDeStyle> arrayFig = new Array<FigureDeStyle>();
        arrayFig.add(database.getRandomFigure());
        arrayFig.add(database.getRandomFigure());
        arrayFig.add(database.getRandomFigure());
        playerUI.setUpForType(fig,arrayFig);
    }
    public void reinitScene(){
        spellUI.reinit(5);
        monsterUI.reinit(5);
        playerUI.reinit(5);
    }

    public void switchPhase(){
        attackPhase = !attackPhase;
        reinitScene();
        setUpForFigure(database.getRandomFigure(),monsterUI.getMonsterDifficulty());
    }
    /**
     * For the Attacking phase,
     *
     * @param answer's type must be same type as monster's weakness
     *              with higher difficulty answer being more effective
     */
    public void checkAnswerFigure(FigureDeStyle answer){
        //TODO
        float damageDealt = 10;
        if(answer.getType().equals(currentWeakness)){
            //Diffuclty increases damage
            damageDealt = damageDealt * (answer.getDifficulty()+1);
            monsterUI.takeDamage();
            spellUI.takeDamage(damageDealt);
            hurtSound.play();
        }else{
            damageDealt = damageDealt * monsterUI.getMonsterDifficulty();
            playerUI.takeDamage(damageDealt);
            hurtSound.play();
        }

        switchPhase();
    }

    /**
     * For the Defense phase:
     * @param answer must be the type of the monster's spell
     */

    public void checkAnswerType(String answer){
        //TODO
        //System.out.println("Checking Answer...\n Player Answer is " + answer + "\n Expected Answer is " + currentFigure.getType());
        float damageDealt = 10;
        String nonAccAns = Normalizer.normalize(currentFigure.getType(), Normalizer.Form.NFD);
        nonAccAns= nonAccAns.replaceAll("[^\\p{ASCII}]", "");

        if(answer.equals(nonAccAns)){
            System.out.println("Correct...Dealing Damage to monster");
            //Difficulty increases damage
            damageDealt = damageDealt * (currentFigure.getDifficulty()+1);
            monsterUI.takeDamage();
            spellUI.takeDamage(damageDealt);
            hurtSound.play();
            if(currentFigure.getMastery() > 90 ){
                database.updateFigureMastery(currentFigure.getID(),100);
            }else {
                database.updateFigureMastery(currentFigure.getID(),currentFigure.getMastery()+5);
            }
        }else{
            System.out.println("Incorrect...Dealing Damage to player");
            damageDealt = damageDealt * monsterUI.getMonsterDifficulty();
            playerUI.takeDamage(damageDealt);
            hurtSound.play();
            //Add Mastery to known spells
            if(currentFigure.getMastery() < 10 ){
                System.out.println("Updated figure mastery for : " + currentFigure.getType());
                database.updateFigureMastery(currentFigure.getID(),1);
            }else {
                System.out.println("Updated figure mastery for : " + currentFigure.getType());
                database.updateFigureMastery(currentFigure.getID(),currentFigure.getMastery()-10);
            }
        }
        if(spellUI.isDead()){
            battleMusic.stop();
            winMusic.play();
            playScreen.gameOver(true);
            return;
        }else if(playerUI.isDead()){
            battleMusic.stop();
            deathSound.play();
            playScreen.gameOver(false);
        }
        switchPhase();
    }

    public void timeIsUp(){
        playerUI.takeDamage(15);
        hurtSound.play();
        if(currentFigure.getMastery() < 10 ){
            database.updateFigureMastery(currentFigure.getID(),1);
        }else {
            database.updateFigureMastery(currentFigure.getID(),currentFigure.getMastery()-10);
        }
        reinitScene();
        switchPhase();
    }

    public void setCastingCompletude(float completude){
        monsterUI.setCastCompletude(completude);
    }
    /**
     * Generate new Phase
     */
    public void playPhase(){

    }

    public void dispose(){
        battleMusic.stop();
        explosionSound.stop();
        winMusic.stop();
        deathSound.stop();
    }

    public void debug(){
        System.out.println("Debugging Manager....");
    }
    //SETTERS===================================================================================
    public void setMonsterUI(ContainerMonsterUI monsterUI) {
        this.monsterUI = monsterUI;
    }

    public void setSpellUI(ContainerSpellUI spellUI) {
        this.spellUI = spellUI;
    }

    public void setPlayerUI(ContainerUI playerUI) {
        this.playerUI = playerUI;
    }


}
