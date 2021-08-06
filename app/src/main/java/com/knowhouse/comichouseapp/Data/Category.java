package com.knowhouse.comichouseapp.Data;

public class Category {
    private Comics preview;
    private ComicSeries series;
    private Creators creators;

    public Category(Comics preview,ComicSeries series,Creators creators){
        this.preview = preview;
        this.series = series;
        this.creators = creators;
    }

    public Comics getPreview() {
        return preview;
    }

    public ComicSeries getSeries() {
        return series;
    }

    public Creators getCreators() {
        return creators;
    }
}
