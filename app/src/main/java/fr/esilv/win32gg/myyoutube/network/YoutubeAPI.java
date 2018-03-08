package fr.esilv.win32gg.myyoutube.network;

import java.util.List;

import fr.esilv.win32gg.myyoutube.objects.YoutubeVideoDescription;
import fr.esilv.win32gg.myyoutube.objects.YoutubeVideoSearchAnswer;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by win32gg on 16/02/18.
 */

public interface YoutubeAPI {

    @GET("search")
    Call<YoutubeVideoSearchAnswer> searchFor(@Query("part") String part,
                                             @Query("q") String query,
                                             @Query("key") String apiKey,
                                             @Query("maxResults") int maxResults);

}
