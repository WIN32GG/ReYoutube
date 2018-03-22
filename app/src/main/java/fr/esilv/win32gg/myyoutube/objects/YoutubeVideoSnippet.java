package fr.esilv.win32gg.myyoutube.objects;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by win32gg on 16/02/18.
 */

public class YoutubeVideoSnippet implements Serializable {

    public String publishedAt;
    public String channelId;
    public String title;
    public String description;

    public String channelTitle;

    public Map<String, Thumbnail> thumbnails;

}
