package fr.esilv.win32gg.myyoutube.objects;

import java.util.Map;

/**
 * Created by win32gg on 16/02/18.
 */

public class YoutubeVideoDescription {

    public String kind;
    public String etag;

    public Map<String, String> id;

    public YoutubeVideoSnippet snippet;

    public String getVideoId() {
        return id.get("videoId");
    }

}
