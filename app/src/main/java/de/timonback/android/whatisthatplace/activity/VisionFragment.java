package de.timonback.android.whatisthatplace.activity;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.io.File;

import de.timonback.android.whatisthatplace.R;
import de.timonback.android.whatisthatplace.model.knowledge.KnowledgeResult;
import de.timonback.android.whatisthatplace.model.vision.Landmark;
import de.timonback.android.whatisthatplace.model.vision.Location;
import de.timonback.android.whatisthatplace.model.vision.VisionResult;
import de.timonback.android.whatisthatplace.service.ServiceProvider;
import de.timonback.android.whatisthatplace.util.MyParamCallable;


public class VisionFragment extends Fragment implements OnMapReadyCallback {
    private final static String LOG_NAME = VisionFragment.class.getName();

    public final static String ARG_IMAGE_PATH = "imagePath";

    private String imagePath;
    private GoogleMap map = null;

    public VisionFragment() {
    }

    public static VisionFragment newInstance(String imagePath) {
        VisionFragment fragment = new VisionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_IMAGE_PATH, imagePath);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            imagePath = getArguments().getString(ARG_IMAGE_PATH);
        } else {
            getActivity().onBackPressed();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_vision, container, false);
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        updateView();
    }

    public void updateImage(String path) {
        imagePath = path;

        updateView();
    }

    private void updateView() {
        final Context context = getContext();
        final File file = new File(imagePath);

        ImageView imageView = getActivity().findViewById(R.id.visionImage);
        Picasso.with(context).load(file).fit().centerInside().into(imageView);

        ServiceProvider.getVisionService(context).analyse(Uri.fromFile(file), new MyParamCallable<VisionResult>() {
            @Override
            public void call(VisionResult param) {
                if (param.getLandmarks().isEmpty()) {
                    Toast.makeText(context, "no landmark identified", Toast.LENGTH_SHORT).show();
                    return;
                }

                Landmark landmark = param.getLandmarks().get(0);
                if(!landmark.getLocations().isEmpty() && map != null) {
                    Location location = landmark.getLocations().get(0);

                    LatLng landmarkPosition = new LatLng(location.getLatitude(), location.getLongitude());
                    map.moveCamera(CameraUpdateFactory.zoomTo(15));
                    map.moveCamera(CameraUpdateFactory.newLatLng(landmarkPosition));
                    map.addMarker(new MarkerOptions().position(landmarkPosition));
                }

                String mid = landmark.getMid();
                ServiceProvider.getKnowledgeService(context).getKnowledgeInfo(mid, new MyParamCallable<KnowledgeResult>() {
                    @Override
                    public void call(KnowledgeResult param) {
                        if (param.getItemListElement().isEmpty()) {
                            Toast.makeText(context, "Google seems to not know this place...", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        String name = param.getItemListElement().get(0).getResult().getName();
                        String description = param.getItemListElement().get(0).getResult().getDescription();
                        Toast.makeText(context, "This is " + name, Toast.LENGTH_SHORT).show();
                        Toast.makeText(context, description, Toast.LENGTH_SHORT).show();


                        TextView nameView = getActivity().findViewById(R.id.visionName);
                        nameView.setText(name);
                        TextView descriptionView = getActivity().findViewById(R.id.visionDescription);
                        descriptionView.setText(description);
                    }
                });
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.getUiSettings().setZoomControlsEnabled(true);
    }
}
