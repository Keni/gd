package keni.gd_new;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import keni.gd_new.app.AppController;
import keni.gd_new.mail.MailSendDialog;

public class FlatviewActivity extends Activity
{
    private String id, image, title, clas, type, district;
    private int place, price;

    private GoogleMap googleMap;
    private double latitude, longitude;
    private float distance;
    DialogFragment booking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flat_view);
        booking = new MailSendDialog();
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF0000")));

        ImageLoader imageLoader = AppController.getInstance().getImageLoader();


        TextView textViewID = (TextView) findViewById(R.id.textID);
        TextView textViewDistrict = (TextView) findViewById(R.id.textDistrict);
        TextView textViewClass = (TextView) findViewById(R.id.textClass);
        TextView textViewType = (TextView) findViewById(R.id.textType);
        TextView textViewAddress = (TextView) findViewById(R.id.textAddress);
        TextView textViewPlace = (TextView) findViewById(R.id.textPlace);
        TextView textViewPrice = (TextView) findViewById(R.id.textViewPrice);

        NetworkImageView images = (NetworkImageView) findViewById(R.id.images);

        Intent info = getIntent();

        id = info.getStringExtra("id");
        image = info.getStringExtra("image");
        title = info.getStringExtra("title");
        clas = info.getStringExtra("class");
        type = info.getStringExtra("type");
        district = info.getStringExtra("district");
        place = info.getIntExtra("place", 0);
        price = info.getIntExtra("price", 0);

        images.setImageUrl(image, imageLoader);
        textViewID.setText(id);
        textViewAddress.setText(title);
        textViewClass.setText(clas);
        textViewType.setText(type);
        textViewDistrict.setText(district);
        textViewPlace.setText(" " + place);
        textViewPrice.setText(price + " Р/сут.");


        try {

            //Передача координат
            latitude = info.getDoubleExtra("latitude", 0);
            longitude = info.getDoubleExtra("longitude", 0);

            //Функция инициализации карты
            initializeMap();

            CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(latitude, longitude)).zoom(15).build();
            MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude));

            googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            googleMap.addMarker(marker);



        }

        catch (Exception e)
        {
            e.printStackTrace();
        }

    }




    public void Booking(View v)
    {
        Bundle args = new Bundle();
        args.putString("id", id);
        args.putString("title", title);
        booking.setArguments(args);
        booking.show(getFragmentManager(), "Бронирование");
    }

    private void initializeMap()
    {
        if (googleMap == null)
        {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();


            if (googleMap == null)
            {
                Toast.makeText(getApplicationContext(), "Невозможно загрузить карту", Toast.LENGTH_SHORT).show();
            }
        }

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener()
        {
            @Override
            public void onMapClick(LatLng point)
            {
                Log.d("Map", "Map clicked");
                Intent map = new Intent(FlatviewActivity.this, MapsActivity.class);
                map.putExtra("latitude", latitude);
                map.putExtra("longitude", longitude);
                startActivity(map);
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        switch (menuItem.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }




}
