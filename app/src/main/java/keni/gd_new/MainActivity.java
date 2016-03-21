package keni.gd_new;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
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
import keni.gd_new.model.Movie;

public class MainActivity extends Activity {
    // Log tag
    private static final String TAG = MainActivity.class.getSimpleName();

    // Movies json url
    private static final String url = "http://192.168.1.10/json/gd/flats.json";
    private ProgressDialog pDialog;
    private List<Movie> movieList = new ArrayList<Movie>();
    private ListView listView;
    private CustomListAdapter adapter;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list);
        adapter = new CustomListAdapter(this, movieList);
        listView.setAdapter(adapter);




        // changing action bar color
        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF0000")));



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent flatView = new Intent(MainActivity.this, FlatviewActivity.class);

                flatView.putExtra("id", movieList.get(position).getId());
                flatView.putExtra("image", movieList.get(position).getThumbnailUrl());
                flatView.putExtra("title", movieList.get(position).getTitle());
                flatView.putExtra("class", movieList.get(position).getRating());
                flatView.putExtra("type", movieList.get(position).getYear());
                flatView.putExtra("district", movieList.get(position).getDistrict());
                flatView.putExtra("place", movieList.get(position).getPlace());
                flatView.putExtra("price", movieList.get(position).getGenre());
                flatView.putExtra("latitude", movieList.get(position).getLatitude());
                flatView.putExtra("longitude", movieList.get(position).getLongitude());
                startActivity(flatView);

            }
        });


        // Creating volley request obj
        JsonArrayRequest movieReq = new JsonArrayRequest(url, new Response.Listener<JSONArray>()
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
                                Movie movie = new Movie();
                                movie.setId(obj.getString("id"));
                                movie.setTitle(new String(obj.getString("title").getBytes("ISO-8859-1"), "UTF-8"));
                                movie.setThumbnailUrl(obj.getString("image"));
                                movie.setRating(new String(obj.getString("rating").getBytes("ISO-8859-1"), "UTF-8"));
                                movie.setYear(new String(obj.getString("releaseYear").getBytes("ISO-8859-1"), "UTF-8"));
                                movie.setDistrict(new String(obj.getString("district").getBytes("ISO-8859-1"), "UTF-8"));
                                movie.setPlace(obj.getInt("place"));
                                movie.setGenre(obj.getInt("genre"));
                                movie.setLatitude(obj.getDouble("latitude"));
                                movie.setLongitude(obj.getDouble("longitude"));

                                // adding movie to movies array
                                movieList.add(movie);

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
        AppController.getInstance().addToRequestQueue(movieReq);


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
