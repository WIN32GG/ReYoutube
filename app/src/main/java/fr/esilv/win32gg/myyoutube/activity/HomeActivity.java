package fr.esilv.win32gg.myyoutube.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;

import fr.esilv.win32gg.myyoutube.R;
import fr.esilv.win32gg.myyoutube.graphic.YoutubeVideoAdapter;
import fr.esilv.win32gg.myyoutube.network.YoutubeAPI;
import fr.esilv.win32gg.myyoutube.objects.YoutubeVideoDescription;
import fr.esilv.win32gg.myyoutube.objects.YoutubeVideoSearchAnswer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity {

    private static class HomeActivityViewHolder {
        private EditText searchTextField;
        private RecyclerView answers;

    }

    public static final String API_KEY = "AIzaSyAWkBtVU08ixyY_LaEvQ-x1VkzFFmFeZcE";

    private HomeActivityViewHolder holder = new HomeActivityViewHolder();
    private YoutubeAPI youtubeAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        holder.searchTextField = this.findViewById(R.id.searchQuery);
        holder.answers = this.findViewById(R.id.video_list);

        initApi();

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
    }

    private void showVideoList(List<YoutubeVideoDescription> list) {
       holder.answers.setLayoutManager(new LinearLayoutManager(this));
       holder.answers.setAdapter(new YoutubeVideoAdapter(list));
    }

    public void searchFor(View button) {
        String rawSearchQuery = this.holder.searchTextField.getText().toString();
        System.out.println("Searching for "+rawSearchQuery);

        Call<YoutubeVideoSearchAnswer> call =  this.youtubeAPI.searchFor("snippet", rawSearchQuery, API_KEY, 50);
        call.enqueue(new Callback<YoutubeVideoSearchAnswer>() {
            @Override
            public void onResponse(Call<YoutubeVideoSearchAnswer> call, Response<YoutubeVideoSearchAnswer> response) {
                System.out.println("Response");
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
            public void onFailure(Call<YoutubeVideoSearchAnswer> call, Throwable t) {
                System.out.println("Failure");
                t.printStackTrace();
            }
        });



    }
}
