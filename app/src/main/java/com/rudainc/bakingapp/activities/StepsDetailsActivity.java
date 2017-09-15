package com.rudainc.bakingapp.activities;

import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.rudainc.bakingapp.R;
import com.rudainc.bakingapp.models.Step;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsDetailsActivity extends BaseActivity {

    private static final String VIDEO_URL = "url";
    private static final String VIDEO_POSITION = "video_position";

    @BindView(R.id.playerView)
    SimpleExoPlayerView mPlayerView;

    @BindView(R.id.step_description)
    TextView mStepDescription;

    private SimpleExoPlayer mExoPlayer;

    private String url = "";
    private long position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_step_details);
        } else {
            setContentView(R.layout.activity_step_details);

        }
        ButterKnife.bind(this);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            mStepDescription.setVisibility(View.GONE);

        mPlayerView.setDefaultArtwork(BitmapFactory.decodeResource
                (getResources(), R.drawable.no_video));

        Step step = (Step) getIntent().getParcelableExtra(STEPS);
        if (step != null) {
            getSupportActionBar().setTitle(getIntent().getStringExtra(RECIPE_NAME));
            mStepDescription.setText(step.getDescription());

            if (!step.getVideoURL().isEmpty()) {
                url = step.getVideoURL();
                initializePlayer(Uri.parse(url));
            } else if (!step.getThumbnailURL().isEmpty()) {
                url = step.getVideoURL();
                initializePlayer(Uri.parse(url));
            } else {
                url = "";
                mStepDescription.setVisibility(View.VISIBLE);
            }
        }


        if (savedInstanceState != null) {
           initializePlayer(Uri.parse(savedInstanceState.getString(VIDEO_URL)));
            position = savedInstanceState.getLong(VIDEO_POSITION);
            mExoPlayer.seekTo((int)position);
        }

    }



    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);
            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(this, "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    this, userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }


    /**
     * Release ExoPlayer.
     */
    private void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mExoPlayer != null) {
            position = mExoPlayer.getCurrentPosition();
            releasePlayer();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(VIDEO_URL, url);
        outState.putLong(VIDEO_POSITION,position);
    }
}
