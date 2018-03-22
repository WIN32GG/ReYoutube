package fr.esilv.win32gg.myyoutube.objects;

import java.io.Serializable;

/**
 * Created by win32gg on 22/03/18.
 */

public class YoutubeVideoStatistics implements Serializable {
    /*
    "statistics": {
        "viewCount": "13963715",
        "likeCount": "181066",
        "dislikeCount": "3439",
        "favoriteCount": "0",
        "commentCount": "6897"
    }
     */


    public String viewCount;
    public String likeCount;
    public String dislikeCount;
    public String favoriteCount;
    public String commentCount;


}
