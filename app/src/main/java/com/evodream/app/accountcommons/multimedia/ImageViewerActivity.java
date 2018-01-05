package com.evodream.app.accountcommons.multimedia;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alexvasilkov.gestures.Settings;
import com.alexvasilkov.gestures.views.GestureImageView;
import com.evodream.app.accountcommons.R;
import com.evodream.app.accountcommons.util.AndroidUtil;
import com.evodream.app.accountcommons.util.DataUtil;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

public class ImageViewerActivity extends AppCompatActivity {

    private static final String BUNDLE_URI = "BUNDLE_URI";
    private static final String BUNDLE_ROTATION = "BUNDLE_ROTATION";
    private static final String BUNDLE_TITLE = "BUNDLE_TITLE";
    private static final String BUNDLE_DESC = "BUNDLE_DESC";

    ProgressBar vProgressBar;
    GestureImageView gestureImageView;
    TextView vImageName, vImageDesc;

    public static Intent getStartIntent(Context context, Uri uri) {
        Intent intent = new Intent(context, ImageViewerActivity.class);
        intent.putExtra(BUNDLE_URI, uri);
        return intent;
    }

    public static Intent getStartIntent(Context context, Uri uri, float rotation) {
        Intent intent = new Intent(context, ImageViewerActivity.class);
        intent.putExtra(BUNDLE_URI, uri);
        intent.putExtra(BUNDLE_ROTATION, rotation);
        return intent;
    }

    public static Intent getStartIntent(Context context, Uri uri, float rotation, String title, String description) {
        Intent intent = new Intent(context, ImageViewerActivity.class);
        intent.putExtra(BUNDLE_URI, uri);
        intent.putExtra(BUNDLE_ROTATION, rotation);
        intent.putExtra(BUNDLE_TITLE, title);
        intent.putExtra(BUNDLE_DESC, description);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowTitleEnabled(false);
            supportActionBar.setElevation(0f);
            supportActionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white);
        }

        vProgressBar = (ProgressBar) findViewById(R.id.progress_image_loading);
        vProgressBar.setVisibility(View.GONE);

        vImageName = (TextView) findViewById(R.id.tv_image_name);
        vImageDesc = (TextView) findViewById(R.id.tv_image_desc);

        gestureImageView = (GestureImageView) findViewById(R.id.gesture_image_view);
        gestureImageView.getController().getSettings()
                .setMaxZoom(2f)
                .setDoubleTapZoom(-1f) // Falls back to max zoom level
                .setPanEnabled(true)
                .setZoomEnabled(true)
                .setDoubleTapEnabled(true)
                .setRotationEnabled(false)
                .setRestrictRotation(false)
                .setOverscrollDistance(0f, 0f)
                .setOverzoomFactor(2f)
                .setFillViewport(true)
                .setFitMethod(Settings.Fit.INSIDE)
                .setGravity(Gravity.CENTER);

        if (getIntent().hasExtra(BUNDLE_URI)) {
            RequestCreator load;

            Uri uri = getIntent().getParcelableExtra(BUNDLE_URI);
            load = Picasso.get().load(DataUtil.normalize(uri));
            load.resize(1024, 0);

            if (getIntent().hasExtra(BUNDLE_ROTATION)) {
                float rotation = getIntent().getFloatExtra(BUNDLE_ROTATION, 0f);
                load.rotate(rotation);
            }

            vProgressBar.setVisibility(View.VISIBLE);
            load.into(gestureImageView, new Callback() {
                @Override
                public void onSuccess() {
                    vProgressBar.setVisibility(View.GONE);
                }

                @Override
                public void onError(Exception e) {
                    vProgressBar.setVisibility(View.GONE);
                    if (AndroidUtil.isDebuggable(ImageViewerActivity.this)) AndroidUtil.showErrorMessage(ImageViewerActivity.this, e);
                    Toast.makeText(ImageViewerActivity.this, getString(R.string.message_image_load_failed), Toast.LENGTH_SHORT).show();
                }
            });
        }

        if (getIntent().hasExtra(BUNDLE_TITLE)) {
            vImageName.setText(getIntent().getStringExtra(BUNDLE_TITLE));
        }
        if (getIntent().hasExtra(BUNDLE_DESC)) {
            vImageDesc.setText(getIntent().getStringExtra(BUNDLE_DESC));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
