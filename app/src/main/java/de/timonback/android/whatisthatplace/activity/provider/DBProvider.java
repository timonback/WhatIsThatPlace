package de.timonback.android.whatisthatplace.activity.provider;

import de.timonback.android.whatisthatplace.component.database.VisionResultDbHelper;

public interface DBProvider {
    VisionResultDbHelper getDB();
}
