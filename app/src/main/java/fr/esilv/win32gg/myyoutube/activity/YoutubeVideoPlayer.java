package fr.esilv.win32gg.myyoutube.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import fr.esilv.win32gg.myyoutube.R;
import fr.esilv.win32gg.myyoutube.objects.YoutubeVideoDescription;
import fr.esilv.win32gg.myyoutube.utils.StaticConfig;

public class YoutubeVideoPlayer extends YouTubeBaseActivity {

    private TextView likes;
    private TextView dislikes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_video_player);

        YoutubeVideoDescription video = (YoutubeVideoDescription) this.getIntent().getExtras().get("video");

        ((TextView)findViewById(R.id.video_title)).setText(video.snippet.title);
        ((TextView)findViewById(R.id.viewCountTextView)).setText(video.getViews()+" vues");

        this.likes = ((TextView)findViewById(R.id.likesCount));
        this.dislikes = ((TextView)findViewById(R.id.dislikesCount));

        this.likes.setTextColor(getResources().getColor(R.color.likes));
        this.dislikes.setTextColor(getResources().getColor(R.color.dislikes));

        this.likes.setText(""+video.getLikes());
        this.dislikes.setText(""+video.getDislikes());

        this.startVideo(video);
    }

    private void startVideo(final YoutubeVideoDescription video) {
        YouTubePlayerView v = this.findViewById(R.id.video_view);
        v.initialize(StaticConfig.API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(video.getVideoId());
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                System.err.println("Failed");
            }
        });
    }
}
