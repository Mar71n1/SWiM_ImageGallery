package pl.pwr.imagegallery;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class HelperFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_helper, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ArrayList<ImageData> images = getArguments().getParcelableArrayList("images");
        int imageClickedPosition = getArguments().getInt("imageClickedPosition");
        Fragment imageDetailsFragment = ImageDetailsFragment.newInstance(images.get(imageClickedPosition).getUrl(), images.get(imageClickedPosition).getTitle(), images.get(imageClickedPosition).getDate(), images.get(imageClickedPosition).getTags());
        Fragment similarImagesFragment = SimilarImagesFragment.newInstance(images, imageClickedPosition);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_helper_child_fragment_1, imageDetailsFragment).commit();
        transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_helper_child_fragment_2, similarImagesFragment).commit();
    }

    public static HelperFragment newInstance(ArrayList<ImageData> images, int imageClickedPosition) {
        HelperFragment helperFragment = new HelperFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("images", images);
        bundle.putInt("imageClickedPosition", imageClickedPosition);
        helperFragment.setArguments(bundle);
        return helperFragment;
    }
}
