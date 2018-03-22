package fr.esilv.win32gg.myyoutube.objects;

import java.util.Map;

/**
 * Created by win32gg on 22/03/18.
 */

public class YoutubeIdInfo {
    public Map<String, String> id;

    public boolean isVideo() {
        return id.get("kind").equals("youtube#video");
    }

    public String getVideoId() {
        return id.get("videoId");
    }
}
