package de.timonback.android.whatisthatplace.component.gallery;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.concurrent.Callable;

import de.timonback.android.whatisthatplace.R;
import de.timonback.android.whatisthatplace.component.MyCallable;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryHolder> {
    private final List<GalleryItem> galleryList;
    private final Context context;

    private final MyCallable<GalleryItem> onClickCallable;

    public GalleryAdapter(Context context, List<GalleryItem> galleryList, MyCallable<GalleryItem> clickCallable) {
        this.galleryList = galleryList;
        this.context = context;
        this.onClickCallable = clickCallable;
    }

    @Override
    public GalleryAdapter.GalleryHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.gallery_cell, viewGroup, false);
        return new GalleryHolder(view);
    }

    @Override
    public void onBindViewHolder(final GalleryAdapter.GalleryHolder viewHolder, final int i) {
        viewHolder.title.setText(getItem(i).getTitle());
        viewHolder.img.setScaleType(ImageView.ScaleType.CENTER_CROP);

        Picasso.with(context).load(getItem(i).getImageFile()).resize(240, 120).into(viewHolder.img);

        viewHolder.img.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Image", Toast.LENGTH_SHORT).show();

                onClickCallable.call(getItem(i));
            }
        });
    }

    @Override
    public int getItemCount() {
        return galleryList.size();
    }

    private GalleryItem getItem(int i) {
        return galleryList.get(i);
    }

    public class GalleryHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private ImageView img;

        public GalleryHolder(View view) {
            super(view);

            title = (TextView) view.findViewById(R.id.title);
            img = (ImageView) view.findViewById(R.id.img);
        }
    }

}