package keni.gd_new.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.List;

import keni.gd_new.R;
import keni.gd_new.app.AppController;
import keni.gd_new.model.Flat;

/**
 * Created by Keni on 02.03.2016.
 */

public class CustomListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Flat> flatItems;
    private List<Flat> allflats;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public CustomListAdapter(Activity activity, List<Flat> flatItems)
    {
        this.activity = activity;
        this.flatItems = flatItems;
    }

    @Override
    public int getCount()
    {
        return flatItems.size();
    }

    @Override
    public Object getItem(int location)
    {
        return flatItems.get(location);
    }


    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        NetworkImageView thumbNail = (NetworkImageView) convertView.findViewById(R.id.thumbnail);
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView rating = (TextView) convertView.findViewById(R.id.rating);
        TextView genre = (TextView) convertView.findViewById(R.id.genre);
        TextView year = (TextView) convertView.findViewById(R.id.releaseYear);

        // getting movie data for the row
        Flat m = flatItems.get(position);

        // thumbnail image
        thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);

        // title
        title.setText(m.getTitle());

        // rating
        rating.setText("Класс: " + m.getRating());

        // release year
        year.setText(m.getYear());

        // genre
        genre.setText(String.valueOf(m.getGenre()) + " Р/сут.");


        return convertView;


    }


    public Filter getFiler()
    {
        this.allflats = new ArrayList<Flat>(flatItems);
        Filter filter = new Filter()
        {
            @Override
            protected FilterResults performFiltering(CharSequence constraint)
            {
                FilterResults result = new Filter.FilterResults();
                //List<Flat> allflats = flatItems;
                if (constraint == null || constraint.length() == 0)
                {
                    result.values = allflats;
                    result.count = allflats.size();
                }
                else
                {
                    ArrayList<Flat> filteredList = new ArrayList<Flat>();
                    for (Flat m : allflats)
                    {
                        if (m.getRating().contains(constraint))
                            filteredList.add(m);
                    }
                    result.values = filteredList;
                    result.count = filteredList.size();
                }
                return result;

            }

                @SuppressWarnings("unchecked")
                @Override
                protected void publishResults (CharSequence constraint, FilterResults results)
                {
                    flatItems = (ArrayList<Flat>) results.values;
                    notifyDataSetChanged();
                }
            };

        return filter;
    }

}
