package fr.esilv.win32gg.myyoutube.objects;

import java.io.Serializable;
import java.util.List;

/**
 * Created by win32gg on 16/02/18.
 */

public class YoutubeVideoSearchAnswer implements Serializable {

    public List<YoutubeIdInfo> items;
    public String regionCode;
}
