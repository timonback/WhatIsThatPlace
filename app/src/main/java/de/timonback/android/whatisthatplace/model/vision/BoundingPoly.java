package de.timonback.android.whatisthatplace.model.vision;

import java.util.List;

public class BoundingPoly {
    private List<Vertices> vertices;

    public List<Vertices> getVertices() {
        return vertices;
    }

    @Override
    public String toString() {
        return "BoundingPoly [vertices = " + vertices.toString() + "]";
    }
}
