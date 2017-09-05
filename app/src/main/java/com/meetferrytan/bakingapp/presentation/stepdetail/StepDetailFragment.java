package com.meetferrytan.bakingapp.presentation.stepdetail;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.meetferrytan.bakingapp.BakingApplication;
import com.meetferrytan.bakingapp.R;
import com.meetferrytan.bakingapp.data.component.DaggerActivityInjectorComponent;
import com.meetferrytan.bakingapp.data.entity.Step;
import com.meetferrytan.bakingapp.presentation.base.BaseFragment;

import butterknife.BindView;

public class StepDetailFragment extends BaseFragment<StepDetailPresenter>
        implements StepDetailContract.View,
        Player.EventListener {

    public static final String TAG = StepDetailFragment.class.getSimpleName();
    public static final String KEY_POSITION = "position";
    public static final String KEY_STEP = "step";
    @BindView(R.id.playerView)
    SimpleExoPlayerView playerView;
    @BindView(R.id.txvDescription)
    TextView txvDescription;
    @BindView(R.id.imgNoVideo)
    ImageView imgNoVideo;

    private SimpleExoPlayer mExoPlayer;
    private static MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;
    private long lastPlayedPosition = 0;
    private Step mStep;

    public static StepDetailFragment newInstance(Step step) {

        Bundle args = new Bundle();
        args.putParcelable(KEY_STEP, step);
        StepDetailFragment fragment = new StepDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initComponent() {
        mComponent = DaggerActivityInjectorComponent.builder()
                .appComponent(BakingApplication.getAppComponent())
                .build();
        mComponent.inject(this);
    }

    @Override
    protected View createLayout(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_step_detail, container, false);
    }

    @Override
    public void processArguments(Bundle args) {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void startingUpFragment(View view, Bundle savedInstanceState) {
        playerView.setDefaultArtwork(BitmapFactory.decodeResource
                (getResources(), R.drawable.img_recipe));


        if (savedInstanceState != null) {
            lastPlayedPosition = savedInstanceState.getLong(KEY_POSITION);
            mStep = savedInstanceState.getParcelable(KEY_STEP);
            Log.d(TAG, "startingUpFragment: saved");
            Log.d(TAG, "startingUpFragment: pos - " + lastPlayedPosition);
            Log.d(TAG, "startingUpFragment: step - " + mStep.toString());
        }else{
            mStep = getArguments().getParcelable(KEY_STEP);
            Log.d(TAG, "startingUpFragment: new");
            Log.d(TAG, "startingUpFragment: pos - " + lastPlayedPosition);
            Log.d(TAG, "startingUpFragment: step - " + mStep.toString());
        }

        // Initialize the Media Session.
        initializeMediaSession();

        updateStepData(mStep);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        releasePlayer();
        mMediaSession.setActive(false);
    }

    @Override
    public void showError(int processCode, int errorCode, String message) {

    }

    @Override
    public void showLoading(int processCode, boolean show) {

    }

    @Override
    public void displayData(String videoUrl, String description) {
        txvDescription.setText(description);
        initializePlayer(videoUrl);
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    /**
     * Method that is called when the ExoPlayer state changes. Used to update the MediaSession
     * PlayBackState to keep in sync, and post the media notification.
     *
     * @param playWhenReady true if ExoPlayer is playing, false if it's paused.
     * @param playbackState int describing the state of ExoPlayer. Can be STATE_READY, STATE_IDLE,
     *                      STATE_BUFFERING, or STATE_ENDED.
     */
    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if ((playbackState == Player.STATE_READY) && playWhenReady) {
            mStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                    mExoPlayer.getCurrentPosition(), 1f);
        } else if ((playbackState == Player.STATE_READY)) {
            mStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                    mExoPlayer.getCurrentPosition(), 1f);
        }
        mMediaSession.setPlaybackState(mStateBuilder.build());
    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    /**
     * Initialize ExoPlayer.
     *
     * @param videoUrl The String url of the sample to play.
     */
    private void initializePlayer(String videoUrl) {
        if (videoUrl.equals("")) {
            imgNoVideo.setVisibility(View.VISIBLE);
        } else {
            imgNoVideo.setVisibility(View.GONE);
            if (mExoPlayer == null) {
                Uri mediaUri = Uri.parse(videoUrl);
                // Create an instance of the ExoPlayer.
                TrackSelector trackSelector = new DefaultTrackSelector();
                LoadControl loadControl = new DefaultLoadControl();
                RenderersFactory renderersFactory = new DefaultRenderersFactory(getActivity());
                mExoPlayer = ExoPlayerFactory.newSimpleInstance(renderersFactory, trackSelector, loadControl);
                playerView.setPlayer(mExoPlayer);

                // Set the ExoPlayer.EventListener to this activity.
                mExoPlayer.addListener(this);

                // Prepare the MediaSource.
                String userAgent = Util.getUserAgent(getActivity(), getActivity().getString(R.string.app_name));
                MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                        getActivity(), userAgent), new DefaultExtractorsFactory(), null, null);
                mExoPlayer.prepare(mediaSource);
                if (lastPlayedPosition > 0) mExoPlayer.seekTo(lastPlayedPosition);
                mExoPlayer.setPlayWhenReady(lastPlayedPosition > 0);
            }
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

    /**
     * Initializes the Media Session to be enabled with media buttons, transport controls, callbacks
     * and media controller.
     */
    private void initializeMediaSession() {

        // Create a MediaSessionCompat.
        mMediaSession = new MediaSessionCompat(getActivity(), TAG);

        // Enable callbacks from MediaButtons and TransportControls.
        mMediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        // Do not let MediaButtons restart the player when the app is not visible.
        mMediaSession.setMediaButtonReceiver(null);

        // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player.
        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mMediaSession.setPlaybackState(mStateBuilder.build());

        // MySessionCallback has methods that handle callbacks from a media controller.
        mMediaSession.setCallback(new BakingSessionCallback());

        // Start the Media Session since the activity is active.
        mMediaSession.setActive(true);
    }

    /**
     * Media Session Callbacks, where all external clients control the player.
     */
    private class BakingSessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            mExoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            mExoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            mExoPlayer.seekTo(0);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mExoPlayer != null) {
            lastPlayedPosition = mExoPlayer.getCurrentPosition();
            Log.d(TAG, "onSaveInstanceState: lastPlayedPosition = " + lastPlayedPosition);
            outState.putLong(KEY_POSITION, lastPlayedPosition);
        }
        outState.putParcelable(KEY_STEP, mStep);
    }

    public void updateStepData(Step step) {
        releasePlayer();
        mStep = step;
        getPresenter().updateData(mStep);
    }

    public void setLastPlayedPosition(long lastPlayedPosition) {
        this.lastPlayedPosition = lastPlayedPosition;
    }
}
