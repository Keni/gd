package keni.gd_new.flats;

/**
 * Created by Keni on 27.03.2016.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

import keni.gd_new.R;
import keni.gd_new.app.AppController;

public class FlatImageActivity extends Fragment
{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.flat_image, null);
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();

        Bundle args = getArguments();

        ArrayList<String> image_url = args.getStringArrayList("image_url");
        int position = args.getInt("position");

        NetworkImageView image = (NetworkImageView) view.findViewById(R.id.image);

        image.setImageUrl(image_url.get(position), imageLoader);

        return view;
    }
}
