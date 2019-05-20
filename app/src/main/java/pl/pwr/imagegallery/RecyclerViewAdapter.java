package pl.pwr.imagegallery;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapter";
    private static final int TAGS_TO_DISPLAY = 3;

    private Context context;
    private ArrayList<ImageData> images;
    private OnImageListener onImageListener;

    public RecyclerViewAdapter(Context context, ArrayList<ImageData> images, OnImageListener onImageListener) {
        this.context = context;
        this.images = images;
        this.onImageListener = onImageListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_main_recycler_view_list_item, viewGroup, false);
        return new ViewHolder(view, onImageListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        Log.d(TAG, "onBindViewHolder: called.");

        Picasso.get().load(images.get(i).getUrl()).into(viewHolder.image);
        viewHolder.imageTitle.setText(images.get(i).getTitle());
        viewHolder.imageDate.setText(images.get(i).getDate());
        if(0 < images.get(i).getTags().size()) {
            StringBuilder newTags = new StringBuilder();
            if (TAGS_TO_DISPLAY < images.get(i).getTags().size()) {
                for (int j = 0; j < TAGS_TO_DISPLAY; j++) {
                    newTags.append(images.get(i).getTags().get(j));
                    newTags.append(", ");
                }
            } else
                for (String tag : images.get(i).getTags()) {
                    newTags.append(tag);
                    newTags.append(", ");
                }
            viewHolder.tags.setText(newTags.toString().substring(0, newTags.length() - 2));
        } else
            viewHolder.tags.setText("No tags for this image.");
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView image;
        TextView imageTitle;
        TextView imageDate;
        TextView tags;
        RelativeLayout itemLayout;

        OnImageListener onImageListener;

        public ViewHolder(@NonNull View itemView, OnImageListener onImageListener) {
            super(itemView);
            image = itemView.findViewById(R.id.activity_main_recycler_view_list_item_image);
            imageTitle = itemView.findViewById(R.id.activity_main_recycler_view_list_item_title);
            imageDate = itemView.findViewById(R.id.activity_main_recycler_view_list_item_date);
            tags = itemView.findViewById(R.id.activity_main_recycler_view_list_item_tags);
            itemLayout = itemView.findViewById(R.id.activity_main_recycler_view_list_item_layout);

            this.onImageListener = onImageListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onImageListener.onImageClick(getAdapterPosition());
        }
    }

    public interface OnImageListener {
        void onImageClick(int position);
    }

    public void removeItem(int position) {
        images.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(int position, ImageData image) {
        images.add(position, image);
        notifyItemInserted(position);
    }
}
