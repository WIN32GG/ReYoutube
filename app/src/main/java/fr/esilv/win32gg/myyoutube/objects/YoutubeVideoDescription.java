package fr.esilv.win32gg.myyoutube.objects;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by win32gg on 16/02/18.
 */

public class YoutubeVideoDescription implements Serializable{

    public String kind;
    public String etag;

    public String id;

    public YoutubeVideoSnippet snippet;

    @SerializedName("statistics")
    public YoutubeVideoStatistics stats;



    /*
    "contentDetails": {
        "duration": "PT21M3S",
        "dimension": "2d",
        "definition": "hd",
        "caption": "true",
        "licensedContent": true,
        "projection": "rectangular"
      },
      "statistics": {
        "viewCount": "13963715",
        "likeCount": "181066",
        "dislikeCount": "3439",
        "favoriteCount": "0",
        "commentCount": "6897"
      }
     */

    public int getViews() {return Integer.parseInt(this.stats.viewCount);}
    public int getLikes() {return Integer.parseInt(this.stats.likeCount);}
    public int getDislikes() {return Integer.parseInt(this.stats.dislikeCount);}
    public int getCommentCount() {return Integer.parseInt(this.stats.dislikeCount);}
    public String getVideoId() {return id;}

    public String computeRating() {
        return String.format("%.2f", (float)this.getLikes()/(this.getLikes()+this.getDislikes())*100);
    }
}
