package de.timonback.android.whatisthatplace.service.api;


public interface ApiCallable
{
    interface Success<Response>
    {
        void call(Response param);
    }

    interface Failure
    {
        void call(final String errorMessage);
    }
}