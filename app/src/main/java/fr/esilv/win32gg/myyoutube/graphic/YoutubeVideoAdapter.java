package fr.esilv.win32gg.myyoutube.graphic;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import fr.esilv.win32gg.myyoutube.R;
import fr.esilv.win32gg.myyoutube.graphic.viewholder.YoutubeVideoViewHolder;
import fr.esilv.win32gg.myyoutube.objects.YoutubeVideoDescription;
import fr.esilv.win32gg.myyoutube.objects.YoutubeVideoSearchAnswer;

/**
 * Created by win32gg on 16/02/18.
 */

public class YoutubeVideoAdapter extends RecyclerView.Adapter<YoutubeVideoViewHolder> {

    private List<YoutubeVideoDescription> videos;

    public YoutubeVideoAdapter(List<YoutubeVideoDescription> list) {
        videos = list;
    }

    public YoutubeVideoAdapter(YoutubeVideoSearchAnswer answ) {
        this(answ.items);
    }

    @Override
    public YoutubeVideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new YoutubeVideoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_video, parent, false));
    }

    @Override
    public void onBindViewHolder(YoutubeVideoViewHolder holder, int position) {
        holder.bind(this.videos.get(position));
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }
}
