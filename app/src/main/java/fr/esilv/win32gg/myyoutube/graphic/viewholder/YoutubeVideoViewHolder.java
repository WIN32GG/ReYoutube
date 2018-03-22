package fr.esilv.win32gg.myyoutube.graphic.viewholder;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import fr.esilv.win32gg.myyoutube.R;
import fr.esilv.win32gg.myyoutube.objects.YoutubeVideoDescription;
import fr.esilv.win32gg.myyoutube.utils.DownloadImageTask;

public class YoutubeVideoViewHolder extends RecyclerView.ViewHolder {

    private TextView title;
    private TextView description;
    private ImageView image;


    public YoutubeVideoViewHolder(View itemView) {
        super(itemView);
        this.title = itemView.findViewById(R.id.title);
        this.description = itemView.findViewById(R.id.description);
        this.image = itemView.findViewById(R.id.image);

    }

    public void bind(YoutubeVideoDescription video) {
        try {
            this.title.setText(video.snippet.title);
            this.description.setText(video.snippet.description);

            Picasso.get().load(video.snippet.thumbnails.get("default").url).into(this.image);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
