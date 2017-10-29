package de.timonback.android.whatisthatplace.activity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.util.ArrayList;

import de.timonback.android.whatisthatplace.Constants;
import de.timonback.android.whatisthatplace.R;
import de.timonback.android.whatisthatplace.activity.provider.DBProvider;
import de.timonback.android.whatisthatplace.activity.provider.ImageProvider;
import de.timonback.android.whatisthatplace.component.MyCallable;
import de.timonback.android.whatisthatplace.component.database.VisionResultDbHelper;
import de.timonback.android.whatisthatplace.component.gallery.GalleryAdapter;
import de.timonback.android.whatisthatplace.component.gallery.GalleryItem;

public class GalleryFragment extends Fragment {
    public static final String LOG_NAME = GalleryFragment.class.getName();

    private DBProvider dbProvider;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);

        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.imagegallery);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),3);
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<GalleryItem> createLists = prepareData();
        GalleryAdapter adapter = new GalleryAdapter(getActivity(), createLists, new MyCallable<GalleryItem>() {
            @Override
            public void call(GalleryItem item) {
                Log.i(LOG_NAME, "Gallery Click");
                String filePath = item.getImageFile().getPath();

                //gVisionComponent.analyze(filePath, GVisionComponent.FEATURE.LANDMARK);
            }
        });
        recyclerView.setAdapter(adapter);

        return view;
    }

    private ArrayList<GalleryItem> prepareData() {
        ArrayList<GalleryItem> items = new ArrayList<>();

        for(File image: ImageProvider.getFilePaths(getActivity())) {
            items.add(new GalleryItem(image.getName(), image));
        }

        return items;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment GalleryFragment.
     */
    public static GalleryFragment newInstance(DBProvider dbProvider) {
        GalleryFragment fragment = new GalleryFragment();
        fragment.dbProvider = dbProvider;

        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }
}
