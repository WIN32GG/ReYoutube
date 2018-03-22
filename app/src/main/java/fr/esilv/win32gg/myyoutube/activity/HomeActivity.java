package fr.esilv.win32gg.myyoutube.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;

import fr.esilv.win32gg.myyoutube.R;
import fr.esilv.win32gg.myyoutube.graphic.YoutubeVideoAdapter;
import fr.esilv.win32gg.myyoutube.network.YoutubeAPI;
import fr.esilv.win32gg.myyoutube.objects.YoutubeIdInfo;
import fr.esilv.win32gg.myyoutube.objects.YoutubeVideoDescription;
import fr.esilv.win32gg.myyoutube.objects.YoutubeVideoListAnswer;
import fr.esilv.win32gg.myyoutube.objects.YoutubeVideoSearchAnswer;
import fr.esilv.win32gg.myyoutube.utils.StaticConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private static class HomeActivityViewHolder {
        private EditText searchTextField;
        private RecyclerView answers;

    }


    private HomeActivityViewHolder holder = new HomeActivityViewHolder();
    private YoutubeAPI youtubeAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setupSearchActionBar();
        holder.searchTextField = this.findViewById(R.id.searchQuery);
        holder.answers = this.findViewById(R.id.video_list);

        initApi();
    }

    private void setupSearchActionBar() {
        ActionBar mActionBar = this.getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);

        View mCustomView = mInflater.inflate(R.layout.search_layout, null);

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

    private void initApi() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.googleapis.com/youtube/v3/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        this.youtubeAPI = retrofit.create(YoutubeAPI.class);
        System.out.println("Youtube API initialized");
    }

    private void showVideoList(List<YoutubeVideoDescription> list) {
       holder.answers.setLayoutManager(new LinearLayoutManager(this));
       holder.answers.setAdapter(new YoutubeVideoAdapter(list, this));
    }


    private void fetchDetails(String videoIds) {
        System.out.println("Fetching: "+videoIds);
        Call<YoutubeVideoListAnswer> call = this.youtubeAPI.getVideoDetails("snippet,statistics",videoIds, StaticConfig.API_KEY);
        call.enqueue(new Callback<YoutubeVideoListAnswer>() {
            @Override
            public void onResponse(Call<YoutubeVideoListAnswer> call, Response<YoutubeVideoListAnswer> response) {
                List<YoutubeVideoDescription> descriptions = response.body().items;
                System.out.println(descriptions.size()+ " videos");

                if(response.isSuccessful()) {
                    for(YoutubeVideoDescription vid : descriptions) {
                        System.out.println(vid.snippet.title + " "+ vid.getVideoId());
                    }

                    HomeActivity.this.showVideoList(descriptions);

                } else {
                    try {
                        System.err.println("Err: "+response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<YoutubeVideoListAnswer> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    public void searchFor(View button) {
        String rawSearchQuery = this.holder.searchTextField.getText().toString();
        System.out.println("Searching for "+rawSearchQuery);

        Call<YoutubeVideoSearchAnswer> call =  this.youtubeAPI.searchFor("id", rawSearchQuery, StaticConfig.API_KEY, 50);
        call.enqueue(new Callback<YoutubeVideoSearchAnswer>() {
            @Override
            public void onResponse(Call<YoutubeVideoSearchAnswer> call, Response<YoutubeVideoSearchAnswer> response) {
                System.out.println("Response of code "+response.code());
                try {
                    System.out.println("Response is "+response.errorBody().string());
                } catch (Exception e) {
                    ;
                }
                if(response.code() != 200)
                    return;

                String ids = "";
                for(YoutubeIdInfo idInfo:response.body().items) {
                    if(idInfo.isVideo())
                        ids += idInfo.getVideoId()+",";
                }
                if(ids.endsWith(","))
                    ids = ids.substring(0, ids.length()-1);

                fetchDetails(ids);

            }

            @Override
            public void onFailure(Call<YoutubeVideoSearchAnswer> call, Throwable t) {
                System.out.println("Failure");
                t.printStackTrace();
            }
        });

    }

    @Override
    public void onClick(View v) {
       YoutubeVideoDescription video = ((YoutubeVideoAdapter)this.holder.answers.getAdapter()).getVideoByPosition(this.holder.answers.getChildAdapterPosition(v));
       System.out.println("You clicked on "+video.snippet.title);

       Intent intent = new Intent(this, YoutubeVideoPlayer.class);
       intent.putExtra("video", video);
       this.startActivity(intent);
    }
}
