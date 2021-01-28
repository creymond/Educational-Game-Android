package com.education.Database;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class Database {

    private ArrayList<FigureDeStyle> figures;
    private ArrayList<ExplanationFigure> explanationFigures;

    private FileHandle fileFigures;
    private FileHandle fileExplanation;
    private FileHandle localSave;
    private Json json;

    public Database() {
        figures = new ArrayList<FigureDeStyle>();
        json = new Json();
    }

    /**
     * Initially get data from JSON file.
     * Save it as Array<FigureDeStyle>
     */
    public void initDatabase() {
        //figures = new ArrayList<FigureDeStyle>();
        String pathLocal = "/data/user/0/com.logiedu.speechfigures/files/session.json";
        if(Gdx.files.local(pathLocal).exists()) {
            fileFigures = Gdx.files.local(pathLocal);

        }
         else {fileFigures = Gdx.files.internal("JSONFigures/dataFigures.json");
        }
        fileExplanation = Gdx.files.internal("JSONFigures/dataExplanation.json");
        localSave = Gdx.files.local(pathLocal);
        System.out.println("FILE EMPTY  : " + localSave.readString());

        figures = json.fromJson(ArrayList.class, FigureDeStyle.class, fileFigures);


        explanationFigures = json.fromJson(ArrayList.class, ExplanationFigure.class, fileExplanation);
        System.out.println(json.prettyPrint(figures));
        System.out.println("ARRAY FIGURES" + figures);
    }

    /**
     * Save the session progression to JSON file.
     * @param figures
     */
    public void saveSession(ArrayList<FigureDeStyle> figures) {
        json.setOutputType(JsonWriter.OutputType.json);

        json.toJson(figures,ArrayList.class,FigureDeStyle.class,localSave);
        //System.out.println(json.prettyPrint(figures));
        System.out.println("Session saved.");
        initDatabase();
    }

    public void resetMastery() {
        // TODOO
    }


    /**
     * Total number of figures available in database.
     */
    public int numberOfFigures() {
        return figures.size();
    }

    /**
     *  Search for every figures with same type.
     * @param f type enum of figure de style
     * @return list of same figures type
     */
    public ArrayList<FigureDeStyle> getFiguresWithSameType(TypeFigure f) {
        String fig = f.getValue();
        ArrayList<FigureDeStyle> figList = new ArrayList<FigureDeStyle>();
        for(FigureDeStyle fds : figures) {
            if(fds.getType().equals(fig)) {
                figList.add(fds);
            }
        }
        return figList;
    }

    /**
     * Select randomly a figure of any type.
     * @return figure de style randomly picked
     */
    public FigureDeStyle getRandomFigure() {
        FigureDeStyle fig;
        int index = (int)(Math.random() * (figures.size()));
        fig = figures.get(index);
        return fig;
    }

    /**
     * Search for every figures for the given difficulty.
     * @param difficulty value authorized are 1,2,3. 3 is the highest difficulty
     * @return list of same figures difficulty
     */
    public ArrayList<FigureDeStyle> getFiguresByDifficulty(int difficulty) {
        ArrayList<FigureDeStyle> figList = new ArrayList<FigureDeStyle>();
        for(FigureDeStyle fds : figures) {
            if(fds.getDifficulty() == difficulty) {
                figList.add(fds);
            }
        }
        return figList;
    }

    /**
     * Update the actual progress for the given figure found by ID.
     * @param ID unique identifiant of the figure
     * @param mastery value between 0 and 100. 100 means the figure is well known
     */
    public void updateFigureMastery(int ID, int mastery) {
        for(int i = 0; i < figures.size(); i++) {
            if(figures.get(i).getID() == ID) {
                FigureDeStyle f = figures.get(i);
                f.setMastery(mastery);
                figures.set(i,f);
                break;
            }
        }
    }

    /**
     * Obtain the whole list of figures for current session.
     * @return list of figures
     */
    public ArrayList<FigureDeStyle> getFigures() {
        return figures;
    }

    /**
     * Obtain the whole list of explanation depending on figure type for current session.
     * @return list of explanation
     */
    public ArrayList<ExplanationFigure> getExplanationFigures(){
        return explanationFigures;
    }

    /**
     * Obtain explanation for the given figure type.
     * @param type from enum TypeFigure
     * @return string of explanation
     */
    public String getExplanationForType(TypeFigure type) {
        String str = "";
        for(ExplanationFigure exf : explanationFigures) {
            if(exf.getType().equals(type.getValue())) {
                str = exf.getExplanation();
            }
        }
        return str;
    }

    /**
     * Obtain explanation for the given type as string.
     * @param type string
     * @return string of explanation
     */
    public String getExplanationForType(String type) {
        String str = "";
        String c = type.substring(0, 1).toLowerCase() + type.substring(1);
        for(ExplanationFigure exf : explanationFigures) {
            if(exf.getType().equals(c)) {
                str = exf.getExplanation();
            }
        }
        return str;
    }

    /**
     * Obtain example for the given figure type.
     * @param type from enum TypeFigure
     * @return string of example
     */
    public String getExampleForType(TypeFigure type) {
        String str = "";
        for(ExplanationFigure exf : explanationFigures) {
            if(exf.getType().equals(type.getValue())) {
                str = exf.getExample();
                break;
            }
        }
        return str;
    }

    /**
     * Obtain array of figures type as string for each figures with mastery not equal 0.
     * @return array of string figure type
     */
    public Array<String> getFigureNameMastery() {
        Array<String> figNames = new Array<String>();
        for(FigureDeStyle fds : figures) {
            if(fds.getMastery() > 0) {
                if(!figNames.contains(fds.getType(),false)) {
                    figNames.add(fds.getType());
                }
            }
        }
        return figNames;
    }

    public int getNbFiguresSameType(String type) {
        int nb = 0;
        for(FigureDeStyle fds : figures) {
            if(fds.getType().equals(type)) {
                nb++;
            }
        }
        return nb;
    }

    public int getSumMasterySameType(String type) {
        int sum = 0;
        for(FigureDeStyle fds : figures) {
            if(fds.getType().equals(type)) {
                sum += fds.getMastery();
            }
        }
        return sum;
    }

    public int computeMasteryAverage(String type) {
        int nb = getNbFiguresSameType(type);
        int sum = getSumMasterySameType(type);
        int average = sum/nb;
        return average;
    }
    public int getAdequateDifficulty(){
        return 2;
    }
}
