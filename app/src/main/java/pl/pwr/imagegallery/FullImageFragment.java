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

public class FullImageFragment extends Fragment {
    private ImageView image;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_full_image , container, false);
        image = view.findViewById(R.id.fragment_full_image_imageView);
        Picasso.get().load(getArguments().getString("url")).into(image);
        return view;
    }

    public static FullImageFragment newInstance(String url) {
        FullImageFragment fullImageFragment = new FullImageFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        fullImageFragment.setArguments(bundle);
        return fullImageFragment;
    }
}
