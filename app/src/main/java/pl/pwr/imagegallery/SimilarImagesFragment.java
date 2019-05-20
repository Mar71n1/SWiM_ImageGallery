package pl.pwr.imagegallery;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SimilarImagesFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_similar_images, container, false);
        ArrayList<ImageView> similarImageViews = new ArrayList<>();
        similarImageViews.add((ImageView) view.findViewById(R.id.fragment_similar_images_imageView1));
        similarImageViews.add((ImageView) view.findViewById(R.id.fragment_similar_images_imageView2));
        similarImageViews.add((ImageView) view.findViewById(R.id.fragment_similar_images_imageView3));
        similarImageViews.add((ImageView) view.findViewById(R.id.fragment_similar_images_imageView4));
        similarImageViews.add((ImageView) view.findViewById(R.id.fragment_similar_images_imageView5));
        similarImageViews.add((ImageView) view.findViewById(R.id.fragment_similar_images_imageView6));
        int nextFreePosition = 0;
        ArrayList<ImageData> images = getArguments().getParcelableArrayList("images");
        int imageClickedPosition = getArguments().getInt("imageClickedPosition");
        findSimilarImages(images, imageClickedPosition, similarImageViews, nextFreePosition);
        return view;
    }

    private void findSimilarImages(ArrayList<ImageData> images, int imageClickedPosition, ArrayList<ImageView> similarImageViews, int nextFreePosition) {
        for(int i = 0; i < images.size(); i++) {
            if(imageClickedPosition != i) {
                if(compareTags(images.get(imageClickedPosition).getTags(), images.get(i).getTags())) {
                    Picasso.get().load(images.get(i).getUrl()).into(similarImageViews.get(nextFreePosition));
                    nextFreePosition++;
                }
            }
        }
    }

    // Returns true if at least one common tag for both images.
    private boolean compareTags(ArrayList<String> tags1, ArrayList<String> tags2) {
        for(int i = 0; i < tags1.size(); i++)
            for(int j = 0; j < tags2.size(); j++)
                if(tags1.get(i).equals(tags2.get(j)))
                    return true;
        return false;
    }

    public static SimilarImagesFragment newInstance(ArrayList<ImageData> images, int imageClickedPosition) {
        SimilarImagesFragment similarImagesFragment = new SimilarImagesFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("images", images);
        bundle.putInt("imageClickedPosition", imageClickedPosition);
        similarImagesFragment.setArguments(bundle);
        return similarImagesFragment;
    }
}
