package com.education.Database;

public class FigureDeStyle {

    private int ID;
    private String type;
    private String sentence;
    private String author;
    private int difficulty;
    private int mastery;

    public FigureDeStyle() {
        ID = 0;
        type = "";
        sentence = "";
        author = "";
        difficulty = 0;
        mastery = 1;
    }

    public FigureDeStyle(String type, String sentence, int difficulty, int mastery) {
        this.type = type;
        this.sentence = sentence;
        this.difficulty = difficulty;
        this.mastery = mastery;
    }


    public void setID(int ID) { this.ID = ID; }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public void setAuthor(String author) { this.author = author;}

    public void setType(String type) {
        this.type = type;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public void setMastery(int mastery) {
        this.mastery = mastery;
    }

    public int getID() {
        return ID;
    }

    public String getSentence() {
        return sentence;
    }

    public String getAuthor() { return author; }

    public String getType() {
        return type;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public int getMastery() {
        return mastery;
    }

    @Override
    public String toString() {
        return "Figure ID = "+ getID() + "\n Figure Type = " + getType() + "\n Mastery = " + getMastery();
    }
}
