package de.timonback.android.whatisthatplace.model.knowledge;

import java.util.List;

public class KnowledgeResult {
    private List<ItemListElement> itemListElement;

    public List<ItemListElement> getItemListElement() {
        return itemListElement;
    }

    @Override
    public String toString() {
        return "KnowledgeResult [itemListElement = " + itemListElement.toString() + "]";
    }
}

