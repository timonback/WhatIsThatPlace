package de.timonback.android.whatisthatplace.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.common.collect.ListMultimap;
import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.Ordering;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import de.timonback.android.whatisthatplace.R;
import de.timonback.android.whatisthatplace.activity.provider.ImageProvider;
import de.timonback.android.whatisthatplace.component.gallery.GalleryItem;
import de.timonback.android.whatisthatplace.component.gallery.GallerySection;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

public class GalleryFragment extends Fragment implements GallerySection.OnGalleryItemClickListener {
    public interface OnChangeFragmentListener {
        void onFragmentChange(String path);
    }

    public static final String LOG_NAME = GalleryFragment.class.getName();

    private OnChangeFragmentListener contextCallback;
    private SectionedRecyclerViewAdapter sectionAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);

        sectionAdapter = new SectionedRecyclerViewAdapter();

        prepareData();

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.imagegallery);

        GridLayoutManager glm = new GridLayoutManager(getContext(), 3);
        glm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (sectionAdapter.getSectionItemViewType(position)) {
                    case SectionedRecyclerViewAdapter.VIEW_TYPE_HEADER:
                        return 3;
                    default:
                        return 1;
                }
            }
        });
        recyclerView.setLayoutManager(glm);
        recyclerView.setAdapter(sectionAdapter);

        return view;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment GalleryFragment.
     */
    public static GalleryFragment newInstance() {
        GalleryFragment fragment = new GalleryFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            contextCallback = (OnChangeFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnChangeFragmentListener");
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void prepareData() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

        ListMultimap<String, File> treeListMultimap =
                MultimapBuilder.treeKeys(Ordering.natural().reverse()).arrayListValues().build();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -1);
        Date modifiedOneYearAgo = calendar.getTime();

        for (File image : ImageProvider.getFilePaths(getActivity())) {
            if (new Date(image.lastModified()).after(modifiedOneYearAgo)) {
                treeListMultimap.put(sdf.format(image.lastModified()), image);
            }
        }

        for (Map.Entry<String, Collection<File>> entry : treeListMultimap.asMap().entrySet()) {
            List<GalleryItem> galleryItems = new ArrayList<>();
            for (File galleryFile : entry.getValue()) {
                galleryItems.add(new GalleryItem(galleryFile.getName(), galleryFile));
            }

            GallerySection section = new GallerySection(getActivity(), entry.getKey(), galleryItems, this);
            sectionAdapter.addSection(section);
        }
    }

    @Override
    public void clicked(GalleryItem item) {
        final File file = item.getImageFile();

        contextCallback.onFragmentChange(file.getPath());
    }

    @Override
    public void notifyDataSetChanged() {
        sectionAdapter.notifyDataSetChanged();
    }
}
