package de.timonback.android.whatisthatplace.model.knowledge;

public class Result {
    private String description;
    private String name;
    private DetailedDescription detailedDescription;
    private Image image;

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public DetailedDescription getDetailedDescription() {
        return detailedDescription;
    }

    public Image getImage() {
        return image;
    }

    @Override
    public String toString() {
        return "Result [description = " + description + ", name = " + name + ", detailedDescription = " + detailedDescription.toString() + ", image = " + image.toString() + "]";
    }
}
