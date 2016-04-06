package keni.gd_new;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import keni.gd_new.adapter.CustomListAdapter;
import keni.gd_new.app.AppController;
import keni.gd_new.flats.FlatviewActivity;
import keni.gd_new.model.Flat;

public class MainActivity extends AppCompatActivity
{
    Toolbar toolbar;

    // Log tag
    private static final String TAG = MainActivity.class.getSimpleName();

    // Movies json url
    private static final String url = "http://192.168.1.10/json/gd/flats.json";
    private ProgressDialog pDialog;
    private List<Flat> flatList = new ArrayList<Flat>();
    private ListView listView;
    private CustomListAdapter adapter;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolBar();

        listView = (ListView) findViewById(R.id.list);
        adapter = new CustomListAdapter(this, flatList);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent flatView = new Intent(MainActivity.this, FlatviewActivity.class);

                flatView.putExtra("id", flatList.get(position).getId());
                flatView.putExtra("image", flatList.get(position).getThumbnailUrl());
                flatView.putExtra("title", flatList.get(position).getTitle());
                flatView.putExtra("class", flatList.get(position).getRating());
                flatView.putExtra("type", flatList.get(position).getYear());
                flatView.putExtra("district", flatList.get(position).getDistrict());
                flatView.putExtra("place", flatList.get(position).getPlace());
                flatView.putExtra("price", flatList.get(position).getGenre());
                flatView.putExtra("latitude", flatList.get(position).getLatitude());
                flatView.putExtra("longitude", flatList.get(position).getLongitude());
                flatView.putExtra("images", flatList.get(position).getImages());
                startActivity(flatView);

            }
        });


        // Creating volley request obj
        JsonArrayRequest flatReq = new JsonArrayRequest(url, new Response.Listener<JSONArray>()
        {
                    @Override
                    public void onResponse(JSONArray response)
                    {
                        Log.d(TAG, response.toString());
                        hidePDialog();

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                Flat flat = new Flat();
                                flat.setId(obj.getString("id"));
                                flat.setTitle(new String(obj.getString("title").getBytes("ISO-8859-1"), "UTF-8"));
                                flat.setThumbnailUrl((String) obj.getJSONArray("images").get(0));
                                flat.setRating(new String(obj.getString("rating").getBytes("ISO-8859-1"), "UTF-8"));
                                flat.setYear(new String(obj.getString("releaseYear").getBytes("ISO-8859-1"), "UTF-8"));
                                flat.setDistrict(new String(obj.getString("district").getBytes("ISO-8859-1"), "UTF-8"));
                                flat.setPlace(obj.getInt("place"));
                                flat.setGenre(obj.getInt("genre"));
                                flat.setLatitude(obj.getDouble("latitude"));
                                flat.setLongitude(obj.getDouble("longitude"));

                                // Массив картинок
                                JSONArray imagesArry = obj.getJSONArray("images");
                                ArrayList<String> images = new ArrayList<String>();
                                for (int j = 0; j < imagesArry.length(); j++)
                                {
                                    images.add((String) imagesArry.get(j));
                                }
                                flat.setImages(images);

                                //
                                flatList.add(flat);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }

                        }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hidePDialog();
            }


        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(flatReq);


    }

    public void Search(View view)
    {
        //adapter.getFiler().filter("1500");
        adapter.getFiler().filter("Полулюкс");
    }


    public void initToolBar()
    {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_launcher);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
    }




    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

}
