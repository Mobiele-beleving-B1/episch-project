package com.example.sprooktochtapp;

/**
 * Data Class
 * stores information on each FairyTale
 */
public class FairyTale {
    private String nameOfTale;
    private String[] gameNames;
    private int imageOfTaleId;
    private String nameOfLand;
    private String gameDescription;
    private String taleDescription;

    /**
     * creates an object of FairyTale with
     * @param nameOfTale : is what FairyTale is called
     * @param gameNames : names of the games at the location
     * @param imageOfTaleId : id of image resource
     * @param nameOfLand : name of land of origin
     * @param gameDescription : describes game situation
     * @param taleDescription : description of the FairyTale, also describe game issue to user
     */
    public FairyTale(String nameOfTale, int imageOfTaleId,String[] gameNames, String nameOfLand, String gameDescription, String taleDescription) {
        this.nameOfTale = nameOfTale;
        this.gameNames = gameNames;
        this.imageOfTaleId = imageOfTaleId;
        this.nameOfLand = nameOfLand;
        this.gameDescription = gameDescription;
        this.taleDescription = taleDescription;
    }

    public String getNameOfTale() {
        return nameOfTale;
    }

    public int getImageOfTaleId() {
        return imageOfTaleId;
    }

    public String getNameOfLand() {
        return nameOfLand;
    }

    public String getGameDescription() {
        return gameDescription;
    }

    public String[] getGameNames(){return gameNames;}

    public String getTaleDescription() {
        return taleDescription;
    }

    @Override
    public String toString() {
        return this.nameOfTale;
    }
}
