package de.timonback.android.whatisthatplace.service;


import android.content.Context;

public class ServiceProvider {

    private static VisionService _visionService;

    private ServiceProvider()
    {
    }

    public static synchronized VisionService getVisionService(Context context) {
        if(_visionService == null) {
            _visionService = new VisionService(context);
        }
        return _visionService;
    }
}
