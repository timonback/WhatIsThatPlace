package de.timonback.android.whatisthatplace.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import de.timonback.android.whatisthatplace.R;
import de.timonback.android.whatisthatplace.component.CameraComponent;

public class MainActivity extends AppCompatActivity implements GalleryFragment.OnChangeFragmentListener {
    private static final String LOG_NAME = MainActivity.class.getName();
    private CameraComponent cameraComponent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cameraComponent = new CameraComponent();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraComponent.takePicture(MainActivity.this);
            }
        });
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }*/

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
    public void onFragmentChange(String imagePath) {
        VisionFragment visionFrag = (VisionFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_vision);

        if (visionFrag != null) {
            // We're in two-pane layout...
            if(imagePath == null) {
                getSupportFragmentManager().beginTransaction().detach(visionFrag);
            }

            // Call a method in the ArticleFragment to update its content
            visionFrag.updateImage(imagePath);
        } else {
            // Otherwise, we're in the one-pane layout and must swap frags...

            if(imagePath != null) {
                // Create fragment and give it an argument for the selected article
                VisionFragment newFragment = new VisionFragment();
                Bundle args = new Bundle();
                args.putString(VisionFragment.ARG_IMAGE_PATH, imagePath);
                newFragment.setArguments(args);

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack so the user can navigate back
                transaction.replace(R.id.fragment_vision, newFragment);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }
        }
    }
}
