package de.timonback.android.whatisthatplace.service;


import android.content.Context;

public class ServiceProvider {

    private static KnowledgeService _knowledgeService;
    private static VisionService _visionService;

    private ServiceProvider()
    {
    }

    public static synchronized KnowledgeService getKnowledgeService(Context context) {
        if (_knowledgeService == null) {
            _knowledgeService = new KnowledgeService(context);
        }
        return _knowledgeService;
    }

    public static synchronized VisionService getVisionService(Context context) {
        if(_visionService == null) {
            _visionService = new VisionService(context);
        }
        return _visionService;
    }
}
