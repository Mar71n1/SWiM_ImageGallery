package pl.pwr.imagegallery;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ImageDetailsFragment extends Fragment {
    private static final int TAGS_TO_DISPLAY = 3;

    private TextView urlTextView;
    private TextView titleTextView;
    private TextView dateTextView;
    private TextView tagsTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_details, container, false);
        urlTextView = view.findViewById(R.id.fragment_image_details_url);
        titleTextView = view.findViewById(R.id.fragment_image_details_title);
        dateTextView = view.findViewById(R.id.fragment_image_details_date);
        tagsTextView = view.findViewById(R.id.fragment_image_details_tags);
        urlTextView.setText("URL: " + getArguments().getString("url"));
        titleTextView.setText("Title: " + getArguments().getString("title"));
        dateTextView.setText("Date: " + getArguments().getString("date"));
        tagsTextView.setText("Tags: " + getArguments().getString("tags"));
        return view;
    }

    public static ImageDetailsFragment newInstance(String url, String title, String date, ArrayList<String> tags) {
        ImageDetailsFragment imageDetailsFragment = new ImageDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        bundle.putString("title", title);
        bundle.putString("date", date);
        if(0 < tags.size()) {
            StringBuilder tagsString = new StringBuilder();
            if (TAGS_TO_DISPLAY < tags.size()) {
                for (int j = 0; j < TAGS_TO_DISPLAY; j++) {
                    tagsString.append(tags.get(j));
                    tagsString.append(", ");
                }
            } else
                for (String tag : tags) {
                    tagsString.append(tag);
                    tagsString.append(", ");
                }
            bundle.putString("tags", tagsString.toString().substring(0, tagsString.length() - 2));
        } else
            bundle.putString("tags", "No tags for this image");
        imageDetailsFragment.setArguments(bundle);
        return imageDetailsFragment;
    }
}
