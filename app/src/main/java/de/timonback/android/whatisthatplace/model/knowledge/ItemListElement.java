package de.timonback.android.whatisthatplace.model.knowledge;

public class ItemListElement {
    private Result result;
    private String resultScore;

    public Result getResult() {
        return result;
    }

    public String getResultScore() {
        return resultScore;
    }

    @Override
    public String toString() {
        return "ItemListElement [result = " + result.toString() + ", resultScore = " + resultScore + "]";
    }
}
