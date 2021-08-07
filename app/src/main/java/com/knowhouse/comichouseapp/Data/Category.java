package com.knowhouse.comichouseapp.Data;

public class Category {
    private Comics preview;
    private ComicSeries series;
    private Characters characters;

    public Category(Comics preview, ComicSeries series, Characters characters){
        this.preview = preview;
        this.series = series;
        this.characters = characters;
    }

    public Comics getPreview() {
        return preview;
    }

    public ComicSeries getSeries() {
        return series;
    }

    public Characters getCharacters() {
        return characters;
    }
}
