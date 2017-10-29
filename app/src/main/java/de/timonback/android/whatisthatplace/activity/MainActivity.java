package de.timonback.android.whatisthatplace.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;


import java.io.File;
import java.util.ArrayList;

import de.timonback.android.whatisthatplace.Constants;
import de.timonback.android.whatisthatplace.R;
import de.timonback.android.whatisthatplace.activity.provider.DBProvider;
import de.timonback.android.whatisthatplace.component.CameraComponent;
import de.timonback.android.whatisthatplace.component.MyCallable;
import de.timonback.android.whatisthatplace.component.database.VisionResultDbHelper;
import de.timonback.android.whatisthatplace.component.gallery.GalleryAdapter;
import de.timonback.android.whatisthatplace.component.gallery.GalleryItem;

public class MainActivity extends AppCompatActivity implements DBProvider {
    private static final String LOG_NAME = MainActivity.class.getName();

    private VisionResultDbHelper gVisionDb = null;
    private CameraComponent cameraComponent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gVisionDb = new VisionResultDbHelper(this);
        cameraComponent = new CameraComponent();
        //gVisionComponent = new GVisionComponent();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            GalleryFragment gallyerFragment = GalleryFragment.newInstance(this);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, gallyerFragment).commit();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraComponent.takePicture(MainActivity.this);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        gVisionDb.close();
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public VisionResultDbHelper getDB() {
        return gVisionDb;
    }
}
