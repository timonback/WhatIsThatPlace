package de.timonback.android.whatisthatplace.component.gallery;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import de.timonback.android.whatisthatplace.R;
import de.timonback.android.whatisthatplace.model.knowledge.KnowledgeResult;
import de.timonback.android.whatisthatplace.model.vision.VisionResult;
import de.timonback.android.whatisthatplace.service.ServiceProvider;
import de.timonback.android.whatisthatplace.util.MyParamCallable;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;

public class GallerySection extends StatelessSection {
    private static final String LOG_NAME = GallerySection.class.getName();

    private final Context context;
    private final String title;
    private final List<GalleryItem> list;

    boolean expanded = true;

    public GallerySection(Context context, String title, List<GalleryItem> collection) {
        super(new SectionParameters.Builder(R.layout.gallery_cell)
                .headerResourceId(R.layout.gallery_header)
                .build());

        this.context = context;
        this.title = title;
        this.list = collection;
    }

    @Override
    public int getContentItemsTotal() {
        return list.size();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ItemViewHolder itemHolder = (ItemViewHolder) holder;

        String name = list.get(position).getTitle();
        String category = list.get(position).getTitle();

        itemHolder.mainText.setText(name);
        itemHolder.subText.setText(category);

        final File file = list.get(position).getImageFile();
        Picasso.with(context).load(file).resize(150, 150).centerCrop().into(itemHolder.image);

        itemHolder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServiceProvider.getVisionService(context).analyse(Uri.fromFile(file), new MyParamCallable<VisionResult>() {
                    @Override
                    public void call(VisionResult param) {
                        if (param.getLandmarks().isEmpty()) {
                            Toast.makeText(context, "no landmark identified", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        String mid = param.getLandmarks().get(0).getMid();
                        ServiceProvider.getKnowledgeService(context).getKnowledgeInfo(mid, new MyParamCallable<KnowledgeResult>() {
                            @Override
                            public void call(KnowledgeResult param) {
                                if (param.getItemListElement().isEmpty()) {
                                    Toast.makeText(context, "Google seems to not know this place...", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                String name = param.getItemListElement().get(0).getResult().getName();
                                String description = param.getItemListElement().get(0).getResult().getDescription();
                                Toast.makeText(context, "This is " + name, Toast.LENGTH_SHORT).show();
                                Toast.makeText(context, description, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        HeaderViewHolder headerHolder = (HeaderViewHolder) holder;

        headerHolder.title.setText(title);
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final Button btnMore;

        HeaderViewHolder(View view) {
            super(view);

            title = (TextView) view.findViewById(R.id.header_title);
            btnMore = (Button) view.findViewById(R.id.header_button);
        }
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        private final View rootView;
        private final TextView mainText;
        private final TextView subText;
        private final ImageView image;

        ItemViewHolder(View view) {
            super(view);

            rootView = view;
            mainText = (TextView) view.findViewById(R.id.gallery_item_main);
            subText = (TextView) view.findViewById(R.id.gallery_item_sub);
            image = (ImageView) view.findViewById(R.id.gallery_img);
        }
    }
}
