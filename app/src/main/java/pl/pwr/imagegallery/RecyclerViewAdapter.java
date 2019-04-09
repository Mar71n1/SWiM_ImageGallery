package pl.pwr.imagegallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabel;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabeler;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapter";

    private Context context;
    private ArrayList<ImageData> images;
    //private ArrayList<String> imageUrls;
    //private ArrayList<String> imageTitles;
    //private ArrayList<String> imageDates;

    public RecyclerViewAdapter(Context context, ArrayList<ImageData> images/*ArrayList<String> imageUrls, ArrayList<String> imageTitles, ArrayList<String> imageDates*/) {
        this.images = images;
        //this.imageUrls = imageUrls;
        //this.imageTitles = imageTitles;
        //this.imageDates = imageDates;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_main_recycler_view_list_item, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        Log.d(TAG, "onBindViewHolder: called.");

        Picasso.get().load(images.get(i).getUrl()).into(viewHolder.image);
        //viewHolder.imageUrl.setText(images.get(i).getUrl());
        viewHolder.imageTitle.setText(images.get(i).getTitle());
        viewHolder.imageDate.setText(images.get(i).getDate());
        Picasso.get().load(images.get(i).getUrl()).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                processImageTagging(bitmap, viewHolder);
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView imageTitle;
        TextView imageDate;
        TextView tags;
        RelativeLayout itemLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.activity_main_recycler_view_list_item_image);
            imageTitle = itemView.findViewById(R.id.activity_main_recycler_view_list_item_title);
            imageDate = itemView.findViewById(R.id.activity_main_recycler_view_list_item_date);
            tags = itemView.findViewById(R.id.activity_main_recycler_view_list_item_tags);
            itemLayout = itemView.findViewById(R.id.activity_main_recycler_view_list_item_layout);
        }
    }

    public void removeItem(int position) {
        images.remove(position);
        //imageUrls.remove(position);
        //imageTitles.remove(position);
        //imageDates.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(int position, ImageData image/*String url, String title, String date*/) {
        images.add(position, image);
        //imageUrls.add(position, url);
        //imageTitles.add(position, title);
        //imageDates.add(position, date);
        notifyItemInserted(position);
    }

    private void processImageTagging(Bitmap bitmap, @NonNull final ViewHolder viewHolder) {
        FirebaseVisionImage visionImage = FirebaseVisionImage.fromBitmap(bitmap);
        FirebaseVisionImageLabeler labeler = FirebaseVision.getInstance().getOnDeviceImageLabeler();

        labeler.processImage(visionImage)
                .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionImageLabel>>() {
                    @Override
                    public void onSuccess(List<FirebaseVisionImageLabel> firebaseVisionImageLabels) {
                        String newTags = "";
                        if(firebaseVisionImageLabels.size() > 3)
                            for (int i = 0; i < 3; i++) {
                                newTags += firebaseVisionImageLabels.get(i).getText();
                                newTags += ", ";
                            }
                        else
                            for(FirebaseVisionImageLabel label : firebaseVisionImageLabels) {
                                newTags += label.getText();
                                newTags += ", ";
                            }
                        viewHolder.tags.setText(newTags.substring(0, newTags.length() - 2));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.wtf("LAB", e);
                    }
                });
    }
}
